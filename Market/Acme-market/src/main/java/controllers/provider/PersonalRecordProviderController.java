package controllers.provider;

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
	private CurriculaService curriculaService;
		
	@Autowired
	private ProviderService providerService;
	
	@Autowired
	private PersonalRecordService personalService;
	
	@Autowired
	private ProfessionalRecordService professionalService;
	
	@Autowired
	private EducationRecordService educationService;
	
	
	// Save---------------------------------------------------------------------
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(PersonalRecord personal, BindingResult binding) {
		ModelAndView result;
		
		try {
			personalService.reconstruct(personal,binding);
			result = new ModelAndView("redirect:curricula/provider/show.do");
		} catch (final Throwable oops) {
			oops.printStackTrace();
			result = new ModelAndView("redirect:actor/show.do");
		}

		return result;
	}
		

	//Helper methods --------------------------------------------------------------------------

}
