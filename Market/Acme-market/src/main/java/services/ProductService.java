package services;


import java.util.ArrayList;
import java.util.Collection;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ProductRepository;
import security.LoginService;
import domain.Comment;
import domain.DeliveryBoy;
import domain.Product;
import domain.Provider;
import forms.ProductForm;


@Service
@Transactional
public class ProductService {

	//Managed Repository -----
	
	@Autowired
	private ProductRepository productRepository;
	
	//Supporting Services -----
	

	@Autowired
	private ProviderService providerService;

	@Autowired
	private Validator validator;
	
	//Simple CRUD methods -----
	
	public Product create(){
		Product res = new Product();
		return res;
	}

	//Simple CRUD methods -----
	
	public Product create(Provider provider){
		Product res = new Product();
		Collection<Comment> comments = new ArrayList<>();
		res.setComments(comments);
		res.setProvider(provider);
		return res;
	}
	
	public Collection<Product> findAll(){
		return productRepository.findAll();
	}
	
	public Product findOne(int Id){
		return productRepository.findOne(Id);
	}
	
	public Product save(Product a){
		Assert.isTrue(LoginService.hasRole("PROVIDER")||LoginService.hasRole("CUSTOMER") ||LoginService.hasRole("MARKET"));
		Product saved = productRepository.saveAndFlush(a);
		return saved;
	}
	
	public void delete(Product a){
		productRepository.delete(a);
	}
	
	//Other business methods -----
	
	public Collection<Product> reconstruct(ProductForm form, BindingResult bindingResult) {
		Collection<Product> res = new ArrayList<>();
		
		validator.validate(form, bindingResult);
		
		if(bindingResult.hasErrors()){
			System.out.println(bindingResult.getFieldErrors());
			throw new ValidationException();
		}
		
		Product aux = this.create(providerService.getPrincipal()); 
		aux.setName(form.getName());
		aux.setPrice(form.getPrice());
		aux.setStock(form.getPacketSize());
		
		for (int i=0; i < form.getTotalStock()/form.getPacketSize(); i++) {
			res.add(aux);		
		}

		return res;
	}
	
	public Collection<Product> getProductsByMarket(int marketId){
		return this.productRepository.getProductsByMarket(marketId);
	}

	public Collection<Product> findProductsByPrincipal() {
		return this.productRepository.getProductsByProvider(providerService.getPrincipal().getId());
	}
	
	public Collection<Product> getMarketProducts(){
		return this.productRepository.getMarketProducts();
	}
	
	public Collection<Product> searchProducts(String keyword){
		return this.productRepository.searchProducts(keyword);
	}

	public Collection<Product> getProductsByDepartment(int departmentId) {
		return this.productRepository.getProductsByDepartment(departmentId);
	}

	public Collection<Product> getUnassignedProducts() {
		return this.productRepository.getUnassignedProducts();
	}

	public Collection<Product> getUnasignedProductsByNameAndProvider(String name, int providerId){
		return this.productRepository.getUnasignedProductsByNameAndProvider(name, providerId);
	}
	
	public Collection<Product> getProductsByNameAndProvider(String name, int providerId){
		return this.productRepository.getProductsByNameAndProvider(name, providerId);
	}
	
	public Double getScore(Product d){
		Double res = 0.;
		Double cont = 0.;
		for (Comment c : d.getComments()) {
			Double score = new Double(c.getScore());
			res += score;
			cont++;
		}
		res = res/cont;
		return res;
	}
}