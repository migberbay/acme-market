package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.MarketRepository;
import security.LoginService;
import security.UserAccount;
import domain.Market;
import domain.Customer;
import domain.Request;


@Service
@Transactional
public class MarketService {

	//Managed Repository -----
	
	@Autowired
	private MarketRepository marketRepository;
	
	//Supporting Services -----
	
	//Simple CRUD methods -----
	
	public Market create(UserAccount ua){
		Market res = new Market();
		res.setUserAccount(ua);
		Collection<Request> requests = new ArrayList<>();
		res.setRequests(requests);
		
		return res;
	}
	
	public Collection<Market> findAll(){
		return marketRepository.findAll();
	}
	
	public Market findOne(int Id){
		return marketRepository.findOne(Id);
	}
	
	public Market save(Market a){
		
		Market saved = marketRepository.saveAndFlush(a);
		return saved;
	}
	
	public void delete(Market a){
		marketRepository.delete(a);
	}
	
	//Other business methods -----
	
	public Market getPrincipal(){
		return this.marketRepository.findByPrincipal(LoginService.getPrincipal());
	}
	
	public Market getMarketByProduct(int productId){
		return this.marketRepository.getMarketByProduct(productId);
	}

}