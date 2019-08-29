
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
import services.ProductService;
import domain.Department;
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

	//Listing-----------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam(required = false) Integer departmentId) {
		ModelAndView res;
		Collection<Product> products;
		
		if(departmentId == null){
			 products = productService.getMarketProducts();
		}else{
			products = productService.getProductsByDepartment(departmentId);
		}
		
		
		res = new ModelAndView("product/list");
		Collection<Department> deps = departmentService.findAll();
		res.addObject("departments",deps);
		res.addObject("requestURI","product/list.do");
		res.addObject("products", products);

		return res;
	}

	// filter: change filter parameters and lists packages -------------------------------------

	@RequestMapping(value="/search", method= RequestMethod.GET)
	public ModelAndView search() {
		ModelAndView result;
		StringFinderForm finder = new StringFinderForm();
		finder.setKeyword("");
		result = createEditModelAndView(finder);
		return result;
	}

	@RequestMapping(value="/search", method= RequestMethod.POST, params = "save")
	public ModelAndView filter(StringFinderForm finder, final BindingResult binding) {
		ModelAndView result;
		try {
			result = createEditModelAndView(finder);
		} catch (final Throwable oops) {
			oops.printStackTrace();
			result = createEditModelAndView(finder,
						"finder.commit.error");
			}
		return result;
	}
	
	@RequestMapping(value="/search", method= RequestMethod.POST, params = "clear")
	public ModelAndView clear(StringFinderForm finder, final BindingResult binding) {
		ModelAndView result;
		try {
			finder.setKeyword("");
			result = createEditModelAndView(finder);
		} catch (final Throwable oops) {
			oops.printStackTrace();
			result = createEditModelAndView(finder,
						"finder.commit.error");
		}
		return result;
	}

	protected ModelAndView createEditModelAndView(StringFinderForm finder){
		ModelAndView res;
		res = createEditModelAndView(finder, null);
		return res;
	}
	
	protected ModelAndView createEditModelAndView(StringFinderForm finder, String messageCode){
		ModelAndView res;
		Collection<Product> products = new HashSet<Product>();
		res = new ModelAndView("product/search");
		if(finder.getKeyword().isEmpty()){
			products.addAll(productService.getMarketProducts());
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
