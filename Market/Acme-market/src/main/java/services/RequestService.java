package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.RequestRepository;
import domain.Request;


@Service
@Transactional
public class RequestService {

	//Managed Repository -----
	
	@Autowired
	private RequestRepository requestRepository;
	
	//Supporting Services -----
	
	@Autowired
	private MarketService marketService;
	
	//Simple CRUD methods -----
	
	public Request create(){
		Request res = new Request();
		
		return res;
	}
	
	public Collection<Request> findAll(){
		return requestRepository.findAll();
	}
	
	public Request findOne(int Id){
		return requestRepository.findOne(Id);
	}
	
	public Request save(Request a){
		
		Request saved = requestRepository.saveAndFlush(a);
		return saved;
	}
	
	public void delete(Request a){
		requestRepository.delete(a);
	}
	
	//Other business methods -----
	
	public Collection<Request> getRequestsByPrincipal() {
		return this.requestRepository.getRequestsByMarket(marketService.getPrincipal().getId());
	}

}