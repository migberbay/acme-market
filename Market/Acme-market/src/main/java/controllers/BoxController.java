
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
import services.BoxService;
import domain.Box;

@Controller
@RequestMapping("box/")
public class BoxController extends AbstractController {

	//Services

	@Autowired
	private BoxService	boxService;


	// Constructors -----------------------------------------------------------

	public BoxController() {
		super();
	}

	//Listing-----------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView res;
		final List<Box> boxes = new ArrayList<>();

		final UserAccount logged = LoginService.getPrincipal();

		boxes.addAll(this.boxService.findByUserAccountId(logged.getId()));

		res = new ModelAndView("box/list");
		res.addObject("boxes", boxes.toArray());

		return res;
	}

	//Helper methods---------------------------------------------------
	protected ModelAndView createEditModelAndView(final Box box) {
		ModelAndView res;
		res = this.createEditModelAndView(box, null);
		return res;
	}
	protected ModelAndView createEditModelAndView(final Box box, final String messageCode) {
		ModelAndView res;

		final UserAccount logged = LoginService.getPrincipal();

		res = new ModelAndView("box/edit");
		res.addObject("box", box);
		res.addObject("boxes", this.boxService.findByUserAccountId(logged.getId()));
		res.addObject("errorMessage", messageCode);

		return res;
	}

}
