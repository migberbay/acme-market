package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;

@Entity
@Access(AccessType.PROPERTY)
public class DeliveryBoy extends Actor {
	

	//Relationships
	
	private Collection<Comment> comments;
	private Collection<Purchase> purchases;
	
	@Valid
	@OneToMany
	public Collection<Comment> getComments() {
		return comments;
	}
	
	public void setComments(Collection<Comment> comments) {
		this.comments = comments;
	}
	
	@Valid
	@OneToMany(mappedBy="deliveryBoy")
	public Collection<Purchase> getPurchases() {
		return purchases;
	}
	
	public void setPurchases(Collection<Purchase> purchases) {
		this.purchases = purchases;
	}
	
	

	
}
