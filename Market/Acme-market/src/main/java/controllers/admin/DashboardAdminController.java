package controllers.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.DeliveryBoyService;
import services.ProviderService;
import services.PurchaseService;
import services.RequestService;

import controllers.AbstractController;


@Controller
@RequestMapping("/admin/")
public class DashboardAdminController extends AbstractController {

	@Autowired
	private RequestService requestService;

	@Autowired
	private PurchaseService purchaseService;
	
	@Autowired
	private DeliveryBoyService deliveryBoyService;
	
	@Autowired
	private ProviderService providerService;
	
	//DASHBOARD--------------------------------------------------------
	@RequestMapping(value="/dashboard", method=RequestMethod.GET)
	public ModelAndView dashboard(){
		ModelAndView res;
	
		res = new ModelAndView("admin/dashboard");
	
		res.addObject("avgRequestsPerMarket", Math.round(requestService.getAvgRequestsPerMarket()*100.0d)/100.0d);
		res.addObject("minRequestsPerMarket", requestService.getMinRequestsPerMarket());
		res.addObject("maxRequestsPerMarket",requestService.getMaxRequestsPerMarket());
		res.addObject("stdevRequestsPerMarket", Math.round(requestService.getStdevRequestsPerMarket()*100.0d)/100.0d);

		res.addObject("avgRequestsPerProvider", Math.round(requestService.getAvgRequestsPerProvider()*100.0d)/100.0d);
		res.addObject("minRequestsPerProvider", requestService.getMinRequestsPerProvider());
		res.addObject("maxRequestsPerProvider",requestService.getMaxRequestsPerProvider());
		res.addObject("stdevRequestsPerProvider", Math.round(requestService.getStdevRequestsPerProvider()*100.0d)/100.0d);
		
		res.addObject("avgPurchasesPerCustomer", Math.round(purchaseService.getAvgPurchasesPerCustomer()*100.0d)/100.0d);
		res.addObject("minPurchasesPerCustomer", purchaseService.getMinPurchasesPerCustomer());
		res.addObject("maxPurchasesPerCustomer",purchaseService.getMaxPurchasesPerCustomer());
		res.addObject("stdevPurchasesPerCustomer", Math.round(purchaseService.getStdevPurchasesPerCustomer()*100.0d)/100.0d);
		
		res.addObject("avgDeliveredPurchasesPerDeliveryBoy", Math.round(purchaseService.getAvgDeliveredPurchasesPerDeliveryBoy()*100.0d)/100.0d);
		res.addObject("minDeliveredPurchasesPerDeliveryBoy", purchaseService.getMinDeliveredPurchasesPerDeliveryBoy());
		res.addObject("maxDeliveredPurchasesPerDeliveryBoy",purchaseService.getMaxDeliveredPurchasesPerDeliveryBoy());
		res.addObject("stdevDeliveredPurchasesPerDeliveryBoy", Math.round(purchaseService.getStdevDeliveredPurchasesPerDeliveryBoy()*100.0d)/100.0d);

		res.addObject("top10DeliveryBoys", deliveryBoyService.getTopDeliveryBoyByScore());
		res.addObject("top10Providers", providerService.getTopProvidersByRequest());


		return res;
	}
	
	
}