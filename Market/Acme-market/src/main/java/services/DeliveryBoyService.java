package services;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.DeliveryBoyRepository;
import security.LoginService;
import security.UserAccount;
import domain.Comment;
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
	
	public Double getScore(DeliveryBoy d){
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

	public Set<Entry<DeliveryBoy, Double>> getTopDeliveryBoyByScore() {
		Map<DeliveryBoy, Double> deliveryBoyByScore = new HashMap<>();
		for (DeliveryBoy deliveryBoy : deliveryBoyRepository.findAll()) {
			deliveryBoyByScore.put(deliveryBoy, this.getScore(deliveryBoy));
		}
		for(Double n: deliveryBoyByScore.values()){
			
		}
		return deliveryBoyByScore.entrySet();
	}
	
	

}