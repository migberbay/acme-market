package controllers.market;

import java.util.AbstractMap;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.validation.ValidationException;

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
import controllers.AbstractController;
import domain.Department;
import domain.Market;
import domain.Product;

@Controller
@RequestMapping("/product/market")
public class ProductMarketController extends AbstractController {

	@Autowired
	private ProductService productService;
			
	// List -----------------------------------------------------------------
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		
		Collection<Product> products = productService.getUnassignedProducts();
		
		Map<Product,Integer> aux = new HashMap<Product, Integer>();
		Set<Entry<Product,Integer>> productsByAmount = aux.entrySet();
		
		for (Product product : products) {
			Boolean existsInSet = false;
			for (Entry<Product, Integer> entry : productsByAmount) {
				if (product.getName().equals(entry.getKey().getName()) &&
					product.getProvider().equals(entry.getKey().getProvider())) {//el producto esta en el set
					
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
		
		return result;
	}

	
}
