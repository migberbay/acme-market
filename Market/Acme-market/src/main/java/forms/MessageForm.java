package forms;

import org.hibernate.validator.constraints.NotBlank;


public class MessageForm {

	private String recipients;
	private String subject;
	private String body;
	
	
	@NotBlank
	public String getRecipients() {
		return recipients;
	}
	public void setRecipients(String recipients) {
		this.recipients = recipients;
	}
	
	@NotBlank
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	@NotBlank
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}


	
}
