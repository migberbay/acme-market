
package controllers;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.DepartmentService;
import services.ProductService;
import domain.Department;
import domain.Product;

@Controller
@RequestMapping("department/")
public class DepartmentController extends AbstractController {

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

}
