
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mysql.jdbc.Messages;

import repositories.MessageRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import security.UserAccountService;
import domain.Box;
import domain.Message;
import forms.MessageForm;

@Service
@Transactional
public class MessageService {

	// Managed Repository -----
	@Autowired
	private MessageRepository	messageRepository;

	// Supporting Services -----

	@Autowired
	private BoxService			boxService;

	@Autowired
	private UserAccountService	userAccountService;


	// Simple CRUD methods -----
	public Message create( UserAccount sender) {

		 Message message = new Message();

		message.setSender(sender);
		message.setMoment(new Date(System.currentTimeMillis() - 1000));

		return message;
	}

	public Collection<Message> findAll() {
		return this.messageRepository.findAll();
	}

	public Message findOne( int Id) {
		return this.messageRepository.findOne(Id);
	}

	public Message save( Message message) {

		Message result;

		 Date moment = new Date(System.currentTimeMillis() - 1000);

		message.setMoment(moment);

		result = this.messageRepository.saveAndFlush(message);
		return result;
	}

	public Message reconstruct( MessageForm form) {
		Message res = this.create(LoginService.getPrincipal());
		res.setBody(form.getBody());
		res.setSubject(form.getSubject());

		List<UserAccount> recipients = new ArrayList<>();

		String[] recipientsArray = form.getRecipients().split(",");


		for (int i = 0; i < recipientsArray.length; i++){
			UserAccount ua = this.userAccountService.findByUsername(recipientsArray[i].trim());
			if(ua != null){
				recipients.add(ua);
			}
		}
			
		res.setRecipients(recipients);


		return res;
	}

	public void delete(Message message) {

		// El mensaje se movera a la trashbox, si el mensaje ya estaba en la
		// trashbox se de la trashbox, el mensaje en si nunca es eliminado

		UserAccount userAccount = LoginService.getPrincipal();
		Box trash = new Box();
		Box holder = new Box();
		Collection<Box> boxes = boxService.findByUserAccountId(userAccount.getId());
		String boxName = ""; 
		
		for (Box box : boxes) {
			if(box.getMessages().contains(message)){
				boxName = box.getName();
				holder = box;
			}
			if(box.getName().trim().equals("Trash Box")){
				trash = box;
			}
		}
		
		if(boxName.trim().equals("Trash Box")){
			trash.getMessages().remove(message);
			boxService.save(trash);
		}else{
			holder.getMessages().remove(message);
			boxService.save(holder);
			
			trash.getMessages().add(message);
			boxService.save(trash);
		}
		
	}

	// Other business methods -----

	public void addMesageToBoxes(Message message) {
		Collection<Box> boxes = new ArrayList<Box>();
		Box outBox = this.boxService.findByUserAccountAndName(message.getSender(), "Out Box");

		boxes.add(outBox);
		
		for (UserAccount ua : message.getRecipients()) {
			Box inBox = this.boxService.findByUserAccountAndName(ua, "In Box");
			boxes.add(inBox);
		}

		for (Box box : boxes) {
			Collection<Message> messages = new ArrayList<Message>();
			
			messages.add(message);
			messages.addAll(box.getMessages());
			box.setMessages(messages);
			this.boxService.save(box);
		}
	}

	public Collection<Message> findBySender( UserAccount sender) {
		return this.messageRepository.findBySender(sender);
	}

}
