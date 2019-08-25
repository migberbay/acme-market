package domain;


import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Order extends DomainEntity {
	
	private String ticker;
	private String status;
	private Date estimatedDate;
	private Double totalPrice;
	private Boolean isFinal;
	private Boolean isAssigned;
	
	@NotBlank
	@Column(unique = true)
	@Pattern(regexp ="^([0-2]{1}[0-9]{1})([0]{1}[1-9]{1}|[1]{1}[0-2]{1})([0]{1}[1-9]{1}|[1,2]{1}[0-9]{1}|[3]{1}[0-1]{1})[-][A-Z0-9]{6}$")
	public String getTicker() {
		return this.ticker;
	}

	public void setTicker(final String ticker) {
		this.ticker = ticker;
	}
	
	@NotBlank
	@Pattern(regexp="^PENDING|ASSIGNED|IN_TRANSIT|DELIVERED$")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getEstimatedDate() {
		return this.estimatedDate;
	}

	public void setEstimatedDate(final Date estimatedDate) {
		this.estimatedDate = estimatedDate;
	}

	@NotNull
	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}
	
	@NotNull
	public Boolean getIsFinal() {
		return isFinal;
	}

	public void setIsFinal(Boolean isFinal) {
		this.isFinal = isFinal;
	}
	
	@NotNull
	public Boolean getIsAssigned() {
		return isAssigned;
	}

	public void setIsAssigned(Boolean isAssigned) {
		this.isAssigned = isAssigned;
	}

	//Relationships
	
	private Collection<Market> markets;
	private Collection<Product> products;
	
	@Valid
	@OneToOne(optional=false)
	public Collection<Market> getMarkets() {
		return markets;
	}

	public void setMarkets(Collection<Market> markets) {
		this.markets = markets;
	}
	
	@Valid
	@ManyToMany
	public Collection<Product> getProducts() {
		return products;
	}

	public void setProducts(Collection<Product> products) {
		this.products = products;
	}
	
}
