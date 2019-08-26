package controllers.provider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import security.UserAccount;
import services.CurriculaService;
import services.EducationRecordService;
import services.PersonalRecordService;
import services.ProfessionalRecordService;
import services.ProviderService;
import controllers.AbstractController;
import domain.Box;
import domain.Curricula;
import domain.PersonalRecord;
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
	public ModelAndView show() {

		ModelAndView result;
		
		Provider provider = providerService.getPrincipal();

		if(curriculaService.findCurriculaByProvider(provider.getId())!=null){
			result = new ModelAndView("curricula/show");
			int curriculaId = curriculaService.findCurriculaByProvider(provider.getId()).getId();
			result.addObject("personal", personalService.findPersonalRecordByCurricula(curriculaId));
			result.addObject("professionals",professionalService.findProfessionalRecordsByCurricula(curriculaId));
			result.addObject("educations",educationService.findEducationRecordsByCurricula(curriculaId));
			result.addObject("uri", "curricula/show.do");
		}else
			result = new ModelAndView("error/access");
		

		return result;
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		
		Provider provider = providerService.getPrincipal();
		if(curriculaService.findCurriculaByProvider(provider.getId())==null){
			PersonalRecord p = personalService.create();
			result = this.createEditModelAndView(p);
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
