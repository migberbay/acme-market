package services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
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
		res.setTicker(generateTicker());
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
	
	public Collection<Purchase> findUnasigned() {
		return this.purchaseRepository.findUnasigned();
	}

	public String generateTicker(){
		Random r = new Random();
		String res = "";
		String possible = new String("ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789");//36
		for (int i = 0; i < 4; i++) {
			res += new Integer(r.nextInt(10)).toString();
		}
		res += "-";
		for (int i = 0; i < 6; i++) {
			res += possible.charAt(r.nextInt(36));
		}

		return res;
	}

	public Date calculateDate(Purchase purchase) {
		Calendar cal = Calendar.getInstance();
		Integer minutesToAdd = 5 * purchase.getProducts().size();
		cal.add(Calendar.MINUTE, minutesToAdd);
		
		return cal.getTime();
	}
	
	public Double getAvgPurchasesPerCustomer(){
		return this.purchaseRepository.getAvgPurchasesPerCustomer();
	}
	
	public Integer getMinPurchasesPerCustomer(){
		return this.purchaseRepository.getMinPurchasesPerCustomer();
	}
	
	public Integer getMaxPurchasesPerCustomer(){
		return this.purchaseRepository.getMaxPurchasesPerCustomer();
	}
	
	public Double getStdevPurchasesPerCustomer(){
		return this.purchaseRepository.getStdevPurchasesPerCustomer();
	}
	
	public Double getAvgDeliveredPurchasesPerDeliveryBoy(){
		return this.purchaseRepository.getAvgDeliveredPurchasesPerDeliveryBoy();
	}
	
	public Integer getMinDeliveredPurchasesPerDeliveryBoy(){
		return this.purchaseRepository.getMinDeliveredPurchasesPerDeliveryBoy();
	}
	
	public Integer getMaxDeliveredPurchasesPerDeliveryBoy(){
		return this.purchaseRepository.getMaxDeliveredPurchasesPerDeliveryBoy();
	}
	
	public Double getStdevDeliveredPurchasesPerDeliveryBoy(){
		return this.purchaseRepository.getStdevDeliveredPurchasesPerDeliveryBoy();
	}
}