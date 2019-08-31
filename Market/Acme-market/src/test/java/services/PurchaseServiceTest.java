package services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import utilities.AbstractTest;
import domain.Market;
import domain.Purchase;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/junit.xml"})
@Transactional
public class PurchaseServiceTest extends AbstractTest {

	@Autowired
	private PurchaseService purchaseService;
	
	@Autowired
	private ProductService productService;
	
	
	@Autowired
	private MarketService marketService;
	
	@Autowired
	private CustomerService customerService;
	

	
	//	Se comprueba que solo los market pueden crear los purchases.
	@Test
	public void driverCreatePurchase(){
		
		final Object testingData[][] = {{"customer1", null},
										{"customer2", null},
										{"market1", DataIntegrityViolationException.class}};
		
		for(int i = 0; i < testingData.length; i++){
			templateCreatePurchase((String) testingData[i][0], (Class<?>)testingData[i][1]);
		}
	}
	
	protected void templateCreatePurchase(String username, Class<?> expected){
		Class<?> caught = null;

		try{
			super.authenticate(username);
			Purchase p = this.purchaseService.create();
			Market market = (Market) marketService.findAll().toArray()[0];
			
			p.setMarket(market);
			p.setProducts(productService.getProductsByMarket(market.getId()));
			p.setIsFinal(true);
			p.setCustomer(customerService.getPrincipal());
			
			purchaseService.save(p);
		} catch (Throwable oops){
			caught = oops.getClass();
		}
		
		this.checkExceptions(expected, caught);
		super.unauthenticate();
	}	
}