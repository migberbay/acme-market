
package controllers;

import java.util.Collection;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.DepartmentService;
import services.MarketService;
import services.ProductService;
import domain.Department;
import domain.Market;
import domain.Product;
import forms.StringFinderForm;

@Controller
@RequestMapping("product/")
public class ProductController extends AbstractController {

	//Services

	@Autowired
	private ProductService	productService;
	
	@Autowired
	private DepartmentService departmentService;
	
	@Autowired
	private MarketService marketService;


	
	// Show --------------------------------------------------------------------

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int productId) {

		ModelAndView result;
		result = new ModelAndView("product/show");

		Product product = productService.findOne(productId);
		if(product.getDepartment()!=null){
			Market market = marketService.getMarketByProduct(productId);
			result.addObject("market",market);
		}
		result.addObject("product", product);
		result.addObject("uri", "product/search.do");

		return result;
	}


	// filter: change filter parameters and lists packages -------------------------------------

	@RequestMapping(value="/search", method= RequestMethod.GET)
	public ModelAndView search(@RequestParam(required = false) Integer departmentId) {
		ModelAndView result;
		StringFinderForm finder = new StringFinderForm();
		finder.setKeyword("");
		result = createEditModelAndView(finder, departmentId);
		return result;
	}

	@RequestMapping(value="/search", method= RequestMethod.POST, params = "save")
	public ModelAndView filter(StringFinderForm finder, final BindingResult binding) {
		ModelAndView result;
		try {
			result = createEditModelAndView(finder, null);
		} catch (final Throwable oops) {
			oops.printStackTrace();
			result = createEditModelAndView(finder,null,"finder.commit.error");
			}
		return result;
	}
	
	@RequestMapping(value="/search", method= RequestMethod.POST, params = "clear")
	public ModelAndView clear(StringFinderForm finder, final BindingResult binding) {
		ModelAndView result;
		try {
			finder.setKeyword("");
			result = createEditModelAndView(finder,null);
		} catch (final Throwable oops) {
			oops.printStackTrace();
			result = createEditModelAndView(finder,null,"finder.commit.error");
		}
		return result;
	}

	protected ModelAndView createEditModelAndView(StringFinderForm finder, Integer departmentId){
		ModelAndView res;
		res = createEditModelAndView(finder, departmentId, null);
		return res;
	}
	
	protected ModelAndView createEditModelAndView(StringFinderForm finder, Integer departmentId, String messageCode){
		ModelAndView res;
		Collection<Product> products = new HashSet<Product>();
		res = new ModelAndView("product/search");
		
		if(finder.getKeyword().isEmpty() && departmentId == null){
			products.addAll(productService.getMarketProducts());
		}else if(finder.getKeyword().isEmpty() && departmentId != null){
			products = productService.getProductsByDepartment(departmentId);
			System.out.println("LIST DEPARTMENTS " + products);
		}else{
			products.addAll(productService.searchProducts(finder.getKeyword()));
		}

		Collection<Department> deps = departmentService.findAll();
		res.addObject("departments",deps);
		res.addObject("stringFinderForm",finder);
		res.addObject("products", products);
		res.addObject("requestURI","product/search.do");
		res.addObject("message", messageCode);

		return res;
	}

}
