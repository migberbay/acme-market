package controllers.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CustomerService;
import services.MarketService;
import controllers.AbstractController;
import domain.Customer;
import domain.Market;

@Controller
@RequestMapping("/market/customer")
public class MarketCustomerController extends AbstractController {

	@Autowired
	private MarketService marketService;
		
	@Autowired
	private CustomerService customerService;
	
	
	// Join -----------------------------------------------------------------
	
	@RequestMapping(value = "/join", method = RequestMethod.GET)
	public ModelAndView join(@RequestParam final int marketId) {
		ModelAndView result;
		Customer logged = customerService.getPrincipal();
		Market m = marketService.findOne(marketId);

		if(!logged.getMarkets().contains(m)){
		try {
			logged.getMarkets().add(m);
			customerService.save(logged);
			result = new ModelAndView("redirect:/market/list.do");
		} catch (final Throwable oops) {
			oops.printStackTrace();
			result = new ModelAndView("redirect:/market/list.do");
		}
		}else
			result = new ModelAndView("error/access");

		return result;
	}
	
	// Leave -----------------------------------------------------------------
	
	@RequestMapping(value = "/leave", method = RequestMethod.GET)
	public ModelAndView leave(@RequestParam final int marketId) {
		ModelAndView result;
		Customer logged = customerService.getPrincipal();
		Market m = marketService.findOne(marketId);

		if(logged.getMarkets().contains(m)){
		try {
			logged.getMarkets().remove(m);
			customerService.save(logged);
			result = new ModelAndView("redirect:/market/list.do");
		} catch (final Throwable oops) {
			oops.printStackTrace();
			result = new ModelAndView("redirect:/market/list.do");
		}
		}else
			result = new ModelAndView("error/access");

		return result;
	}
		

	//Helper methods --------------------------------------------------------------------------

}
