package services;

import java.util.Collection;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.RequestRepository;
import domain.Product;
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
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private Validator validator;
	
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
	
	public Collection<Request> getRequestsByProvider(int providerId){
		return this.requestRepository.getRequestsByProvider(providerId);
	}

	public Request reconstruct(Request request, int productId, BindingResult bindingResult) {
		Request res = new Request();
		Product product = productService.findOne(productId);
		
		if(request.getId()==0){
			res = request;
			res.setStatus("PENDING");
			res.setProvider(product.getProvider());
			res.setProduct(product);
			res.setMarket(marketService.getPrincipal());
		}else{
			Request e = requestRepository.findOne(request.getId());
			res=e;
			res.setQuantity(request.getQuantity());
		}

		validator.validate(res, bindingResult);
		
		if(bindingResult.hasErrors()){
			System.out.println(bindingResult.getFieldErrors());
			throw new ValidationException();
		}

		return res;
	}
	
	public Request reconstruct(Request request, BindingResult bindingResult) {
		Request res = new Request();
		
		Request e = requestRepository.findOne(request.getId());
		res=e;
		res.setStatus(request.getStatus());
		res.setRejectReason(request.getRejectReason());

		validator.validate(res, bindingResult);
		
		if(bindingResult.hasErrors()){
			System.out.println(bindingResult.getFieldErrors());
			throw new ValidationException();
		}

		return res;
	}
}