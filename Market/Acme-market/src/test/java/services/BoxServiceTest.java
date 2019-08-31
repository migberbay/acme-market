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
import domain.Box;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/junit.xml"})
@Transactional
public class BoxServiceTest extends AbstractTest {

	
	
	@Autowired
	private BoxService boxService;
	
	@Autowired
	private UserAccountService userAccountService;
	

	//Comprobamos que todos puede hacerlo.
	@Test
	public void driverCreateBox(){
		
		final Object testingData[][] = {{"market1", null},
										{"customer1", null},
										{"admin", null},
										{"provider1", null},
										{"deliveryboy1", null},
										{null, IllegalArgumentException.class}};
		
		for(int i = 0; i < testingData.length; i++){
			templateCreateBox((String) testingData[i][0], (Class<?>)testingData[i][1]);
		}
	}
	
	protected void templateCreateBox(String username, Class<?> expected){
		Class<?> caught = null;

		try{
			super.authenticate(username);
			String nameArray[] = {"In Box", "Out Box", "Trash Box"};
			for (int i = 0; i < nameArray.length; i++) {
				Box b = this.boxService.create(LoginService.getPrincipal());
				b.setName(nameArray[i]);
				boxService.save(b);
			}	
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

		Box b = this.boxService.create(LoginService.getPrincipal());
		b.setName("");
		Box result = boxService.save(b);
		
		Assert.isTrue(boxService.findAll().contains(result));
		
		unauthenticate();
	}
	

	
	

}