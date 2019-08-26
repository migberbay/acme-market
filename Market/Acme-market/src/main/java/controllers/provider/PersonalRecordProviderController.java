package controllers.provider;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CurriculaService;
import services.PersonalRecordService;
import services.ProviderService;
import controllers.AbstractController;
import domain.Curricula;
import domain.PersonalRecord;
import domain.Provider;

@Controller
@RequestMapping("/personalRecord/provider")
public class PersonalRecordProviderController extends AbstractController {

	@Autowired
	private PersonalRecordService personalService;
	
	@Autowired
	private ProviderService providerService;
	
	@Autowired
	private CurriculaService curriculaService;

	// Edit -------------------------------------------------------------------------
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int personalRecordId) {
		ModelAndView result;
		PersonalRecord personal = personalService.findOne(personalRecordId);
		if(personal!=null){
		Provider logged = providerService.getPrincipal();
		Curricula c = curriculaService.findCurriculaByProvider(logged.getId());
		if(personal.getCurricula().equals(c)){
			result = this.createEditModelAndView(personal);
		}else
			result = new ModelAndView("error/access");
		}
		else result = new ModelAndView("error/access");
		return result;
	}
	
	// Save---------------------------------------------------------------------
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@ModelAttribute("personal") PersonalRecord personal, BindingResult binding) {
		ModelAndView result;
		
		try {
			PersonalRecord saved = personalService.reconstruct(personal,binding);
			personalService.save(saved);
			result = new ModelAndView("redirect:/curricula/provider/show.do");
		} catch (ValidationException oops) {
			oops.printStackTrace();
			result = this.createEditModelAndView(personal);
		} catch (final Throwable oops) {
			oops.printStackTrace();
			result = this.createEditModelAndView(personal,"record.commit.error");
		}

		return result;
	}
	
	// Delete -----------------------------------------------------------------

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int personalRecordId) {
		ModelAndView result;
		Provider logged = providerService.getPrincipal();
		PersonalRecord personal = personalService.findOne(personalRecordId);
		Curricula c = curriculaService.findCurriculaByProvider(logged.getId());
		if(personal.getCurricula().equals(c)){
		try {
			personalService.delete(personal);
			result = new ModelAndView("redirect:/curricula/provider/show.do");
		} catch (final Throwable oops) {
			oops.printStackTrace();
			result = this.createEditModelAndView(personal, "personal.commit.error");
		}
		}else
			result = new ModelAndView("error/access");

		return result;
	}
		

	//Helper methods --------------------------------------------------------------------------

	protected ModelAndView createEditModelAndView(final PersonalRecord personal) {
		ModelAndView res;
		res = this.createEditModelAndView(personal, null);
		return res;
	}
	protected ModelAndView createEditModelAndView(final PersonalRecord personal, final String messageCode) {
		ModelAndView res;

		res = new ModelAndView("personalRecord/edit");
		res.addObject("personal", personal);
		res.addObject("errorMessage", messageCode);

		return res;
	}
}
