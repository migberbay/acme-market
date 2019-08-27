package services;

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
	
	

}