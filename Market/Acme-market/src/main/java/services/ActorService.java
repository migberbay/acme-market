package services;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.Validator;

import repositories.ActorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import security.UserAccountService;
import domain.Actor;
import domain.Admin;
import domain.CreditCard;
import domain.Customer;
import domain.DeliveryBoy;
import domain.Market;
import domain.Provider;
import forms.RegisterForm;


@Service
@Transactional
public class ActorService {

	@Autowired
	private ActorRepository actorRepository;
	
	@Autowired
	private UserAccountService userAccountService;
	
	@Autowired
	private AdminService adminService;
	
	@Autowired
	private MarketService marketService;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private ProviderService providerService;
	
	@Autowired
	private DeliveryBoyService deliveryBoyService;
	
	@Autowired
	private CreditCardService creditCardService;
	
	@Autowired
	private BoxService boxService;
	
	@Autowired
	private ConfigurationService configurationService;
	
	
	@Autowired
	private Validator validator;

	// Simple CRUD methods -----
	public Actor create (UserAccount ua){
		Actor res = new Actor();
		res.setUserAccount(ua);
		
		return res;
	}
	
	public Collection<Actor> findAll() {
		return actorRepository.findAll();
	}

	public Actor findOne(int Id) {
		return actorRepository.findOne(Id);
	}

	public Actor save(Actor actor) {

		Actor result;

		result = actorRepository.saveAndFlush(actor);
		return result;
	}

	public void delete(Actor actor) {
		
		actorRepository.delete(actor);
	}

	// Other business methods -----

	public Actor getPrincipal() {
		return actorRepository.getPrincipal(LoginService.getPrincipal());
	}

	public Customer registerCustomer(Actor actor, RegisterForm form) {
		
		Customer res = customerService.create(actor.getUserAccount());
		UserAccount savedua =  userAccountService.save(actor.getUserAccount());
		
		System.out.println("la contraseña de la useraccount persistida es :" + savedua.getPassword());
		
		Calendar calendar = Calendar.getInstance();
		calendar.set(Integer.valueOf(form.getExpirationYear()),Integer.valueOf(form.getExpirationMonth())-1, 1);
		Date date = calendar.getTime();
		
		CreditCard cc = creditCardService.create();
		cc.setHolder(form.getHolder());
		cc.setMake(form.getMake());
		cc.setNumber(form.getNumber());
		cc.setCVV(form.getCVV());
		cc.setExpirationDate(date);
		
		CreditCard savedCC = creditCardService.save(cc);
		
		
		res.setUserAccount(savedua);
		
		res.setAddress(actor.getAddress());
		res.setMiddleName(actor.getMiddleName());
		res.setSurname(actor.getSurname());
		res.setEmail(actor.getEmail());
		res.setName(actor.getName());
		res.setPhone(actor.getPhone());
		res.setPhoto(actor.getPhoto());
		res.setCreditCard(savedCC);

		Customer saved = customerService.save(res);
		
		boxService.createSystemBoxes(saved.getUserAccount());
				
		return saved;
	}
	
	public Market registerMarket(Actor actor, RegisterForm form) {

		Market res = marketService.create(actor.getUserAccount());
		UserAccount savedua =  userAccountService.save(actor.getUserAccount());
		
		Authority adminauth = new Authority();
		adminauth.setAuthority(Authority.ADMIN);
		Assert.isTrue(LoginService.getPrincipal().getAuthorities().contains(adminauth));
		
		System.out.println("la contraseña de la useraccount persistida es :" + savedua.getPassword());
		
		res.setUserAccount(savedua);
		
		res.setAddress(actor.getAddress());
		res.setMiddleName(actor.getMiddleName());
		res.setSurname(actor.getSurname());
		res.setEmail(actor.getEmail());
		res.setName(actor.getName());
		res.setPhone(actor.getPhone());
		res.setPhoto(actor.getPhoto());
		
		res.setVATNumber(form.getVATNumber());
		res.setCompanyName(form.getCompanyName());
		
		Market saved = marketService.save(res);
		boxService.createSystemBoxes(saved.getUserAccount());
		
		return saved;
	}
	
	public Admin registerAdmin(Actor actor) {
		
		Admin res = adminService.create(actor.getUserAccount());
		
		Authority adminauth = new Authority();
		adminauth.setAuthority(Authority.ADMIN);
		Assert.isTrue(LoginService.getPrincipal().getAuthorities().contains(adminauth));

		UserAccount savedua =  userAccountService.save(actor.getUserAccount());
		
		System.out.println("la contraseña de la useraccount persistida es :" + savedua.getPassword());
		
		res.setUserAccount(savedua);
		
		res.setAddress(actor.getAddress());
		res.setMiddleName(actor.getMiddleName());
		res.setSurname(actor.getSurname());
		res.setEmail(actor.getEmail());
		res.setName(actor.getName());
		res.setPhone(actor.getPhone());
		res.setPhoto(actor.getPhoto());
		
		Admin saved = adminService.save(res);
		
		boxService.createSystemBoxes(saved.getUserAccount());
		
		return saved;
	}
	
	public Provider registerProvider(Actor actor) {
		
		Provider res = providerService.create(actor.getUserAccount());
		
		UserAccount savedua =  userAccountService.save(actor.getUserAccount());
		
		System.out.println("la contraseña de la useraccount persistida es :" + savedua.getPassword());
		
		res.setUserAccount(savedua);
		
		res.setAddress(actor.getAddress());
		res.setMiddleName(actor.getMiddleName());
		res.setSurname(actor.getSurname());
		res.setEmail(actor.getEmail());
		res.setName(actor.getName());
		res.setPhone(actor.getPhone());
		res.setPhoto(actor.getPhoto());
		
		Provider saved = providerService.save(res);
		
		boxService.createSystemBoxes(saved.getUserAccount());
		
		return saved;
	}
	
	public DeliveryBoy registerDeliveryBoy(Actor actor) {
		
		DeliveryBoy res = deliveryBoyService.create(actor.getUserAccount());

		UserAccount savedua =  userAccountService.save(actor.getUserAccount());
		
		System.out.println("la contraseña de la useraccount persistida es :" + savedua.getPassword());
		
		res.setUserAccount(savedua);
		
		res.setAddress(actor.getAddress());
		res.setMiddleName(actor.getMiddleName());
		res.setSurname(actor.getSurname());
		res.setEmail(actor.getEmail());
		res.setName(actor.getName());
		res.setPhone(actor.getPhone());
		res.setPhoto(actor.getPhoto());
		
		DeliveryBoy saved = deliveryBoyService.save(res);
		
		boxService.createSystemBoxes(saved.getUserAccount());
		
		return saved;
	}

	public Actor reconstruct(RegisterForm form, BindingResult binding){
		
		//Creamos la cuenta de usuario.
		
		UserAccount ua = userAccountService.create();
		
		ua.setPassword(form.getPassword());
		ua.setUsername(form.getUsername());
		
		// Le asignamos la authority cosrrespondiente.
		
		Authority authority = new Authority();
		authority.setAuthority(form.getType());
		ua.addAuthority(authority);
		
		// Creamos el telefono
		
		
		// Creamos el actor con la useraccount sin persistir.
		
		Actor actor = this.create(ua);
		
		actor.setName(form.getName());
		actor.setMiddleName(form.getMiddleName());
		actor.setSurname(form.getSurname());
		
		actor.setPhoto(form.getPhoto());
		actor.setEmail(form.getEmail());
		actor.setPhone(form.getPhone());
		actor.setAddress(form.getAddress());
		
		
		validator.validate(form, binding);		
		
		if (form.getPassword().equals(form.getPassword2())==false) {
			ObjectError error = new ObjectError(form.getPassword(), "password does not match");
			binding.addError(error);
		}		
		return actor;
	}
	
	
//	public String actorToJson(Actor actor){
//		
//		EditPersonalForm form = new EditPersonalForm();
//		
//		form.setEmail(actor.getEmail());
//		form.setName(actor.getName());
//		form.setPhoto(actor.getPhoto());
//		form.setSurnames(actor.getSurnames());
//		form.setPhoneNumber(actor.getPhone());
//		
//		String res = "";
//		ObjectMapper mapper = new ObjectMapper();
//		
//        try {
//            String json = mapper.writeValueAsString(form);
//            res = json;
//            System.out.println("JSON = " + json);
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }
//		return res;
//	}
	
}