
package controllers;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import security.UserAccount;
import security.UserAccountService;
import services.BoxService;
import services.MessageService;
import domain.Box;
import domain.Message;
import forms.MessageForm;

@Controller
@RequestMapping("message/")
public class MessageController extends AbstractController {

	//Services

	@Autowired
	private MessageService		messageService;

	@Autowired
	private BoxService			boxService;

	@Autowired
	private UserAccountService	userAccountService;


	// Constructors -----------------------------------------------------------

	public MessageController() {
		super();
	}



	//Create-----------------------------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView res;
		 MessageForm form = new MessageForm();

		res = this.createEditModelAndView(form);
		return res;

	}

//	@RequestMapping(value = "/createBroadcast", method = RequestMethod.GET)
//	public ModelAndView createBroadcast() {
//		ModelAndView res;
//		if (!LoginService.hasRole("ADMIN"))
//			res = new ModelAndView("error/access");
//		else {
//			 MessageForm form = new MessageForm();
//			String recipients = " ";
//			for ( UserAccount ua : this.userAccountService.findAll())
//				recipients = recipients + ua.getUsername() + " , ";
//
//			form.setRecipients(recipients);
//			res = this.createEditModelAndView(form, true);
//		}
//		return res;
//
//	}

	//Save-------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save( MessageForm form,  BindingResult binding) {
		ModelAndView res;

		if (binding.hasErrors()) {
			System.out.println(binding.toString());
			res = this.createEditModelAndView(form);
		} else
			try {
				Message message = this.messageService.reconstruct(form);
				Message saved = this.messageService.save(message);
				this.messageService.addMesageToBoxes(saved);
				res = new ModelAndView("redirect:/box/list.do");
			} catch ( Throwable e) {
				res = this.createEditModelAndView(form, "message.commit.error");
				for ( StackTraceElement st : e.getStackTrace())
					System.out.println(st);

			}
		return res;
	}

	//Delete-----------------------------------------------------------
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam int messageId) {
		ModelAndView res;
		 Message message = this.messageService.findOne(messageId);
		 
		 if(message.getSender().equals(LoginService.getPrincipal()) || message.getRecipients().contains(LoginService.getPrincipal())){
			 try {
					this.messageService.delete(message);
					res = new ModelAndView("redirect:/box/list.do");
				} catch ( Throwable e) {
					e.printStackTrace();
					res = this.createEditModelAndView(new MessageForm(), "message.commit.error");
				}
		 }else{
			 res = new ModelAndView("error/access");
		 }
			
		return res;
	}

	//Move message ---------------------------------------------------

//	@RequestMapping(value = "/moveToBox", method = RequestMethod.GET)
//	public ModelAndView moveToBox(@RequestParam  int messageId, @RequestParam  int boxId, @RequestParam  int newBoxId) {
//		ModelAndView res;
//		 MessageForm form = new MessageForm();
//		 Message message = this.messageService.findOne(messageId);
//		 Box original = this.boxService.findOne(boxId);
//		 Box destination = this.boxService.findOne(newBoxId);
//		if (!original.getUserAccount().equals(LoginService.getPrincipal()) || !destination.getUserAccount().equals(LoginService.getPrincipal()))
//			res = new ModelAndView("error/access");
//		else
//			try {
//				 Collection<Integer> originalMessages = original.getMessages();
//				originalMessages.remove(message.getId());
//				original.setMessages(originalMessages);
//				this.boxService.save(original);
//
//				 Collection<Integer> newMessages = destination.getMessages();
//				newMessages.add(message.getId());
//				destination.setMessages(newMessages);
//				this.boxService.save(destination);
//
//				res = new ModelAndView("redirect:/box/list.do");
//			} catch ( Throwable e) {
//				res = this.createEditModelAndView(form, false, "message.commit.error");
//			}
//		return res;
//	}

	//Helper methods---------------------------------------------------
	protected ModelAndView createEditModelAndView( MessageForm form) {
		ModelAndView res;
		res = this.createEditModelAndView(form, null);
		return res;
	}
	protected ModelAndView createEditModelAndView( MessageForm form,  String messageCode) {
		ModelAndView res;

		res = new ModelAndView("message/edit");
		res.addObject("messageForm", form);
		res.addObject("accounts", this.userAccountService.findAll());
		res.addObject("message", messageCode);

		return res;
	}

}
