package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
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

	public List<DeliveryBoy> getTopDeliveryBoyByScore() {
		Map<DeliveryBoy, Double> deliveryBoyByScore = new HashMap<>();
		for (DeliveryBoy deliveryBoy : deliveryBoyRepository.findAll()) {
			deliveryBoyByScore.put(deliveryBoy, this.getScore(deliveryBoy));
		}
	    List<Map.Entry<DeliveryBoy, Double>> list = new LinkedList<Map.Entry<DeliveryBoy, Double> >(deliveryBoyByScore.entrySet()); 
	    List<DeliveryBoy> result = new ArrayList<>();
	    Collections.sort(list, new Comparator<Map.Entry<DeliveryBoy, Double> >() { 
	            public int compare(Map.Entry<DeliveryBoy, Double> o1,  
	                               Map.Entry<DeliveryBoy, Double> o2) 
	            { 
	                return (o1.getValue()).compareTo(o2.getValue()); 
	            } 
	        }); 
	    for (Map.Entry<DeliveryBoy, Double> aa : list) { 
	    	System.out.println(" ORDERED: DELIVER " + aa.getKey().getUserAccount().getUsername() + " y score " + aa.getValue());
            result.add(aa.getKey());
        } 
	    
	    List<DeliveryBoy> res;
	    if(result.size()<10) res = result;
	    else res = result.subList(0, 10);
	    
		return res;
	}
	
	

}