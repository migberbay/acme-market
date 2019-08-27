package services;


import java.util.Collection;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ProductRepository;
import domain.Market;
import domain.Product;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.ProductRepository;
import domain.Comment;
import domain.Product;
import domain.Provider;


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
		
		Product saved = productRepository.saveAndFlush(a);
		return saved;
	}
	
	public void delete(Product a){
		productRepository.delete(a);
	}
	
	//Other business methods -----
	
	public Product reconstruct(Product product, BindingResult bindingResult) {
		Product res = new Product();
		
		if(product.getId()==0){
			res = product;
		}else{
			Product e = productRepository.findOne(product.getId());
			res=e;
		}

		validator.validate(res, bindingResult);
		
		if(bindingResult.hasErrors()){
			System.out.println(bindingResult.getFieldErrors());
			throw new ValidationException();
		}

		return res;
	}
	
	public Collection<Product> getProductsByMarket(int marketId){
		return this.productRepository.getProductsByMarket(marketId);
	}

	public Collection<Product> findProductsByPrincipal() {
		return this.productRepository.getProductsByProvider(providerService.getPrincipal().getId());
	}

}