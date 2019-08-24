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
public class DeliveryBoy extends Actor {
	

	//Relationships
	
//	private Collection<Comment> comments;
//	private Collection<Order> orders;
//	
//	public Collection<Comment> getComments() {
//		return comments;
//	}
//	
//	public void setComments(Collection<Comment> comments) {
//		this.comments = comments;
//	}
//	
//	
//	public Collection<Order> getOrders() {
//		return orders;
//	}
//	
//	public void setOrders(Collection<Order> orders) {
//		this.orders = orders;
//	}
	
	

	
}
