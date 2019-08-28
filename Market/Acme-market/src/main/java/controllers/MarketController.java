
package controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.Authority;
import security.LoginService;
import security.UserAccount;
import services.CustomerService;
import services.MarketService;
import domain.Market;

@Controller
@RequestMapping("market/")
public class MarketController extends AbstractController {

	//Services

	@Autowired
	private MarketService	marketService;

	@Autowired
	private CustomerService customerService;

	//Listing-----------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView res;
		Collection<Market> markets = marketService.findAll();
		Boolean isCustomer = false;
		res = new ModelAndView("market/list");

		try {
			if(LoginService.hasRole("CUSTOMER")){
				isCustomer = true;
				res.addObject("customer",customerService.getPrincipal());
			}
		} catch (Exception e) {
			System.out.println("actor not logged.");
		}
		
		res.addObject("markets", markets);
		res.addObject("isCustomer",isCustomer);

		return res;
	}

	//Helper methods---------------------------------------------------
//	protected ModelAndView createEditModelAndView(Market market) {
//		ModelAndView res;
//		res = this.createEditModelAndView(market, null);
//		return res;
//	}
//	protected ModelAndView createEditModelAndView(Market market,  String messageCode) {
//		ModelAndView res;
//
//		 UserAccount logged = LoginService.getPrincipal();
//
//		res = new ModelAndView("market/edit");
//		res.addObject("market", market);
//		res.addObject("markets", this.marketService.findByUserAccountId(logged.getId()));
//		res.addObject("errorMessage", messageCode);
//
//		return res;
//	}

}
