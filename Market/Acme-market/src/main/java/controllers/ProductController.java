
package controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import security.UserAccount;
import services.MarketService;
import services.ProductService;
import domain.Market;
import domain.Product;

@Controller
@RequestMapping("product/")
public class ProductController extends AbstractController {

	//Services

	@Autowired
	private ProductService	productService;
	
	@Autowired
	private MarketService	marketService;


	// Constructors -----------------------------------------------------------

	public ProductController() {
		super();
	}

	//Listing-----------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam(required = false) Integer marketId) {
		ModelAndView res;
		Collection<Product> products;
		
		if(marketId == null){
			 products = productService.findAll();
		}else{
			Market market = marketService.findOne(marketId);
		}
		

		res = new ModelAndView("product/list");
		res.addObject("products", products);

		return res;
	}

	//Helper methods---------------------------------------------------
//	protected ModelAndView createEditModelAndView(Product product) {
//		ModelAndView res;
//		res = this.createEditModelAndView(product, null);
//		return res;
//	}
//	protected ModelAndView createEditModelAndView(Product product,  String messageCode) {
//		ModelAndView res;
//
//		 UserAccount logged = LoginService.getPrincipal();
//
//		res = new ModelAndView("product/edit");
//		res.addObject("product", product);
//		res.addObject("products", this.productService.findByUserAccountId(logged.getId()));
//		res.addObject("errorMessage", messageCode);
//
//		return res;
//	}

}
