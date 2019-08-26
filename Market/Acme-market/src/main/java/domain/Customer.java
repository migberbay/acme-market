package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;

@Entity
@Access(AccessType.PROPERTY)
public class Customer extends Actor {
	

	//Relationships
	private CreditCard creditCard;
	private Collection<Market> markets;
	private Collection<Purchase> purchases;
	
	@OneToOne (optional = false)
	public CreditCard getCreditCard() {
		return this.creditCard;
	}

	public void setCreditCard(final CreditCard creditCard) {
		this.creditCard = creditCard;
	}

	@Valid
	@ManyToMany
	public Collection<Market> getMarkets() {
		return markets;
	}

	public void setMarkets(Collection<Market> markets) {
		this.markets = markets;
	}

	@Valid
	@OneToMany(mappedBy="customer")
	public Collection<Purchase> getPurchases() {
		return purchases;
	}

	public void setPurchases(Collection<Purchase> purchases) {
		this.purchases = purchases;
	}
	
	

	

	
}
