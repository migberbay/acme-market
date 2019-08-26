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
import services.EducationRecordService;
import services.ProviderService;
import controllers.AbstractController;
import domain.Curricula;
import domain.EducationRecord;
import domain.Provider;

@Controller
@RequestMapping("/educationRecord/provider")
public class EducationRecordProviderController extends AbstractController {

	@Autowired
	private EducationRecordService educationService;
	
	@Autowired
	private ProviderService providerService;
	
	@Autowired
	private CurriculaService curriculaService;

	// Create ------------------------------------------------------------------
	
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		
		EducationRecord e = educationService.create();
		result = this.createEditModelAndView(e);

		return result;
	}
	
	// Save---------------------------------------------------------------------
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@ModelAttribute("education") EducationRecord education, BindingResult binding) {
		ModelAndView result;
		
		try {
			EducationRecord saved = educationService.reconstruct(education,binding);
			educationService.save(saved);
			result = new ModelAndView("redirect:/curricula/provider/show.do");
		} catch (ValidationException oops) {
			oops.printStackTrace();
			result = this.createEditModelAndView(education);
		} catch (final Throwable oops) {
			oops.printStackTrace();
			result = this.createEditModelAndView(education,"record.commit.error");
		}

		return result;
	}
	
	// Edit -------------------------------------------------------------------------
		
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int educationRecordId) {
		ModelAndView result;
		EducationRecord education = educationService.findOne(educationRecordId);
		if(education!=null){
		Provider logged = providerService.getPrincipal();
		Curricula c = curriculaService.findCurriculaByProvider(logged.getId());
		if(education.getCurricula().equals(c)){
			result = this.createEditModelAndView(education);
		}else
			result = new ModelAndView("error/access");
		}
		else result = new ModelAndView("error/access");
		return result;
	}
	
	// Delete -----------------------------------------------------------------

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int educationRecordId) {
		ModelAndView result;
		Provider logged = providerService.getPrincipal();
		EducationRecord education = educationService.findOne(educationRecordId);
		Curricula c = curriculaService.findCurriculaByProvider(logged.getId());
		if(education.getCurricula().equals(c)){
		try {
			educationService.delete(education);
			result = new ModelAndView("redirect:/curricula/provider/show.do");
		} catch (final Throwable oops) {
			oops.printStackTrace();
			result = this.createEditModelAndView(education, "education.commit.error");
		}
		}else
			result = new ModelAndView("error/access");

		return result;
	}

	//Helper methods --------------------------------------------------------------------------

	protected ModelAndView createEditModelAndView(final EducationRecord education) {
		ModelAndView res;
		res = this.createEditModelAndView(education, null);
		return res;
	}
	protected ModelAndView createEditModelAndView(final EducationRecord education, final String messageCode) {
		ModelAndView res;

		res = new ModelAndView("educationRecord/edit");
		res.addObject("education", education);
		res.addObject("errorMessage", messageCode);

		return res;
	}
}
