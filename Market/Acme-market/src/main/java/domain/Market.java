package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Market extends Actor {
	
	private String VATNumber;
	private String CompanyName;
	
	@NotBlank
	@Pattern(regexp="^([E]{1}[S]{1})([A-Z]{1})([0-9]{8})$")
	public String getVATNumber() {
		return VATNumber;
	}

	public void setVATNumber(String VATNumber) {
		this.VATNumber = VATNumber;
	}
	
	@NotBlank
	public String getCompanyName() {
		return CompanyName;
	}

	public void setCompanyName(String companyName) {
		CompanyName = companyName;
	}



	//Relationships
//	private Collection<Request> requests;
//
//	public Collection<Request> getRequests() {
//		return requests;
//	}
//
//	public void setRequests(Collection<Request> requests) {
//		this.requests = requests;
//	}
	

	
}
