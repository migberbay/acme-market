package services;

import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import security.LoginService;
import security.UserAccountService;
import utilities.AbstractTest;
import domain.Message;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/junit.xml"})
@Transactional
public class MessageServiceTest extends AbstractTest {

	
	
	@Autowired
	private MessageService messageService;
	
	@Autowired
	private UserAccountService userAccountService;
	

	//Comprobamos que todos puede hacerlo.
	@Test
	public void driverCreateMessage(){
		
		final Object testingData[][] = {{"market1", null},
										{"customer1", null},
										{"admin", null},
										{"provider1", null},
										{"deliveryboy1", null},
										{null, IllegalArgumentException.class}};
		
		for(int i = 0; i < testingData.length; i++){
			templateCreateMessage((String) testingData[i][0], (Class<?>)testingData[i][1]);
		}
	}
	
	protected void templateCreateMessage(String username, Class<?> expected){
		Class<?> caught = null;

		try{
			super.authenticate(username);
			Message m = this.messageService.create(LoginService.getPrincipal());
			m.setBody("hi ma nigga");
			m.setSubject("this is a nigga message");
			m.setRecipients(userAccountService.findAll());
			
		} catch (Throwable oops){
			caught = oops.getClass();
		}
		
		this.checkExceptions(expected, caught);
		super.unauthenticate();
	}
	
	
	
	//	Se comprueba que no se puede guardar con campos en blanco.
	@Test(expected = ConstraintViolationException.class)
	public void testSaveIncorrectData(){
		
		authenticate("customer1");

		Message m = this.messageService.create(LoginService.getPrincipal());
		m.setBody("");
		m.setSubject("");
		m.setRecipients(userAccountService.findAll());
		Message result = messageService.save(m);
		
		Assert.isTrue(messageService.findAll().contains(result));
		
		unauthenticate();
	}
	

	
	

}