
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

import security.LoginService;
import security.UserAccount;
import services.MarketService;
import domain.Market;

@Controller
@RequestMapping("market/")
public class MarketController extends AbstractController {

	//Services

	@Autowired
	private MarketService	marketService;


	// Constructors -----------------------------------------------------------

	public MarketController() {
		super();
	}

	//Listing-----------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView res;
		Collection<Market> markets = marketService.findAll();

		res = new ModelAndView("market/list");
		res.addObject("markets", markets);

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
