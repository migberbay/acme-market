package controllers.provider;

import java.util.ArrayList;
import java.util.Collection;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.DepartmentService;
import services.ProviderService;
import services.ProductService;
import services.RequestService;
import controllers.AbstractController;
import domain.Department;
import domain.Provider;
import domain.Product;
import domain.Request;

@Controller
@RequestMapping("/request/provider")
public class RequestProviderController extends AbstractController {

	@Autowired
	private RequestService requestService;
		
	@Autowired
	private ProviderService providerService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private DepartmentService departmentService;
	
	private int productId;
	// List -----------------------------------------------------------------
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		
		Collection<Request> requests = requestService.getRequestsByProvider(providerService.getPrincipal().getId());
		
		result = new ModelAndView("request/list");
		result.addObject("requests",requests);
		result.addObject("auth",true);
		
		return result;
	}

	// Edit -----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int requestId) {
		ModelAndView result;

		Request request = requestService.findOne(requestId);
		Provider logged = providerService.getPrincipal();
		if(request.getProvider().equals(logged))
			result = this.createEditModelAndView(request);
		else
			result = new ModelAndView("error/access");
		return result;
	}

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
	}

	//Helper methods --------------------------------------------------------------------------

		
	protected ModelAndView createEditModelAndView(Request request){
		ModelAndView res;
		res = createEditModelAndView(request, null);
		return res;
	}
		
	protected ModelAndView createEditModelAndView(Request request, String messageCode){
			
		ModelAndView res;
		res = new ModelAndView("request/edit");
		Collection<String> items = new ArrayList<>();
		items.add("ACCEPTED"); items.add("REJECTED");
		res.addObject("request", request);
		res.addObject("items",items);
		res.addObject("message", messageCode);

		return res;
	}
}
