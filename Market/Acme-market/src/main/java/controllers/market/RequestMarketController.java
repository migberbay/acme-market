package controllers.market;

import java.util.Collection;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.DepartmentService;
import services.MarketService;
import services.ProductService;
import services.RequestService;
import controllers.AbstractController;
import domain.Department;
import domain.Market;
import domain.Product;
import domain.Request;
import forms.RequestForm;

@Controller
@RequestMapping("/request/market")
public class RequestMarketController extends AbstractController {

	@Autowired
	private RequestService requestService;
		
	@Autowired
	private MarketService marketService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private DepartmentService departmentService;
	
	int productId;
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
		Product product = productService.findOne(productId);
		
		if(LoginService.hasRole("MARKET") && product.getDepartment() == null){
			RequestForm res = new RequestForm();
			try {
				this.productId = productId;
				result = createEditModelAndView(res);
			} catch (Exception e) {
				e.printStackTrace();
				result = createEditModelAndView(res,"commit.error");
			}
			
		}else{
			result =  new ModelAndView("error/access");
		}
		return result;
	}

	// Edit -----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int requestId) {
		ModelAndView result;

		Request request = requestService.findOne(requestId);
		Market logged = marketService.getPrincipal();
		RequestForm form = requestService.construct(request);
		if(request.getMarket().equals(logged))
			result = this.createEditModelAndView(form);
		else
			result = new ModelAndView("error/access");
		return result;
	}

	// Save -----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView edit(@ModelAttribute("request") RequestForm request, final BindingResult bindingResult) {
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
	}

	//Helper methods --------------------------------------------------------------------------

		
	protected ModelAndView createEditModelAndView(RequestForm request){
		ModelAndView res;
		res = createEditModelAndView(request, null);
		return res;
	}
		
	protected ModelAndView createEditModelAndView(RequestForm request, String messageCode){
			
		ModelAndView res;
		res = new ModelAndView("request/edit");
		
		Collection<Department> deps = departmentService.findDepartmentsByPrincipal();
		res.addObject("request", request);
		res.addObject("deps",deps);
		res.addObject("message", messageCode);

		return res;
	}
}
