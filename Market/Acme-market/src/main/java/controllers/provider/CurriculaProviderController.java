package controllers.provider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
import domain.Provider;

@Controller
@RequestMapping("/curricula/provider")
public class CurriculaProviderController extends AbstractController {

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
	
	
	// List -----------------------------------------------------------------

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int curriculaId) {

		ModelAndView result;

		
		result = new ModelAndView("curricula/show");
		result.addObject("personal", personalService.findPersonalRecordByCurricula(curriculaId));
		result.addObject("professionals",professionalService.findProfessionalRecordsByCurricula(curriculaId));
		result.addObject("educations",educationService.findEducationRecordsByCurricula(curriculaId));
		result.addObject("uri", "curricula/show.do");

		return result;
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		
		Provider provider = providerService.getPrincipal();
		if(curriculaService.findCurriculaByProvider(provider.getId())==null){
			try {
				Curricula created = curriculaService.create();
				result = new ModelAndView("redirect:curricula/provider/show.do?curriculaId="+created.getId());
			} catch (final Throwable oops) {
				oops.printStackTrace();
				result = new ModelAndView("redirect:actor/show.do");
			}
		}else
			result = new ModelAndView("error/access");

		return result;
	}
		

	//Helper methods --------------------------------------------------------------------------

}
