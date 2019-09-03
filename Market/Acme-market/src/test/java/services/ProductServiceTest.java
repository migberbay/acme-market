package services;

import java.util.ArrayList;

import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Comment;
import domain.Department;
import domain.Product;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/junit.xml"})
@Transactional
public class ProductServiceTest extends AbstractTest {

	//	Coverage: 92.1%
	//	Covered Instructions: 163
	//	Missed  Instructions: 14
	//	Total   Instructions: 177
	
	
	@Autowired
	private ProductService productService;
		
	@Autowired
	private DepartmentService departmentService;
	
	@Autowired
	private ProviderService providerService;
	

	
	//	Se comprueba que solo los provider pueden crear los products.
	@Test
	public void driverCreateProduct(){
		
		final Object testingData[][] = {{"provider1", null},
										{"provider2", null},
										{"market1", IllegalArgumentException.class}};
		
		for(int i = 0; i < testingData.length; i++){
			templateCreateProduct((String) testingData[i][0], (Class<?>)testingData[i][1]);
		}
	}
	
	protected void templateCreateProduct(String username, Class<?> expected){
		Class<?> caught = null;

		try{
			super.authenticate(username);
			Product p = this.productService.create();
			Department department = (Department) departmentService.findAll().toArray()[0];
			
			p.setName("Test");
			p.setPrice(20d);
			p.setStock(20);
			p.setComments(new ArrayList<Comment>());
			p.setDepartment(department);
			p.setProvider(providerService.findOne(super.getEntityId(username)));
			
			productService.save(p);
		} catch (Throwable oops){
			caught = oops.getClass();
		}
		
		this.checkExceptions(expected, caught);
		super.unauthenticate();
	}
	
	@Test(expected = ConstraintViolationException.class)
	public void testSaveIncorrectData(){
		
		authenticate("provider1");

		Product p = this.productService.create();
		Department department = (Department) departmentService.findAll().toArray()[0];
		
		p.setName("");
		p.setPrice(20d);
		p.setStock(20);
		p.setComments(new ArrayList<Comment>());
		p.setDepartment(department);
		p.setProvider(providerService.findOne(super.getEntityId("provider1")));
		
		Product result = productService.save(p);

		Assert.isTrue(productService.findAll().contains(result));
		
		unauthenticate();
	}
	

}