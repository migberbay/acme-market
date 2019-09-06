package controllers.provider;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.MarketService;
import services.ProductService;
import services.ProviderService;
import controllers.AbstractController;
import domain.Product;
import forms.ProductForm;

@Controller
@RequestMapping("/product/provider")
public class ProductProviderController extends AbstractController {
		
	@Autowired
	private ProviderService providerService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private MarketService marketService;
	
	// List -----------------------------------------------------------------
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		
		Collection<Product> products = productService.findProductsByPrincipal();
		
		Map<Product,Integer> aux = new HashMap<Product, Integer>();
		Set<Entry<Product,Integer>> productsByAmount = aux.entrySet();
		
		try {
			for (Product product : products) {
				Boolean existsInSet = false;
				for (Entry<Product, Integer> entry : productsByAmount) {
					if (product.getName().equals(entry.getKey().getName())) {//el producto esta en el set
						
						entry.setValue(entry.getValue()+1);
						existsInSet = true;
						break;
					}
				}
				if(!existsInSet){
					aux.put(product, 1);
					productsByAmount = aux.entrySet();
				}
			}
			
			result = new ModelAndView("product/list");
			result.addObject("products",productsByAmount);
			result.addObject("auth",true);
			result.addObject("requestURI","product/provider/list.do");
		} catch (Exception e) {
			e.printStackTrace();
			result = new ModelAndView("error/access");
		}
		
		
		return result;
	}
		
	
	// Create -----------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;

		ProductForm form =  new ProductForm();
		form.setCreating(true);
		result = this.createEditModelAndView(form);

		return result;
	}
//	
//	// Edit -----------------------------------------------------------------
//
//	@RequestMapping(value = "/edit", method = RequestMethod.GET)
//	public ModelAndView edit(@RequestParam final int productId) {
//		ModelAndView result;
//		Product product = productService.findOne(productId);	
//		Provider logged = providerService.getPrincipal();
//		
//		if(product.getProvider().equals(logged)){
//			result = this.createEditModelAndView(product);
//		}else
//			result = new ModelAndView("error/access");
//
//		return result;
//	}
	
	
	// Add stock -----------------------------------------------------------------

	@RequestMapping(value = "/addStock", method = RequestMethod.GET)
	public ModelAndView addStock(@RequestParam final int productId) {
		ModelAndView result;
		Product product = productService.findOne(productId);	
		
		ProductForm form =  new ProductForm();
		form.setCreating(false);
		form.setName(product.getName());
		form.setPrice(product.getPrice());
		form.setPacketSize(product.getStock());
		result = this.createEditModelAndView(form);

		return result;
	}

	// Save -----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView edit(@ModelAttribute("form")ProductForm form, final BindingResult bindingResult) {
		ModelAndView result;
		Collection<Product> res;
		Collection <Product> aux = productService.getProductsByNameAndProvider(form.getName(), providerService.getPrincipal().getId());
		
		if(form.getCreating() == true && aux.isEmpty()){//no hay colision
			try {
				res = productService.reconstruct(form,bindingResult);
				for (Product p : res) {
					productService.save(p);
				}
				result = new ModelAndView("redirect:/product/provider/list.do");
			} catch (ValidationException oops) {
				oops.printStackTrace();
				result = this.createEditModelAndView(form);
			} catch (final Throwable oops) {
				oops.printStackTrace();
				result = this.createEditModelAndView(form,"product.commit.error");
			}
		}else{
			result = list();
			result.addObject("nameCollision",true);
		}
		
		return result;
	}
	

//	// Delete -----------------------------------------------------------------
//
//	@RequestMapping(value = "/delete", method = RequestMethod.GET)
//	public ModelAndView delete(@RequestParam final int productId) {
//		ModelAndView result;
//		Provider logged = providerService.getPrincipal();
//		Product product = productService.findOne(productId);
//
//		if(product.getProvider().equals(logged)){
//		try {
//			result = new ModelAndView("redirect:/product/provider/list.do");
//			productService.delete(product);
//		} catch (final Throwable oops) {
//			oops.printStackTrace();
//			result = this.createEditModelAndView(product, "product.commit.error");
//		}
//		}else
//			result = new ModelAndView("error/access");
//
//		return result;
//	}

	//Helper methods --------------------------------------------------------------------------

		
	protected ModelAndView createEditModelAndView(ProductForm form){
		ModelAndView res;
		res = createEditModelAndView(form, null);
		return res;
	}
		
	protected ModelAndView createEditModelAndView(ProductForm form, String messageCode){
		ModelAndView res;
		res = new ModelAndView("product/edit");
		if(form.getName()!=null){
			if(!form.getCreating()) 
				res.addObject("stock",true); else res.addObject("stock",false);
		}else{
			res.addObject("stock",false);
		}
		res.addObject("form", form);
		res.addObject("message", messageCode);

		return res;
	}

}
