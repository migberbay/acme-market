package controllers.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;


@Controller
@RequestMapping("/admin/")
public class DashboardAdminController extends AbstractController {


	//DASHBOARD--------------------------------------------------------
	@RequestMapping(value="/dashboard", method=RequestMethod.GET)
	public ModelAndView dashboard(){
		ModelAndView res;
	
		res = new ModelAndView("admin/dashboard");
	/*	
		res.addObject("avgRequestsPerManager", Math.round(requestService.getAvgRequestsPerManager()*100.0d)/100.0d);
		res.addObject("minRequestsPerManager", requestService.getMinRequestsPerManager());
		res.addObject("maxRequestsPerManager",requestService.getMaxRequestsPerManager());
		res.addObject("stdevRequestsPerManager", Math.round(requestService.getStdevRequestsPerManager()*100.0d)/100.0d);

		res.addObject("top10Managers", requestService.getTopManagersWPendingRequests());
*/

		return res;
	}
	
	
}