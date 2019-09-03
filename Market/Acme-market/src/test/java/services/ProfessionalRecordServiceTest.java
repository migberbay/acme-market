package services;

import java.util.ArrayList;
import java.util.Date;

import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import security.LoginService;
import utilities.AbstractTest;
import domain.Comment;
import domain.Curricula;
import domain.Department;
import domain.Message;
import domain.ProfessionalRecord;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/junit.xml"})
@Transactional
public class ProfessionalRecordServiceTest extends AbstractTest {

	//	Coverage: 92.2%
	//	Covered Instructions: 166
	//	Missed  Instructions: 14
	//	Total   Instructions: 180
	
	@Autowired
	private ProfessionalRecordService professionalRecordService;
		
	@Autowired
	private CurriculaService curriculaService;	

	
	//	Se comprueba que solo los provider pueden crear los professionalRecords.
	@Test
	public void driverCreateProfessionalRecord(){
		
		final Object testingData[][] = {{"provider1", null},
										{"provider2", null},
										{"market1", DataIntegrityViolationException.class}};
		
		for(int i = 0; i < testingData.length; i++){
			templateCreateProfessionalRecord((String) testingData[i][0], (Class<?>)testingData[i][1]);
		}
	}
	
	protected void templateCreateProfessionalRecord(String username, Class<?> expected){
		Class<?> caught = null;

		try{
			super.authenticate(username);
			ProfessionalRecord p = this.professionalRecordService.create();
			
			p.setAttachment("http://www.test.com");
			p.setComments("Test");
			p.setCurricula(curriculaService.findCurriculaByProvider(super.getEntityId(username)));
			p.setCompanyName("Test");
			p.setEndDate(new Date(System.currentTimeMillis()-1000000));
			p.setStartDate(new Date(System.currentTimeMillis()-10000000));
			p.setRole("Tester");

			professionalRecordService.save(p);
		} catch (Throwable oops){
			caught = oops.getClass();
		}
		
		this.checkExceptions(expected, caught);
		super.unauthenticate();
	}
	
	@Test(expected = ConstraintViolationException.class)
	public void testSaveIncorrectData(){
		
		authenticate("provider1");

		ProfessionalRecord p = this.professionalRecordService.create();
		Curricula curricula = curriculaService.findCurriculaByProvider(LoginService.getPrincipal().getId());
		
		p.setAttachment("http://www.test.com");
		p.setComments("Test");
		p.setCurricula(curricula);
		p.setCompanyName(" ");
		p.setEndDate(new Date(System.currentTimeMillis()-1000000));
		p.setStartDate(new Date(System.currentTimeMillis()-10000000));
		p.setRole("Tester");
		
		ProfessionalRecord result = professionalRecordService.save(p);

		Assert.isTrue(professionalRecordService.findAll().contains(result));
		
		unauthenticate();
	}
	

}