package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;


@Entity
@Access(AccessType.PROPERTY)
public class Provider extends Actor {
	
	

	//Relationships
	private Curricula curricula;
	private Collection<Request> requests;
	
	public Curricula getCurricula() {
		return curricula;
	}
	public void setCurricula(Curricula curricula) {
		this.curricula = curricula;
	}
	public Collection<Request> getRequests() {
		return requests;
	}
	public void setPurchases(Collection<Request> requests) {
		this.requests = requests;
	}
	
	

	
}
