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
import services.ProfessionalRecordService;
import services.ProviderService;
import controllers.AbstractController;
import domain.Curricula;
import domain.ProfessionalRecord;
import domain.Provider;

@Controller
@RequestMapping("/professionalRecord/provider")
public class ProfessionalRecordProviderController extends AbstractController {

	@Autowired
	private ProfessionalRecordService professionalService;
	
	@Autowired
	private ProviderService providerService;
	
	@Autowired
	private CurriculaService curriculaService;

	// Create ------------------------------------------------------------------
	
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		
		Provider provider = providerService.getPrincipal();
		if(curriculaService.findCurriculaByProvider(provider.getId())==null){
			ProfessionalRecord e = professionalService.create();
			result = this.createEditModelAndView(e);
		}else
			result = new ModelAndView("error/access");

		return result;
	}
	
	// Edit -------------------------------------------------------------------------
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int professionalRecordId) {
		ModelAndView result;
		ProfessionalRecord professional = professionalService.findOne(professionalRecordId);
		if(professional!=null){
		Provider logged = providerService.getPrincipal();
		Curricula c = curriculaService.findCurriculaByProvider(logged.getId());
		if(professional.getCurricula().equals(c)){
			result = this.createEditModelAndView(professional);
		}else
			result = new ModelAndView("error/access");
		}
		else result = new ModelAndView("error/access");
		return result;
	}
	
	// Delete -----------------------------------------------------------------

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int professionalRecordId) {
		ModelAndView result;
		Provider logged = providerService.getPrincipal();
		ProfessionalRecord professional = professionalService.findOne(professionalRecordId);
		Curricula c = curriculaService.findCurriculaByProvider(logged.getId());
		if(professional.getCurricula().equals(c)){
		try {
			professionalService.delete(professional);
			result = new ModelAndView("redirect:/curricula/provider/show.do");
		} catch (final Throwable oops) {
			oops.printStackTrace();
			result = this.createEditModelAndView(professional, "professional.commit.error");
		}
		}else
			result = new ModelAndView("error/access");

		return result;
	}
	
	// Save---------------------------------------------------------------------
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(ProfessionalRecord professional, BindingResult binding) {
		ModelAndView result;
		
		try {
			ProfessionalRecord saved = professionalService.reconstruct(professional,binding);
			professionalService.save(saved);
			result = new ModelAndView("redirect:/curricula/provider/show.do");
		} catch (ValidationException oops) {
			oops.printStackTrace();
			result = this.createEditModelAndView(professional);
		} catch (final Throwable oops) {
			oops.printStackTrace();
			result = this.createEditModelAndView(professional,"record.commit.error");
		}

		return result;
	}
		

	//Helper methods --------------------------------------------------------------------------

	protected ModelAndView createEditModelAndView(final ProfessionalRecord professional) {
		ModelAndView res;
		res = this.createEditModelAndView(professional, null);
		return res;
	}
	protected ModelAndView createEditModelAndView(final ProfessionalRecord professional, final String messageCode) {
		ModelAndView res;

		res = new ModelAndView("professionalRecord/edit");
		res.addObject("professional", professional);
		res.addObject("errorMessage", messageCode);

		return res;
	}
}
