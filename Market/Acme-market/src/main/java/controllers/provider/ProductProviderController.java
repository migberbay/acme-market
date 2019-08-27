package controllers.provider;

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

import services.ProductService;
import services.ProviderService;
import controllers.AbstractController;
import domain.Product;
import domain.Provider;

@Controller
@RequestMapping("/product/provider")
public class ProductProviderController extends AbstractController {
		
	@Autowired
	private ProviderService providerService;
	
	@Autowired
	private ProductService productService;
	
	// List -----------------------------------------------------------------
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		
		Collection<Product> products = productService.findProductsByPrincipal();
		
		result = new ModelAndView("product/list");
		result.addObject("products",products);		
		
		return result;
	}
		
	
	// Show --------------------------------------------------------------------

		@RequestMapping(value = "/show", method = RequestMethod.GET)
		public ModelAndView show(@RequestParam final int productId) {

			ModelAndView result;

			Product product = productService.findOne(productId);

			result = new ModelAndView("product/show");
			result.addObject("product", product);
			result.addObject("uri", "product/provider/list.do");

			return result;
		}
	
	// Create -----------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Product product = productService.create();

		Provider logged = providerService.getPrincipal();
		if(product.getProvider().equals(logged))
			result = this.createEditModelAndView(product);
		else
			result = new ModelAndView("error/access");
		
		return result;
	}
	
	// Edit -----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int productId) {
		ModelAndView result;
		Product product = productService.findOne(productId);	
		Provider logged = providerService.getPrincipal();
		
		if(product.getProvider().equals(logged)){
			result = this.createEditModelAndView(product);
		}else
			result = new ModelAndView("error/access");

		return result;
	}

	// Save -----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView edit(@ModelAttribute("product")Product product, final BindingResult bindingResult) {
		ModelAndView result;
		Product res;
		try {
			res = productService.reconstruct(product,bindingResult);
			productService.save(res);
			result = new ModelAndView("redirect:/product/provider/list.do");
		} catch (ValidationException oops) {
			oops.printStackTrace();
			result = this.createEditModelAndView(product);
		} catch (final Throwable oops) {
			oops.printStackTrace();
			result = this.createEditModelAndView(product,"file.commit.error");
		}
		return result;
	}
	

	// Delete -----------------------------------------------------------------

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int productId) {
		ModelAndView result;
		Provider logged = providerService.getPrincipal();
		Product product = productService.findOne(productId);

		if(product.getProvider().equals(logged)){
		try {
			result = new ModelAndView("redirect:/product/provider/list.do");
			productService.delete(product);
		} catch (final Throwable oops) {
			oops.printStackTrace();
			result = this.createEditModelAndView(product, "product.commit.error");
		}
		}else
			result = new ModelAndView("error/access");

		return result;
	}

	//Helper methods --------------------------------------------------------------------------

		
	protected ModelAndView createEditModelAndView(Product product){
		ModelAndView res;
		res = createEditModelAndView(product, null);
		return res;
	}
		
	protected ModelAndView createEditModelAndView(Product product, String messageCode){
		ModelAndView res;
		
		res = new ModelAndView("product/edit");;
		res.addObject("product", product);
		res.addObject("message", messageCode);

		return res;
	}

}
