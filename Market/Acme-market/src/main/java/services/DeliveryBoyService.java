package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.DeliveryBoyRepository;
import security.LoginService;
import security.UserAccount;
import domain.DeliveryBoy;
import domain.Customer;


@Service
@Transactional
public class DeliveryBoyService {

	//Managed Repository -----
	
	@Autowired
	private DeliveryBoyRepository deliveryBoyRepository;
	
	//Supporting Services -----
	
	//Simple CRUD methods -----
	
	public DeliveryBoy create(UserAccount ua){
		DeliveryBoy res = new DeliveryBoy();
		res.setUserAccount(ua);
		
		return res;
	}
	
	public Collection<DeliveryBoy> findAll(){
		return deliveryBoyRepository.findAll();
	}
	
	public DeliveryBoy findOne(int Id){
		return deliveryBoyRepository.findOne(Id);
	}
	
	public DeliveryBoy save(DeliveryBoy a){
		
		DeliveryBoy saved = deliveryBoyRepository.saveAndFlush(a);
		return saved;
	}
	
	public void delete(DeliveryBoy a){
		deliveryBoyRepository.delete(a);
	}
	
	//Other business methods -----
	
	public DeliveryBoy getPrincipal(){
		return this.deliveryBoyRepository.findByPrincipal(LoginService.getPrincipal());
	}

}