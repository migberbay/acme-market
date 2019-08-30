package controllers.market;

import java.util.ArrayList;
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

import services.MarketService;
import services.DepartmentService;
import services.MarketService;
import services.ProductService;
import controllers.AbstractController;
import domain.Market;
import domain.Department;
import domain.Market;
import domain.Product;

@Controller
@RequestMapping("/department/market")
public class DepartmentMarketController extends AbstractController {
		
	@Autowired
	private MarketService marketService;
	
	@Autowired
	private DepartmentService departmentService;
	
	@Autowired
	private ProductService productService;
	
	
	// List -----------------------------------------------------------------
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		
		Collection<Department> departments = departmentService.findDepartmentsByPrincipal();
		Collection<Department> deps = new ArrayList<>();
		for(Department d: departments){
			Collection<Product> products = productService.getProductsByDepartment(d.getId());
			if(products.isEmpty()){
				deps.add(d);
			}
		}
		result = new ModelAndView("department/list");
		result.addObject("departments",departments);
		result.addObject("deps",deps);
		result.addObject("requestURI","department/market/list.do");
		
		return result;
	}
		
	
	// Show --------------------------------------------------------------------

		@RequestMapping(value = "/show", method = RequestMethod.GET)
		public ModelAndView show(@RequestParam final int departmentId) {

			ModelAndView result;
			result = new ModelAndView("department/show");

			Department department = departmentService.findOne(departmentId);
			
			Collection<Product> products = productService.getProductsByDepartment(departmentId);

			result.addObject("products",products);		
			result.addObject("department", department);
			result.addObject("uri", "department/market/list.do");

			return result;
		}
	
	// Create -----------------------------------------------------------------

		@RequestMapping(value = "/create", method = RequestMethod.GET)
		public ModelAndView create() {
			ModelAndView result;
			Department department = departmentService.create();

	//		Market logged = marketService.getPrincipal();
			result = this.createEditModelAndView(department);

			return result;
		}
	
	// Edit -----------------------------------------------------------------

		@RequestMapping(value = "/edit", method = RequestMethod.GET)
		public ModelAndView edit(@RequestParam final int departmentId) {
			ModelAndView result;
			Department department = departmentService.findOne(departmentId);	
			Market logged = marketService.getPrincipal();
			Collection<Product> products = productService.getProductsByDepartment(departmentId);
		
			if(department.getMarket().equals(logged)){
				result = this.createEditModelAndView(department);
			}else
				result = new ModelAndView("error/access");

			return result;
		}

	// Save -----------------------------------------------------------------

		@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
		public ModelAndView edit(@ModelAttribute("department")Department department, final BindingResult bindingResult) {
			ModelAndView result;
			Department res;
			try {
				res = departmentService.reconstruct(department,bindingResult);
				departmentService.save(res);
				result = new ModelAndView("redirect:/department/market/list.do");
			} catch (ValidationException oops) {
				oops.printStackTrace();
				result = this.createEditModelAndView(department);
			} catch (final Throwable oops) {
				oops.printStackTrace();
				result = this.createEditModelAndView(department,"department.commit.error");
			}
			return result;
		}
	

	// Delete -----------------------------------------------------------------

		@RequestMapping(value = "/delete", method = RequestMethod.GET)
		public ModelAndView delete(@RequestParam final int departmentId) {
			ModelAndView result;
			Market logged = marketService.getPrincipal();
			Department department = departmentService.findOne(departmentId);
			Collection<Product> products = productService.getProductsByDepartment(departmentId);
			
			if(department.getMarket().equals(logged) && products.isEmpty()){
				try {
					result = new ModelAndView("redirect:/department/market/list.do");
					departmentService.delete(department);
				} catch (final Throwable oops) {
					oops.printStackTrace();
					result = this.createEditModelAndView(department, "department.commit.error");
				}
			}else
				result = new ModelAndView("error/access");

			return result;
		}

	//Helper methods --------------------------------------------------------------------------

		
	protected ModelAndView createEditModelAndView(Department department){
		ModelAndView res;
		res = createEditModelAndView(department, null);
		return res;
	}
		
	protected ModelAndView createEditModelAndView(Department department, String messageCode){
		ModelAndView res;
		
		res = new ModelAndView("department/edit");
		if(department.getId()!=0){
			Collection<Product> products = productService.getProductsByDepartment(department.getId());
			res.addObject("isEmpty",products.isEmpty());
		}
		res.addObject("department", department);
		res.addObject("message", messageCode);

		return res;
	}
	
	private void applyDiscount(Department dep){
		System.out.println("DISCOUNTS");
		Department old = departmentService.findOne(dep.getId());
		Collection<Product> products = productService.getProductsByDepartment(dep.getId());
		System.out.println("LIST PRODUCTS DEPARTAMENT " + products);
		for(Product p: products){
			System.out.println("FOR P " + p.getPrice());
			System.out.println("dISCOUNTS DEP " + dep.getDiscount() + " DISCOUNT OLD " + old.getDiscount());
			Double or = Math.abs(1/(p.getPrice()-old.getDiscount()));
			if(dep.getDiscount()>old.getDiscount()) p.setPrice(or -(or*dep.getDiscount()));
			if(dep.getDiscount()<old.getDiscount()) p.setPrice(or +(or*dep.getDiscount()));
			System.out.println("FOR P 2 " + p.getPrice());
			productService.save(p);
		}
	}

}
