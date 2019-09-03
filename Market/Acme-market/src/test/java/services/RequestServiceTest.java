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
import domain.Message;
import domain.Product;
import domain.Request;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/junit.xml"})
@Transactional
public class RequestServiceTest extends AbstractTest {

	//	Coverage: 92.7%
	//	Covered Instructions: 178
	//	Missed  Instructions: 14
	//	Total   Instructions: 192
	
	@Autowired
	private RequestService requestService;
		
	@Autowired
	private DepartmentService departmentService;
	
	@Autowired
	private MarketService marketService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private ProviderService providerService;
	

	
	//	Se comprueba que solo los provider pueden crear los requests.
	@Test
	public void driverCreateRequest(){
		
		final Object testingData[][] = {{"market1", null},
										{"market2", null},
										{"customer1", ArrayIndexOutOfBoundsException.class}};
		
		for(int i = 0; i < testingData.length; i++){
			templateCreateRequest((String) testingData[i][0], (Class<?>)testingData[i][1]);
		}
	}
	
	protected void templateCreateRequest(String username, Class<?> expected){
		Class<?> caught = null;

		try{
			super.authenticate(username);
			Request r = this.requestService.create();
			Department department = (Department) departmentService.findDepartmentsByMarket(super.getEntityId(username)).toArray()[0];
			
			r.setDepartmentId(department.getId());
			r.setMarket(marketService.getPrincipal());
			r.setProduct((Product) productService.getProductsByDepartment(department.getId()).toArray()[0]);
			r.setProvider(providerService.findOne(super.getEntityId("provider1")));
			r.setStatus("PENDING");

			requestService.save(r);
		} catch (Throwable oops){
			caught = oops.getClass();
		}
		
		this.checkExceptions(expected, caught);
		super.unauthenticate();
	}
	
	@Test(expected = ConstraintViolationException.class)
	public void testSaveIncorrectData(){
		
		authenticate("market1");

		Request r = this.requestService.create();
		Department department = (Department) departmentService.findDepartmentsByMarket(marketService.getPrincipal().getId()).toArray()[0];
		
		r.setDepartmentId(department.getId());
		r.setMarket(marketService.getPrincipal());
		r.setProduct((Product) productService.getProductsByDepartment(department.getId()).toArray()[0]);
		r.setProvider(providerService.findOne(super.getEntityId("provider1")));
		r.setStatus("");
		
		Request result = requestService.save(r);

		Assert.isTrue(requestService.findAll().contains(result));
		
		unauthenticate();
	}
	

}