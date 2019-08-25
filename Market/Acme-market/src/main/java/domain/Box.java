
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;

import org.hibernate.validator.constraints.NotBlank;

import security.UserAccount;

@Entity
@Access(AccessType.PROPERTY)
public class Box extends DomainEntity {

	// Default system boxes can't be deleted or modified

	// Attributes -----------------------------------------------------------

	private String	name;

	// Getters and Setters ---------------------------------------------------

	@NotBlank
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}


	// Relationships ----------------------------------------------------------

	private UserAccount			userAccount;
	private Collection<Integer>	messages;


	@Valid
	@ElementCollection
	public Collection<Integer> getMessages() {
		return this.messages;
	}

	public void setMessages(final Collection<Integer> messages) {
		this.messages = messages;
	}

	@Valid
	@ManyToOne(optional = false)
	public UserAccount getUserAccount() {
		return this.userAccount;
	}

	public void setUserAccount(final UserAccount userAccount) {
		this.userAccount = userAccount;
	}

}
