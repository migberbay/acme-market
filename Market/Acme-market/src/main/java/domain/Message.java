
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import security.UserAccount;

@Entity
@Access(AccessType.PROPERTY)
public class Message extends DomainEntity {

	// Attributes -------------------------------------------------------------

	private Date				moment;
	private String				body;
	private String				subject;

	// Getters and Setters ---------------------------------------------------

	@Past
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getMoment() {
		return this.moment;
	}

	public void setMoment(final Date moment) {
		this.moment = moment;
	}

	@NotBlank
	public String getSubject() {
		return this.subject;
	}

	public void setSubject(final String subject) {
		this.subject = subject;
	}

	@NotBlank
	public String getBody() {
		return this.body;
	}

	public void setBody(final String body) {
		this.body = body;
	}

	// Relationships ----------------------------------------------------------

	private UserAccount				sender;
	private Collection<UserAccount>	recipients;


	@Valid
	@ManyToOne(optional = false)
	public UserAccount getSender() {
		return this.sender;
	}

	public void setSender(final UserAccount sender) {
		this.sender = sender;
	}

	@Valid
	@ManyToMany
	@NotNull
	public Collection<UserAccount> getRecipients() {
		return this.recipients;
	}

	public void setRecipients(final Collection<UserAccount> recipients) {
		this.recipients = recipients;
	}

}
