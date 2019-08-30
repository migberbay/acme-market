package forms;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;


public class ProductForm {
	
	private Boolean creating;
	private String name;
	private Integer totalStock;
	private Integer packetSize;
	private Double price;
	
	@NotBlank
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@NotNull
	public Integer getTotalStock() {
		return totalStock;
	}
	public void setTotalStock(Integer totalStock) {
		this.totalStock = totalStock;
	}
	
	@NotNull
	public Integer getPacketSize() {
		return packetSize;
	}
	public void setPacketSize(Integer packetSize) {
		this.packetSize = packetSize;
	}
	
	@NotNull
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	
	@NotNull
	public Boolean getCreating() {
		return creating;
	}
	public void setCreating(Boolean creating) {
		this.creating = creating;
	}

	
}
