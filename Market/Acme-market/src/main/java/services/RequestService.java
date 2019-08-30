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
import forms.RequestForm;


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
	private DepartmentService departmentService;
	
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

	public Request reconstruct(RequestForm request, int productId, BindingResult bindingResult) {
		Request res = new Request();
		Product product = productService.findOne(productId);
		
		if(request.getId()==0){
			res.setStatus("PENDING");
			res.setProvider(product.getProvider());
			res.setProduct(product);
			res.setMarket(marketService.getPrincipal());
			res.setDepartmentId(request.getDepartment().getId());
		}else{
			Request e = requestRepository.findOne(request.getId());
			res=e;
			res.setDepartmentId(request.getDepartment().getId());
		}

		validator.validate(res, bindingResult);
		
		if(bindingResult.hasErrors()){
			System.out.println(bindingResult.getFieldErrors());
			throw new ValidationException();
		}

		return res;
	}

	public RequestForm construct(Request request) {
		RequestForm res = new RequestForm();
		
		res.setId(request.getId());
		res.setVersion(request.getVersion());
		res.setDepartment(departmentService.findOne(request.getDepartmentId()));
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