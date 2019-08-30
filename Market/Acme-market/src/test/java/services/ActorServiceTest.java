package services;

import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import security.Authority;
import security.UserAccount;
import security.UserAccountService;
import utilities.AbstractTest;
import domain.Admin;
import domain.Customer;
import domain.DeliveryBoy;
import domain.Market;
import domain.Provider;
import forms.RegisterForm;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/junit.xml"})
@Transactional
public class ActorServiceTest extends AbstractTest {

	
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private MarketService marketService;
	
	@Autowired
	private AdminService adminService;
	
	@Autowired
	private ProviderService providerService;
	
	@Autowired
	private DeliveryBoyService deliveryBoyService;
	
	@Autowired
	private UserAccountService userAccountService;
	
	@Autowired
	private ActorService actorService;
	
	
	@Test
	public void testRegisterAdmin(){
		
		authenticate("admin");
		
		UserAccount userAccount = userAccountService.create();
		Authority authority = new Authority();
		authority.setAuthority(Authority.ADMIN);
		userAccount.getAuthorities().add(authority);
		userAccount.setUsername("admin10");
		userAccount.setPassword("admin10");		
		Admin admin = adminService.create(userAccount);
		
		
		admin.setName("adminName");
		admin.setAddress("calle falsa 123)");
		admin.setSurname("adminSurname1");
		admin.setEmail("admin@email.com");
		admin.setPhoto("http://www.photo.com");
		admin.setPhone("678901234");
		
		Admin result = actorService.registerAdmin(admin);
		
		Assert.isTrue(adminService.findAll().contains(result));
		
		unauthenticate();
	}
	

	@Test
	public void testRegisterMarket(){
		
		authenticate("admin");
		
		UserAccount userAccount = userAccountService.create();
		Authority authority = new Authority();
		authority.setAuthority(Authority.ADMIN);
		userAccount.getAuthorities().add(authority);
		userAccount.setUsername("market10");
		userAccount.setPassword("market10");		
		Market market = marketService.create(userAccount);
		
		market.setName("marketName");
		market.setAddress("calle falsa 123)");
		market.setSurname("marketSurname");
		market.setEmail("market@email.com");
		market.setPhoto("http://www.photo.com");
		market.setPhone("678901234");
		
		RegisterForm form = new RegisterForm();
		form.setVATNumber("ESN12341234");
		form.setCompanyName("testCompany");
		
		Market result = actorService.registerMarket(market, form);
		
		Assert.isTrue(marketService.findAll().contains(result));
		
		unauthenticate();
	}
	
	@Test
	public void testRegisterCustomer(){
		
		UserAccount userAccount = userAccountService.create();
		Authority authority = new Authority();
		authority.setAuthority(Authority.CUSTOMER);
		userAccount.getAuthorities().add(authority);
		userAccount.setUsername("customer10");
		userAccount.setPassword("customer10");		
		
		Customer customer = customerService.create(userAccount);
		
		customer.setName("customerName");
		customer.setSurname("customerSurname1");
		customer.setEmail("customer@email.com");
		customer.setPhoto("http://www.photo.com");
		customer.setPhone("+34612123456");
		customer.setAddress("calle falsa 123");
		
		RegisterForm form = new RegisterForm();
		form.setCVV(123);
		form.setExpirationMonth("10");
		form.setExpirationYear("2020");
		form.setHolder("customerName");
		form.setMake("VISA");
		form.setNumber("1234123456785678");
		
		Customer result = actorService.registerCustomer(customer, form);
		
		Assert.isTrue(customerService.findAll().contains(result));
		
		unauthenticate();
	}
	
	@Test(expected = ConstraintViolationException.class)
	public void testIncorrectRegisterCustomer(){
		
		
		UserAccount userAccount = userAccountService.create();
		Authority authority = new Authority();
		authority.setAuthority(Authority.CUSTOMER);
		userAccount.getAuthorities().add(authority);
		userAccount.setUsername("customer10");
		userAccount.setPassword("customer10");		
		Customer customer = customerService.create(userAccount);
		
		customer.setName("customerName");
		customer.setSurname("customerSurname1");
		customer.setEmail("customer@email.com");
		customer.setPhoto("not a valid url");
		customer.setPhone("+34612123456");
		
		RegisterForm form = new RegisterForm();
		form.setCVV(123);
		form.setExpirationMonth("10");
		form.setExpirationYear("2020");
		form.setHolder("customerName");
		form.setMake("VISA");
		form.setNumber("not a valid number");
		
		Customer result = actorService.registerCustomer(customer, form);
		
		Assert.isTrue(customerService.findAll().contains(result));
		
	}
	
	
	@Test
	public void testRegisterProvider(){
		
		UserAccount userAccount = userAccountService.create();
		Authority authority = new Authority();
		authority.setAuthority(Authority.PROVIDER);
		userAccount.getAuthorities().add(authority);
		userAccount.setUsername("provider10");
		userAccount.setPassword("provider10");		
		Provider provider = providerService.create(userAccount);
		
		provider.setName("providerName");
		provider.setSurname("providerSurname");
		provider.setEmail("market@email.com");
		provider.setPhoto("http://www.photo.com");
		provider.setPhone("+34612123456");
		
		
		Provider result = actorService.registerProvider(provider);
		
		Assert.isTrue(providerService.findAll().contains(result));
		
	}
	
	@Test
	public void testRegisterDeliveryBoy(){
		
		UserAccount userAccount = userAccountService.create();
		Authority authority = new Authority();
		authority.setAuthority(Authority.DELIVERYBOY);
		userAccount.getAuthorities().add(authority);
		userAccount.setUsername("market10");
		userAccount.setPassword("market10");		
		DeliveryBoy deliveryBoy = deliveryBoyService.create(userAccount);
		
		deliveryBoy.setName("marketName");
		deliveryBoy.setSurname("marketSurname1 marketSurname2");
		deliveryBoy.setEmail("market@email.com");
		deliveryBoy.setPhoto("http://www.photo.com");
		deliveryBoy.setPhone("+34612123456");
		
		DeliveryBoy result = actorService.registerDeliveryBoy(deliveryBoy);
		
		Assert.isTrue(deliveryBoyService.findAll().contains(result));
		
	}
	
	@Test
	public void testEditAdmin(){
		
		authenticate("admin");
		
		Admin admin = (Admin) actorService.getPrincipal();
		
		admin.setName("adminUpdatedName");
		admin.setSurname("adminUpdatedSurname1");
		admin.setEmail("adminUpdated@email.com");
		admin.setPhoto("http://www.photoUpdated.com");
		
		Admin result = adminService.save(admin);
		
		Assert.isTrue(adminService.findAll().contains(result));
		
		unauthenticate();
	}
	
	@Test(expected = ConstraintViolationException.class)
	public void testIncorrectEditAdmin(){
		
		authenticate("admin");
		
		Admin admin = (Admin) actorService.getPrincipal();
		
		admin.setName("adminUpdatedName");
		admin.setSurname("adminUpdatedSurname");
		admin.setEmail("not a valid email");
		admin.setPhoto("not a valid photo");
		
		Admin result = adminService.save(admin);
		
		Assert.isTrue(adminService.findAll().contains(result));
		
		unauthenticate();
	}
	
	@Test
	public void testEditMarket(){
		
		authenticate("market1");
		Market market = marketService.getPrincipal();
		
		market.setName("marketUpdatedName");
		market.setSurname("marketUpdatedSurname1");
		market.setEmail("marketUpdated@email.com");
		market.setPhoto("http://www.photoUpdated.com");
		
		Market result = marketService.save(market);
		
		Assert.isTrue(marketService.findAll().contains(result));
		
		unauthenticate();
	}
	
	@Test(expected = ConstraintViolationException.class)
	public void testIncorrectEditMarket(){
		
		authenticate("market1");
		
		Market market = marketService.getPrincipal();
		
		market.setName("");
		market.setSurname("");
		
		Market result = marketService.save(market);
		
		Assert.isTrue(marketService.findAll().contains(result));
		
		unauthenticate();
	}
	
	@Test
	public void testEditCustomer(){
		
		authenticate("customer1");
		
		Customer customer = customerService.getPrincipal();
		
		customer.setName("customerUpdatedName");
		customer.setSurname("customerUpdatedSurname1");
		customer.setEmail("customerUpdated@email.com");
		customer.setPhoto("http://www.photoUpdated.com");
		
		Customer result = customerService.save(customer);
		
		Assert.isTrue(customerService.findAll().contains(result));
		
		unauthenticate();
	}
	
	@Test(expected = ConstraintViolationException.class)
	public void testIncorrectEditCustomer(){
		
		authenticate("customer1");
		
		Customer customer = customerService.getPrincipal();
		
		customer.setName("");
		customer.setSurname("");
		
		Customer result = customerService.save(customer);
		
		Assert.isTrue(customerService.findAll().contains(result));
		
		unauthenticate();
	}
	
	@Test
	public void testEditProvider(){
		
		authenticate("provider1");
		
		Provider provider = providerService.getPrincipal();
		
		provider.setName("providerUpdatedName");
		provider.setSurname("providerUpdatedSurname1");
		provider.setEmail("providerUpdated@email.com");
		provider.setPhoto("http://www.photoUpdated.com");
		
		Provider result = providerService.save(provider);
		
		Assert.isTrue(providerService.findAll().contains(result));
		
		unauthenticate();
	}
	
	@Test(expected = ConstraintViolationException.class)
	public void testIncorrectEditProvider(){
		
		authenticate("provider1");
		
		Provider provider = providerService.getPrincipal();
		
		provider.setName("");
		provider.setSurname("");
		
		Provider result = providerService.save(provider);
		
		Assert.isTrue(providerService.findAll().contains(result));
		
		unauthenticate();
	}
}
