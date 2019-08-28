package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.PurchaseRepository;
import domain.Product;
import domain.Purchase;


@Service
@Transactional
public class PurchaseService {

	//Managed Repository -----
	
	@Autowired
	private PurchaseRepository purchaseRepository;
	
	
	//Supporting Services -----
	@Autowired
	private CustomerService customerService;
	
	//Simple CRUD methods -----
	
	public Purchase create(){
		Purchase res = new Purchase();
		res.setProducts(new ArrayList<Product>());
		res.setStatus("PENDING");
		res.setIsFinal(false);
		res.setTicker("7802-SH67SY");
		res.setTotalPrice(0.0);
		res.setIsAssigned(false);
		res.setEstimatedDate(new Date());
		return res;
	}
	
	public Collection<Purchase> findAll(){
		return purchaseRepository.findAll();
	}
	
	public Purchase findOne(int Id){
		return purchaseRepository.findOne(Id);
	}
	
	public Purchase save(Purchase a){
		
		Purchase saved = purchaseRepository.saveAndFlush(a);
		return saved;
	}
	
	public void delete(Purchase a){
		purchaseRepository.delete(a);
	}	
	
	//Other business methods -----
	
	public Collection<Purchase> findPurchasesByPrincipal() {
		
		return this.purchaseRepository.findByPrincipal(customerService.getPrincipal());
	}

}