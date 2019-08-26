package controllers.actor;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.Authority;
import security.LoginService;
import services.ActorService;
import services.AdminService;
import services.ConfigurationService;
import services.CreditCardService;
import services.CustomerService;
import services.DeliveryBoyService;
import services.MarketService;
import services.ProviderService;
import controllers.AbstractController;
import domain.Actor;
import domain.Admin;
import domain.CreditCard;
import domain.Customer;
import domain.DeliveryBoy;
import domain.Market;
import domain.Provider;
import forms.EditCreditForm;
import forms.EditPersonalForm;

@Controller
@RequestMapping("actor/")
public class ProfileActorController extends AbstractController {

	// Services ----------------------------------------------------------------

	@Autowired
	private ActorService actorService;
	
	@Autowired
	private ConfigurationService configurationService;

	@Autowired
	private AdminService adminService;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private MarketService marketService;
	
	@Autowired
	private DeliveryBoyService deliveryBoyService;
	
	@Autowired
	private ProviderService providerService;
	
	@Autowired
	private CreditCardService cardService;
	
	@Autowired
	private Validator validator;
	

	// Show --------------------------------------------------------------------

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam(required = false) Integer actorId) {

		ModelAndView result;

		// Diferentes autoridades:
		Authority adminAuth = new Authority();
		adminAuth.setAuthority("ADMIN");

		Authority customerAuth = new Authority();
		customerAuth.setAuthority("CUSTOMER");

		Authority marketAuth = new Authority();
		marketAuth.setAuthority("MARKET");
		
		Authority deliveryBoyAuth = new Authority();
		deliveryBoyAuth.setAuthority("DELIVERYBOY");
		
		Authority providerAuth = new Authority();
		providerAuth.setAuthority("PROVIDER");

		Actor principal;
		
		try {
			principal = actorService.getPrincipal();
		} catch (Exception e) {
			principal = null;
		}

		result = new ModelAndView("actor/show");

		if (actorId != null) { //accedemos al perfil de otro actor
			Actor actor = actorService.findOne(actorId);
				
			result.addObject("actor", actor); // actor que se va a mostrar
			result.addObject("logged", false); // flag para permitir editar
			
			
		} else {//accedemos a nuestro perfil
			Actor actor = actorService.getPrincipal();
			
			System.out.println(actor);
			
			result.addObject("logged", true);
			result.addObject("actor", actor);
			
			if (actor.getUserAccount().getAuthorities().contains(adminAuth)) {
				Admin admin = adminService.findOne(actor.getId());
				result.addObject("actor", admin);
				result.addObject("actorIsAdmin",true);
			}

			if (actor.getUserAccount().getAuthorities().contains(customerAuth)) {
				Customer customer = customerService.findOne(actor.getId());
				result.addObject("actor", customer);
				result.addObject("actorIsCustomer",true);
			}

			if (actor.getUserAccount().getAuthorities().contains(marketAuth)) {
				Market market = marketService.findOne(actor.getId());
				result.addObject("actor", market);
				result.addObject("actorIsMarket",true);
			}
			
			if (actor.getUserAccount().getAuthorities().contains(deliveryBoyAuth)) {
				DeliveryBoy deliveryBoy = deliveryBoyService.findOne(actor.getId());
				result.addObject("actor", deliveryBoy);
				result.addObject("actorIsDeliveryBoy",true);
			}
			
			if (actor.getUserAccount().getAuthorities().contains(providerAuth)) {
				Provider provider = providerService.findOne(actor.getId());
				result.addObject("actor", provider);
				result.addObject("actorIsProvidert",true);
			}
		}
		
		result.addObject("requestURI", "actor/show.do");

		return result;
	}

	// Edit -----------------------------------------------------------------

	@RequestMapping(value = "/editPersonal", method = RequestMethod.GET)
	public ModelAndView editPersonal() {
		ModelAndView result = new ModelAndView();
		EditPersonalForm f = new EditPersonalForm();
		Actor a = actorService.getPrincipal();
		
		Authority marketAuth = new Authority();
		marketAuth.setAuthority(Authority.MARKET);
		
		if(LoginService.getPrincipal().getAuthorities().contains(marketAuth)){
			Market m = marketService.getPrincipal();
			f.setVATNumber(m.getVATNumber());
			f.setCompanyName(m.getCompanyName());
			result.addObject("isMarket",true);
		}

		
		f.setName(a.getName());
		f.setMiddleName(a.getMiddleName());
		f.setSurname(a.getSurname());
		f.setPhone(a.getPhone());
		f.setEmail(a.getEmail());
		f.setAddress(a.getAddress());
		f.setPhoto(a.getPhoto());
		

		result = createEditModelAndView(f,"personal");

		return result;
	}
	
	@RequestMapping(value = "/editCreditCard", method = RequestMethod.GET)
	public ModelAndView editCredit() {

		ModelAndView result = new ModelAndView();
		
		Authority customerAuth = new Authority();
		customerAuth.setAuthority("CUSTOMER");
		
		if (LoginService.getPrincipal().getAuthorities().contains(customerAuth)) {
			
		
		EditCreditForm f = new EditCreditForm();
		Customer c = customerService.getPrincipal();
		
		f.setCVV(c.getCreditCard().getCVV());
		f.setHolder(c.getCreditCard().getHolder());
		f.setMake(c.getCreditCard().getMake());
		f.setNumber(c.getCreditCard().getNumber());
		f.setExpirationMonth(c.getCreditCard().getExpirationDate().getMonth()+1);
		f.setExpirationYear(c.getCreditCard().getExpirationDate().getYear()+1900);
		

		result = createEditModelAndView(f,"credit");
		}else{
			result = new ModelAndView("error/access");
		}
		
		return result;
	}

	// Save -----------------------------------------------------------------
	// Personal Data
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "savePersonal")
	public ModelAndView savePersonal(@ModelAttribute("form") EditPersonalForm form, final BindingResult binding) {
		ModelAndView res;
		Actor a = actorService.getPrincipal();
		
		validator.validate(form, binding);
		
		if (binding.hasErrors()) {
			System.out.println(binding);
			res = new ModelAndView("actor/edit");
			res.addObject("form", form);
			
			Date d = new Date();
			Collection <Integer> months = new ArrayList<>();
			Collection <Integer> years = new ArrayList<>();
			for (int i = 0; i < 11; i++) {
				months.add(i+1);
				years.add(d.getYear()+i+1900);
			}
			
			res.addObject("showPersonal", true);
			res.addObject("months",months);
			res.addObject("years",years);
			
			return res;
		} else {
			try {

			Authority marketAuth = new Authority();
			marketAuth.setAuthority("MARKET");
			
			if (LoginService.getPrincipal().getAuthorities().contains(marketAuth)) {
				Market m = marketService.getPrincipal();
				
				m.setName(form.getName());
				m.setMiddleName(form.getMiddleName());
				m.setSurname(form.getSurname());
				m.setPhone(form.getPhone());
				m.setEmail(form.getEmail());
				m.setAddress(form.getAddress());
				m.setPhoto(form.getPhoto());
				
				m.setVATNumber(form.getVATNumber());
				m.setCompanyName(form.getCompanyName());
				
				marketService.save(m);
			}else{
				a.setName(form.getName());
				a.setMiddleName(form.getMiddleName());
				a.setSurname(form.getSurname());
				a.setPhone(form.getPhone());
				a.setEmail(form.getEmail());
				a.setAddress(form.getAddress());
				a.setPhoto(form.getPhoto());
				
				actorService.save(a);
			}
				
			
		
		res = new ModelAndView("redirect:show.do");
		
		return res;
		
	} catch (final Throwable oops) {
		oops.printStackTrace();
		res = this.editPersonal();
		return res;
		}
	}
}
	
	// CreditCard
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "saveCredit")
	public ModelAndView saveCreditCard(@ModelAttribute("form") EditCreditForm form, final BindingResult binding) {
		ModelAndView res;
		
		Boolean expired = false;
		Calendar cal = Calendar.getInstance();
		
		if(form.getExpirationYear()== cal.get(Calendar.YEAR) && form.getExpirationMonth()-1<=cal.get(Calendar.MONTH)){
			expired = true;
		}
		
		validator.validate(form, binding);
		
		if (binding.hasErrors()|| expired) {
			System.out.println(binding);
			res = new ModelAndView("actor/edit");
			
			res.addObject("isExpired", expired);
			res.addObject("form", form);
			
			Date d = new Date();
			Collection <Integer> months = new ArrayList<>();
			Collection <Integer> years = new ArrayList<>();
			for (int i = 0; i < 11; i++) {
				months.add(i+1);
				years.add(d.getYear()+i+1900);
			}
			
			res.addObject("showCredit", true);
			res.addObject("months",months);
			res.addObject("years",years);

			return res;
		} else {
			try {
	
			Customer c = customerService.getPrincipal();			
			CreditCard credit = c.getCreditCard();
			
			Calendar calendar = Calendar.getInstance();
			calendar.set(form.getExpirationYear(), form.getExpirationMonth()-1, 1);
			Date date = calendar.getTime();
			
			credit.setCVV(form.getCVV());
			credit.setExpirationDate(date);
			credit.setHolder(form.getHolder());
			credit.setMake(form.getMake());
			credit.setNumber(form.getNumber());
			
			CreditCard CC = cardService.save(credit);
			
			c.setCreditCard(CC);
			customerService.save(c);

		res = new ModelAndView("redirect:show.do"); 
		return res;
		
	} catch (final Throwable oops) {
		oops.printStackTrace();
		res = this.editCredit();
		return res;
		}
	}
}

//Generate JSON---------------------------------------------------------------------
//	@RequestMapping(value = "/generateData", method = RequestMethod.GET)
//	public ModelAndView generate() {
//		
//		ModelAndView res = new ModelAndView("actor/personalData");
//
//		Actor actor = actorService.getPrincipal();
//		
//		res.addObject("data", actorService.actorToJson(actor));
//
//		return res;
//	}

	
//Ancillary---------------------------------------------------------------------
	protected ModelAndView createEditModelAndView(Object form, String type) {
		ModelAndView result;
		result = this.createEditModelAndView(form, type ,null);
		return result;
	}

	private ModelAndView createEditModelAndView(Object form, String type,  String message) {

		ModelAndView result = new ModelAndView("actor/edit");
		
		if(type == "personal"){
			EditPersonalForm f1 = (EditPersonalForm) form;
			result.addObject("form", f1);
		}
		if(type == "credit"){
			EditCreditForm f2 = (EditCreditForm) form;
			result.addObject("form", f2);
		}
		
		Date d = new Date();
		Collection <Integer> months = new ArrayList<>();
		Collection <Integer> years = new ArrayList<>();
		for (int i = 0; i < 12; i++) {
			months.add(i+1);
			years.add(d.getYear()+i+1900);
		}
		
		result.addObject("message", message);
		result.addObject("showCredit", type=="credit");
		result.addObject("showPersonal", type=="personal");
		result.addObject("months",months);
		result.addObject("years",years);
		
		return result;
	}
}