package controllers.provider;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CurriculaService;
import services.EducationRecordService;
import services.PersonalRecordService;
import services.ProfessionalRecordService;
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

	
	// Save---------------------------------------------------------------------
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(PersonalRecord personal, BindingResult binding) {
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
