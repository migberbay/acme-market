package controllers.admin;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ConfigurationService;
import controllers.AbstractController;
import domain.Configuration;

@Controller
@RequestMapping("/system/admin/")
public class ConfigurationAdminController extends AbstractController {
	
	@Autowired
	private ConfigurationService configurationService;

	//Edit-------------------------------------------------------------
	@RequestMapping(value="/configuration", method=RequestMethod.GET)
	public ModelAndView edit(){
		ModelAndView res;

		Configuration c = (Configuration) configurationService.findAll().toArray()[0];

		res = this.createEditModelAndView(c);
		return res;
	}

	//Save-------------------------------------------------------------	
	@RequestMapping(value="/configuration", method=RequestMethod.POST, params="save")
	public ModelAndView save(@Valid Configuration config, BindingResult binding){
		ModelAndView res;
		
		
		if(binding.hasErrors()){
			System.out.println(binding.getFieldErrors());
			res = createEditModelAndView(config);
		}else{
			try {
				configurationService.save(config);
				res = new ModelAndView("redirect:/welcome/index.do");
			} catch (Throwable e) {
				e.printStackTrace();
				res = createEditModelAndView(config, "admin.commit.error");
				
			}
		}
		return res;
	}

	
	
	//Helper methods---------------------------------------------------
	protected ModelAndView createEditModelAndView(Configuration config){
		ModelAndView res;
		res = createEditModelAndView(config, null);
		return res;
	}
	protected ModelAndView createEditModelAndView(Configuration config, String messageCode){
		ModelAndView res;

		res = new ModelAndView("admin/configuration");
		Boolean language = false;
		if(LocaleContextHolder.getLocale().getLanguage().toLowerCase().equals("es")){
			language=true;
		}
		if(LocaleContextHolder.getLocale().getLanguage().toLowerCase().equals("en")){
			language = false;
		}
		
		res.addObject("language",language);
		res.addObject("configuration", config);
		res.addObject("errorMessage", messageCode);
		
		return res;
	}
	
}