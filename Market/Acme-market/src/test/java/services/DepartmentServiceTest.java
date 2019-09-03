package services;

import java.util.ArrayList;

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
import domain.Department;
import domain.Market;
import domain.Message;
import domain.Product;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/junit.xml"})
@Transactional
public class DepartmentServiceTest extends AbstractTest {

	//	Coverage: 90.2%
	//	Covered Instructions: 129
	//	Missed  Instructions: 14
	//	Total   Instructions: 143
	
	@Autowired
	private ProductService productService;
		
	@Autowired
	private DepartmentService departmentService;
	
	@Autowired
	private MarketService marketService;
	

	
	//	Se comprueba que solo los provider pueden crear los products.
	@Test
	public void driverCreateDepartment(){
		
		final Object testingData[][] = {{"market1", null},
										{"market2", null},
										{"provider1", IllegalArgumentException.class}};
		
		for(int i = 0; i < testingData.length; i++){
			templateCreateDepartment((String) testingData[i][0], (Class<?>)testingData[i][1]);
		}
	}
	
	protected void templateCreateDepartment(String username, Class<?> expected){
		Class<?> caught = null;

		try{
			super.authenticate(username);
			Department d = this.departmentService.create();
			Market market = (Market) marketService.findAll().toArray()[0];
			
			d.setTitle("Test");
			d.setDiscount(0d);
			d.setMarket(market);

			departmentService.save(d);
		} catch (Throwable oops){
			caught = oops.getClass();
		}
		
		this.checkExceptions(expected, caught);
		super.unauthenticate();
	}
	
	@Test(expected = ConstraintViolationException.class)
	public void testSaveIncorrectData(){
		
		authenticate("market1");

		Department d = this.departmentService.create();
		Market market = (Market) marketService.findAll().toArray()[0];
		
		d.setTitle(" ");
		d.setDiscount(0d);
		d.setMarket(market);

		Department result = departmentService.save(d);

		Assert.isTrue(departmentService.findAll().contains(result));
		
		unauthenticate();
	}
	

}