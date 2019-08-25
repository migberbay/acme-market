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
public class Provider extends Actor {
	
	

	//Relationships
//	private Curricula curricula;
//	private Collection<Request> purchases;
//	
//	public Curricula getCurricula() {
//		return curricula;
//	}
//	public void setCurricula(Curricula curricula) {
//		this.curricula = curricula;
//	}
//	public Collection<Request> getPurchases() {
//		return purchases;
//	}
//	public void setPurchases(Collection<Request> purchases) {
//		this.purchases = purchases;
//	}
//	
	

	
}
