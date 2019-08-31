package services;

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
import domain.Customer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/junit.xml"})
@Transactional
public class CommentServiceTest extends AbstractTest {


	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private CommentService commentService;
	
	@Autowired
	private PurchaseService purchaseService;	
	
	@Autowired
	private DeliveryBoyService deliveryBoyService;
	

	
	//	Se comprueba que cualquier actor puede crear los comments y guardar los comments
	@Test
	public void driverCreateComment(){
		
		final Object testingData[][] = {{"customer1", null},
										{"customer2", ConstraintViolationException.class},
										{"admin", DataIntegrityViolationException.class},
										{null , IllegalArgumentException.class}};
		
		for(int i = 0; i < testingData.length; i++){
			templateCreateComment((String) testingData[i][0], (Class<?>)testingData[i][1]);
		}
	}
	
	protected void templateCreateComment(String username, Class<?> expected){
		Class<?> caught = null;

		try{
			super.authenticate(username);
			Comment c = this.commentService.create();
		
			c.setCustomer(customerService.getPrincipal());
			c.setScore(3);
			c.setText("text");
			c.setMoment(new Date());
			
			c = commentService.save(c);
			Assert.isTrue(commentService.findAll().contains(c));
			
		} catch (Throwable oops){	
			caught = oops.getClass();
		}
		System.out.println("expected: "+ expected+ "caught: "+ caught + "case: "+ username);
		this.checkExceptions(expected, caught);
		super.unauthenticate();
	}
	
	
	//	Se comprueba que no se puede guardar con campos en blanco.
	@Test(expected = ConstraintViolationException.class)
	public void testSaveIncorrectData(){
		
		authenticate("customer2");

		Comment comment = commentService.create();
		comment.setScore(7);
		comment.setText("");
		comment.setMoment(new Date(System.currentTimeMillis()+100000));
		
		Comment result = commentService.save(comment);
		
		Assert.isTrue(commentService.findAll().contains(result));
		
		unauthenticate();
	}
	

	

}