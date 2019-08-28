package controllers.deliveryBoy;

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

import services.DeliveryBoyService;
import services.MarketService;
import services.ProductService;
import services.PurchaseService;
import controllers.AbstractController;
import domain.DeliveryBoy;
import domain.Market;
import domain.Product;
import domain.Purchase;

@Controller
@RequestMapping("/purchase/deliveryBoy")
public class PurchaseDeliveryBoyController extends AbstractController {
		
	@Autowired
	private DeliveryBoyService deliveryBoyService;
	
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
		
		Collection<Purchase> purchases = purchaseService.findUnasigned();
		
		result = new ModelAndView("purchase/list");
		result.addObject("purchases",purchases);
		result.addObject("principal",deliveryBoyService.getPrincipal());
		result.addObject("requestURI","purchase/deliveryBoy/list.do");
		
		return result;
	}


	// Save -----------------------------------------------------------------	
	@RequestMapping(value = "/assign", method = RequestMethod.GET)
	public ModelAndView assignPurchase(Integer purchaseId) {
		ModelAndView result;
		
		Purchase purchase = purchaseService.findOne(purchaseId);
		DeliveryBoy deliveryBoy = deliveryBoyService.getPrincipal();
		
		System.out.println("assigning: " + purchase + "to: " + deliveryBoy);
		System.out.println(purchase.getStatus() + " " +purchase.getIsFinal());
		
		if(purchase.getStatus().equals("PENDING") && purchase.getIsFinal() == true){
			try {
				purchase.setStatus("ASSIGNED");
				purchase.setEstimatedDate(purchaseService.calculateDate(purchase));
				purchase.setIsAssigned(true);
				purchase.setDeliveryBoy(deliveryBoy);
				Purchase saved = purchaseService.save(purchase);
				Collection<Purchase> purchases = deliveryBoy.getPurchases();
				System.out.println("before add: " +purchases);
				purchases.add(saved);
				System.out.println("after add: " +purchases);
				
				deliveryBoy.setPurchases(purchases);
				deliveryBoy = deliveryBoyService.save(deliveryBoy);
				System.out.println(deliveryBoy.getPurchases());
				result = list();
			} catch (Exception e) {
				result = list();
				result.addObject("message", "commit.error");
				e.printStackTrace();
			}
		}else{
			result = new ModelAndView("error/access");
		}
		return result;
	}
	
	@RequestMapping(value = "/setInTransit", method = RequestMethod.GET)
	public ModelAndView setPurchaseInTransit(Integer purchaseId) {
		ModelAndView result;
		
		Purchase purchase = purchaseService.findOne(purchaseId);
		
		System.out.println("setting: " + purchase + "to in transit");
		
		if(purchase.getStatus().equals("ASSIGNED") && purchase.getIsFinal() == true){
			try {
				purchase.setStatus("IN_TRANSIT");
				purchaseService.save(purchase);
				
				result = list();
			} catch (Exception e) {
				result = list();
				result.addObject("message", "commit.error");
				e.printStackTrace();
			}
		}else{
			result = new ModelAndView("error/access");
		}
		return result;
	}
	
	@RequestMapping(value = "/setDelivered", method = RequestMethod.GET)
	public ModelAndView setPurchaseDelivered(Integer purchaseId) {
		ModelAndView result;
		
		Purchase purchase = purchaseService.findOne(purchaseId);
		
		System.out.println("setting: " + purchase + "to in transit");
		System.out.println(purchase.getStatus() + " " + purchase.getIsFinal());
		
		if(purchase.getStatus().equals("IN_TRANSIT") && purchase.getIsFinal() == true){
			try {
				purchase.setStatus("DELIVERED");
				purchaseService.save(purchase);
				
				result = list();
			} catch (Exception e) {
				result = list();
				result.addObject("message", "commit.error");
				e.printStackTrace();
			}
		}else{
			result = new ModelAndView("error/access");
		}
		return result;
	}

	//Helper methods --------------------------------------------------------------------------

		
	

}
