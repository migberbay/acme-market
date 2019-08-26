
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

	public void delete( Message message) {

		// El mensaje se movera a la trashbox, si el mensaje ya estaba en la
		// trashbox se elimina del sistema.

		UserAccount userAccount = LoginService.getPrincipal();

		//añadimos todas las boxes de los actores que tienen el message a allActorBoxes
		Set<Box> allActorBoxes = new HashSet<>();

		Collection<UserAccount> recipients = message.getRecipients();
		for (UserAccount ua : recipients)
			allActorBoxes.addAll(this.boxService.findByUserAccountId(ua.getId()));
		UserAccount sender = message.getSender();
		allActorBoxes.addAll(this.boxService.findByUserAccountId(sender.getId()));

		//Vemos que actor de los que tiene el message es el que esta logeado.
		UserAccount logged = null;

		for (UserAccount recipient : recipients)
			if (recipient.equals(userAccount)) {
				logged = recipient;
				break;
			}

		if (sender.equals(userAccount))
			logged = sender;

		//localizamos la trashbox y separamos los otros boxes en otherboxes para el actor logeado.
		Box trash = null;
		Collection<Box> otherboxes = new ArrayList<Box>();

		for (Box box : this.boxService.findByUserAccountId(logged.getId()))
			if (box.getName().equals("Trash Box"))
				trash = box;
			else
				otherboxes.add(box);

		//comprobar si esta en trashbox
		if (trash.getMessages().contains(message.getId())) {
			Collection<Message> aux = trash.getMessages();
			aux.remove(message.getId());
			trash.setMessages(aux);
			allActorBoxes.remove(trash);

			//comprobamos si el mensaje esta en alguna otra box.
			boolean isInOtherBox = false;
			for (Box b : allActorBoxes)
				if (b.getMessages().contains(message.getId())) {
					isInOtherBox = true;
					break;
				}
			if (!isInOtherBox)
				this.messageRepository.delete(message);

			this.boxService.save(trash);
		} else
			for (Box b : otherboxes)
				if (b.getMessages().contains(message.getId())) {
					Collection<Message> aux = b.getMessages();
					aux.remove(message.getId());
					b.setMessages(aux);

					Collection<Message> t = trash.getMessages();
					t.add(message);
					trash.setMessages(t);

					this.boxService.save(trash);
					this.boxService.save(b);
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
