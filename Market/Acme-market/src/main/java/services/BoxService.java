
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.BoxRepository;
import security.LoginService;
import security.UserAccount;
import domain.Box;
import domain.Message;

@Service
@Transactional
public class BoxService {

	@Autowired
	private BoxRepository	boxRepository;


	public Box create(final UserAccount userAccount) {

		Assert.isTrue(userAccount.equals(userAccount));

		final Box box = new Box();

		box.setUserAccount(userAccount);
		box.setMessages(new ArrayList<Message>());

		return box;
	}

	public Collection<Box> findAll() {
		return this.boxRepository.findAll();
	}

	public Box findOne(final int Id) {
		return this.boxRepository.findOne(Id);
	}

	public Collection<Box> findByUserAccountId(final int userAccountId) {
		return this.boxRepository.findByUserAccountId(userAccountId);
	}

	public Box save(final Box box) {

		Box result;

		//		UserAccount principal = LoginService.getPrincipal();
		//		Assert.isTrue(box.getUserAccount().getUserAccount().equals(principal));

		result = this.boxRepository.saveAndFlush(box);
		return result;
	}

	public void delete(final Box box) {

		final UserAccount userAccount = LoginService.getPrincipal();
		Assert.isTrue(box.getUserAccount().equals(userAccount));

		this.boxRepository.delete(box);
	}

	public Box reconstructBox(final Box box) {

		Box res;
		if (box.getId() == 0) {
			res = this.create(LoginService.getPrincipal());
			res.setName(box.getName());

		} else {
			res = this.findOne(box.getId());
			res.setName(box.getName());
			}

		return res;
	}

	// Other business methods -----

	public void createSystemBoxes(final UserAccount userAccount) {

		final Box inbox = new Box(), outbox = new Box(), thrashbox = new Box();
		inbox.setUserAccount(userAccount);
		inbox.setName("In Box");
		inbox.setMessages(new ArrayList<Message>());
		outbox.setUserAccount(userAccount);
		outbox.setName("Out Box");
		outbox.setMessages(new ArrayList<Message>());
		thrashbox.setUserAccount(userAccount);
		thrashbox.setName("Thrash Box");
		thrashbox.setMessages(new ArrayList<Message>());


		this.saveSystem(inbox);
		this.saveSystem(outbox);
		this.saveSystem(thrashbox);
	}

	public void addMessageToBox(Box box, Message message) {
		box.getMessages().add(message);
		this.save(box);

	}

	public Box findByUserAccountAndName(final UserAccount userAccount, final String boxName) {
		Assert.notNull(userAccount);
		return this.boxRepository.findByUserAccountIdAndName(userAccount.getId(), boxName);
	}

	public Box saveSystem(final Box box) {
		return this.boxRepository.save(box);
	}


}
