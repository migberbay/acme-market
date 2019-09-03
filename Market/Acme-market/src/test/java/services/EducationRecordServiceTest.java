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
import domain.EducationRecord;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/junit.xml"})
@Transactional
public class EducationRecordServiceTest extends AbstractTest {

	//	Coverage: 92.2%
	//	Covered Instructions: 166
	//	Missed  Instructions: 14
	//	Total   Instructions: 180
	
	@Autowired
	private EducationRecordService educationRecordService;
		
	@Autowired
	private CurriculaService curriculaService;	

	
	//	Se comprueba que solo los provider pueden crear los educationRecords.
	@Test
	public void driverCreateEducationRecord(){
		
		final Object testingData[][] = {{"provider1", null},
										{"provider2", null},
										{"market1", DataIntegrityViolationException.class}};
		
		for(int i = 0; i < testingData.length; i++){
			templateCreateEducationRecord((String) testingData[i][0], (Class<?>)testingData[i][1]);
		}
	}
	
	protected void templateCreateEducationRecord(String username, Class<?> expected){
		Class<?> caught = null;

		try{
			super.authenticate(username);
			EducationRecord e = this.educationRecordService.create();
			
			e.setAttachment("http://www.test.com");
			e.setComments("Test");
			e.setCurricula(curriculaService.findCurriculaByProvider(super.getEntityId(username)));
			e.setDiplomaTitle("Test");
			e.setEndDate(new Date(System.currentTimeMillis()-1000000));
			e.setStartDate(new Date(System.currentTimeMillis()-10000000));
			e.setInstitution("Test");

			educationRecordService.save(e);
		} catch (Throwable oops){
			caught = oops.getClass();
		}
		
		this.checkExceptions(expected, caught);
		super.unauthenticate();
	}
	
	@Test(expected = ConstraintViolationException.class)
	public void testSaveIncorrectData(){
		
		authenticate("provider1");

		EducationRecord e = this.educationRecordService.create();
		Curricula curricula = curriculaService.findCurriculaByProvider(LoginService.getPrincipal().getId());
		
		e.setAttachment("Test");
		e.setComments("Test");
		e.setCurricula(curricula);
		e.setDiplomaTitle("");
		e.setEndDate(new Date(System.currentTimeMillis()-1000000));
		e.setStartDate(new Date(System.currentTimeMillis()-10000000));
		e.setInstitution("Test");
		
		EducationRecord result = educationRecordService.save(e);

		Assert.isTrue(educationRecordService.findAll().contains(result));
		
		unauthenticate();
	}
	

}