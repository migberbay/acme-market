package forms;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import domain.Customer;
import domain.DeliveryBoy;
import domain.Product;


public class CommentForm {

	private String text;
	private Integer score;
	private DeliveryBoy deliveryBoy;
	private Customer customer;
	private Product product;
	
	@NotBlank
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	@NotNull
	@Min(1)
	@Max(5)
	public Integer getScore() {
		return score;
	}
	public void setScore(Integer score) {
		this.score = score;
	}
	
	
	public DeliveryBoy getDeliveryBoy() {
		return deliveryBoy;
	}
	public void setDeliveryBoy(DeliveryBoy deliveryBoy) {
		this.deliveryBoy = deliveryBoy;
	}
	
	@NotNull
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	
	
	


	
}
