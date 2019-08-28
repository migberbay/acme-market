package controllers.customer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CustomerService;
import services.MarketService;
import services.ProductService;
import services.PurchaseService;
import controllers.AbstractController;
import domain.Customer;
import domain.Market;
import domain.Product;
import domain.Purchase;

@Controller
@RequestMapping("/purchase/customer")
public class PurchaseCustomerController extends AbstractController {
		
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private PurchaseService purchaseService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private MarketService marketService;
	
	// List -----------------------------------------------------------------
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		
		Collection<Purchase> purchases = purchaseService.findPurchasesByPrincipal();
		
		result = new ModelAndView("purchase/list");
		result.addObject("purchases",purchases);		
		
		return result;
	}
		
	
	// Show --------------------------------------------------------------------

		@RequestMapping(value = "/show", method = RequestMethod.GET)
		public ModelAndView show(@RequestParam final int purchaseId) {

			ModelAndView result;
			Customer c = customerService.getPrincipal();
			Purchase purchase = purchaseService.findOne(purchaseId);
			
			if(purchase.getCustomer().equals(c)){
				result = new ModelAndView("purchase/show");
				result.addObject("purchase", purchase);
			}else{
				result = new ModelAndView("error/access");
			}
			return result;
		}
	
	// Create -----------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam int marketId) {
		ModelAndView result = new ModelAndView();
		try {
			Market market = marketService.findOne(marketId);
			Purchase purchase = purchaseService.create();
			purchase.setCustomer(customerService.getPrincipal());
			purchase.setMarket(market);
			
			Purchase saved = purchaseService.save(purchase);
			
			result = this.createEditModelAndView(saved);
			result.addObject("products",productService.getProductsByMarket(marketId));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}
	
	// Edit -----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int purchaseId) {
		ModelAndView result;
		Purchase purchase = purchaseService.findOne(purchaseId);
		Customer c = customerService.getPrincipal();
		
		if(purchase.getCustomer().equals(c) && purchase.getIsFinal() == false){
			result = this.createEditModelAndView(purchase);
			result.addObject("products",productService.getProductsByMarket(purchase.getMarket().getId()));
		}else{
			result = new ModelAndView("error/access");
		}
		return result;
	}

	// Save -----------------------------------------------------------------
	@RequestMapping(value = "/finalizePurchase", method = RequestMethod.GET)
	public ModelAndView finalize(Integer purchaseId) {
		ModelAndView result;
		
		Purchase purchase = purchaseService.findOne(purchaseId);
		Customer c = customerService.getPrincipal();
		Boolean isEnough = true;
		
		System.out.println("finalizing: " + purchase);
		
		if(purchase.getCustomer().equals(c) && purchase.getIsFinal() == false){
			try {
				Map<Product,Integer> productsByAmount = new HashMap<>();
				for (Product p : purchase.getProducts()) {
					if(productsByAmount.containsKey(p)){
						productsByAmount.put(p, productsByAmount.get(p)+1);
					}else{
						productsByAmount.put(p, 1);
					}
				}
				System.out.println(productsByAmount);
				
				List<Product> overOrderedProducts = new ArrayList<>();
				for (Entry<Product, Integer> e : productsByAmount.entrySet()) {
					if(e.getValue()>e.getKey().getStock()){//ha pedido mas del producto que del stock que hay.
						isEnough = false;
						overOrderedProducts.add(e.getKey());
					}
				}
				
				if(isEnough){
					purchase.setIsFinal(true);
					purchaseService.save(purchase);
					
					for (Entry<Product, Integer> e : productsByAmount.entrySet()) {
						e.getKey().setStock(e.getKey().getStock()-e.getValue());
						productService.save(e.getKey());
					}
					
					result = list();
				}else{
					result = edit(purchaseId);
					result.addObject("isEnough", isEnough);
					result.addObject("overOrderedProducts", overOrderedProducts);
				}
				
			} catch (Exception e) {
				result = edit(purchaseId);
				result.addObject("message", "commit.error");
				e.printStackTrace();
			}
		}else{
			result = new ModelAndView("error/access");
		}
		return result;
	}
	
	@RequestMapping(value = "/addProduct", method = RequestMethod.GET)
	public ModelAndView addProduct(Integer purchaseId, Integer productId) {
		ModelAndView result;
		
		Purchase purchase = purchaseService.findOne(purchaseId);
		Product product = productService.findOne(productId);
		Customer c = customerService.getPrincipal();
		
		System.out.println("adding: " + product + "to: " + purchase);
		
		if(purchase.getCustomer().equals(c) && purchase.getIsFinal() == false){
			try {
				purchase.getProducts().add(product);
				purchaseService.save(purchase);
				
				result = edit(purchaseId);
			} catch (Exception e) {
				result = edit(purchaseId);
				result.addObject("message", "commit.error");
				e.printStackTrace();
			}
		}else{
			result = new ModelAndView("error/access");
		}
		return result;
	}
	
	@RequestMapping(value = "/removeProduct", method = RequestMethod.GET)
	public ModelAndView removeProduct(Integer purchaseId, Integer productId) {
		ModelAndView result;
		
		Purchase purchase = purchaseService.findOne(purchaseId);
		Product product = productService.findOne(productId);
		Customer c = customerService.getPrincipal();
		
		System.out.println("removing: " + product + "from: " + purchase);
		
		if(purchase.getCustomer().equals(c) && purchase.getIsFinal() == false){
			try {
				purchase.getProducts().remove(product);
				purchaseService.save(purchase);
				
				result = edit(purchaseId);
			} catch (Exception e) {
				result = edit(purchaseId);
				result.addObject("message", "commit.error");
				e.printStackTrace();
			}
		}else{
			result = new ModelAndView("error/access");
		}
		return result;
	}
	

	// Delete -----------------------------------------------------------------

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int purchaseId) {
		ModelAndView result;
		Customer logged = customerService.getPrincipal();
		Purchase purchase = purchaseService.findOne(purchaseId);

		if(purchase.getCustomer().equals(logged) && purchase.getIsFinal() == false){
		try {
			result = new ModelAndView("redirect:/purchase/customer/list.do");
			purchaseService.delete(purchase);
		} catch (final Throwable oops) {
			oops.printStackTrace();
			result = this.createEditModelAndView(purchase, "commit.error");
		}
		}else
			result = new ModelAndView("error/access");

		return result;
	}

	//Helper methods --------------------------------------------------------------------------

		
	protected ModelAndView createEditModelAndView(Purchase purchase){
		ModelAndView res;
		res = createEditModelAndView(purchase, null);
		return res;
	}
		
	protected ModelAndView createEditModelAndView(Purchase purchase, String messageCode){
		ModelAndView res;
		
		res = new ModelAndView("purchase/edit");
		res.addObject("purchase", purchase);
		res.addObject("message", messageCode);

		return res;
	}

}
