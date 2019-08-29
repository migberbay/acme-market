package controllers.market;

import java.util.Collection;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.MarketService;
import services.ProductService;
import services.RequestService;
import controllers.AbstractController;
import domain.Market;
import domain.Product;
import domain.Request;

@Controller
@RequestMapping("/request/market")
public class RequestMarketController extends AbstractController {

	@Autowired
	private RequestService requestService;
		
	@Autowired
	private MarketService marketService;
	
	@Autowired
	private ProductService productService;
	
	private int productId;
	// List -----------------------------------------------------------------
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		
		Collection<Request> requests = requestService.getRequestsByPrincipal();
		
		result = new ModelAndView("request/list");
		result.addObject("requests",requests);
		result.addObject("auth",true);
		
		return result;
	}

		
	// Create -----------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam int productId) {
		ModelAndView result;
		
		Market logged = marketService.getPrincipal();
		Collection<Product> products = productService.getProductsByMarket(logged.getId());
		Product t = productService.findOne(productId);
		if(products.contains(t)){
			result = new ModelAndView("error/access");
		}else{
			Request request = requestService.create();
			this.productId=productId;
			result = this.createEditModelAndView(request);
		}
		return result;
	}

	// Edit -----------------------------------------------------------------
/*
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int requestId) {
		ModelAndView result;

		Request request = requestService.findOne(requestId);
		Market logged = marketService.findByPrincipal();
		if(request.getMarket().equals(logged))
			result = this.createEditModelAndView(request);
		else
			result = new ModelAndView("error/access");
		return result;
	}*/
/*
	// Save -----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView edit(final Request request, final BindingResult bindingResult) {
		ModelAndView result;
		Request res;
		try {
			res = requestService.reconstruct(request,this.productId ,bindingResult);
			requestService.save(res);
			result = new ModelAndView("redirect:list.do");
		} catch (ValidationException oops) {
			oops.printStackTrace();
			result = this.createEditModelAndView(request);
		} catch (final Throwable oops) {
			oops.printStackTrace();
			result = this.createEditModelAndView(request,"request.commit.error");
		}
		return result;
	}*/

	//Helper methods --------------------------------------------------------------------------

		
	protected ModelAndView createEditModelAndView(Request request){
		ModelAndView res;
		res = createEditModelAndView(request, null);
		return res;
	}
		
	protected ModelAndView createEditModelAndView(Request request, String messageCode){
			
		ModelAndView res;
		res = new ModelAndView("request/edit");

		res.addObject("request", request);
		res.addObject("message", messageCode);

		return res;
	}
}
