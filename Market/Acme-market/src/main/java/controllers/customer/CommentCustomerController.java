package controllers.customer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.CustomerService;
import services.DeliveryBoyService;
import services.MarketService;
import services.ProductService;
import services.CommentService;
import controllers.AbstractController;
import domain.Customer;
import domain.DeliveryBoy;
import domain.Market;
import domain.Product;
import domain.Comment;
import forms.CommentForm;

@Controller
@RequestMapping("/comment/customer")
public class CommentCustomerController extends AbstractController {
		
	@Autowired
	private DeliveryBoyService deliveryBoyService;
	
	@Autowired
	private CommentService commentService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private MarketService marketService;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private Validator validator;
	
	//Listing-----------------------------------------------------------

		@RequestMapping(value = "/list", method = RequestMethod.GET)
		public ModelAndView list() {
			ModelAndView res;
			res = new ModelAndView("comment/list");
			Collection<Comment> comments = commentService.findByCustomer(customerService.getPrincipal());
			
			res.addObject("comments", comments);

			return res;
		}
	
	// Create -----------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam(required = false) Integer deliveryBoyId , @RequestParam(required = false) Integer productId) {
		ModelAndView result = new ModelAndView();
		DeliveryBoy deliveryBoy = null;
		Product product = null;
		Customer customer = customerService.getPrincipal();
		
		if(deliveryBoyId != null){
			deliveryBoy = deliveryBoyService.findOne(deliveryBoyId);
		}
		if(productId != null){
			product = productService.findOne(productId);
		}
		
		CommentForm form = new CommentForm();
		form.setCustomer(customer);
		form.setDeliveryBoy(deliveryBoy);
		form.setProduct(product);
		
		if(LoginService.hasRole("CUSTOMER")){
			try {
				result = this.createEditModelAndView(form);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			result = new ModelAndView("error/access");
		}
		return result;
	}
		

	// Save -----------------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@ModelAttribute("form")CommentForm form, BindingResult binding) {
		ModelAndView result;
		
		validator.validate(form, binding);
		
		if(binding.hasErrors()){
			result = createEditModelAndView(form);
		}else{
			try {
				Comment comment = commentService.create();
				comment.setScore(form.getScore());
				comment.setText(form.getText());
				comment.setCustomer(form.getCustomer());
				comment = commentService.save(comment);
				
				if(form.getDeliveryBoy()!= null){
					form.getDeliveryBoy().getComments().add(comment);
					deliveryBoyService.save(form.getDeliveryBoy());
					result = new ModelAndView("redirect:/actor/show.do?actorId="+form.getDeliveryBoy().getId());
				}else if(form.getProduct()!=null){
					form.getProduct().getComments().add(comment);
					productService.save(form.getProduct());
					result = new ModelAndView("redirect:/product/show.do?productId="+form.getProduct().getId());
				}else{
					result = createEditModelAndView(form);
				}

			} catch (Exception e) {
				result = createEditModelAndView(form);
				result.addObject("message", "commit.error");
				e.printStackTrace();
			}
		}
		return result;
	}

	//Helper methods --------------------------------------------------------------------------

		
	protected ModelAndView createEditModelAndView(CommentForm form){
		ModelAndView res;
		res = createEditModelAndView(form, null);
		return res;
	}
		
	protected ModelAndView createEditModelAndView(CommentForm form, String messageCode){
		ModelAndView res;
		
		res = new ModelAndView("comment/edit");
		res.addObject("form", form);
		res.addObject("message", messageCode);

		return res;
	}

}
