package services;

import java.util.Collection;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.DepartmentRepository;
import security.LoginService;
import domain.Department;
import domain.Product;


@Service
@Transactional
public class DepartmentService {

	//Managed Repository -----
	
	@Autowired
	private DepartmentRepository departmentRepository;
	
	//Supporting Services -----
	
	@Autowired
	private MarketService marketService;

	@Autowired
	private ProductService productService;
	
	@Autowired
	private Validator validator;
	
	//Simple CRUD methods -----
	
	public Department create(){
		Department res = new Department();
		
		return res;
	}
	
	public Collection<Department> findAll(){
		return departmentRepository.findAll();
	}
	
	public Department findOne(int Id){
		return departmentRepository.findOne(Id);
	}
	
	public Department save(Department a){
		Assert.isTrue(LoginService.hasRole("MARKET"));
		Department saved = departmentRepository.saveAndFlush(a);
		return saved;
	}
	
	public void delete(Department a){
		departmentRepository.delete(a);
	}

	public Collection<Department> findDepartmentsByPrincipal() {
		return this.departmentRepository.findDepartmentsByMarket(marketService.getPrincipal().getId());
	}
	
	//Other business methods -----
	
	public Department reconstruct(Department department, BindingResult bindingResult) {
		Department res = new Department();
		
		if(department.getId()==0){
			res = department;
			res.setMarket(marketService.getPrincipal());
		}else{
			Department e = departmentRepository.findOne(department.getId());
			res=e;
			if(!e.getDiscount().equals(department.getDiscount())){
				this.applyDiscount(e.getDiscount(),department.getDiscount(), department.getId());
			}
			if(department.getTitle()!=null) res.setTitle(department.getTitle());
			res.setDiscount(department.getDiscount());
		}
		
		validator.validate(res, bindingResult);
		
		if(bindingResult.hasErrors()){
			System.out.println(bindingResult.getFieldErrors());
			throw new ValidationException();
		}

		return res;
	}
	
	public Collection<Department> findDepartmentsByMarket(int marketId){
		return this.departmentRepository.findDepartmentsByMarket(marketId);
	}

	private void applyDiscount(Double old, Double newS, int departmentId){
		Collection<Product> products = productService.getProductsByDepartment(departmentId);

		for(Product p: products){
			Double or;
			if(old==0d) or= p.getPrice();
			else or = Math.abs(p.getPrice()/(1-old));
			
			Double op = or -(or*newS);
			p.setPrice(Math.round(op*100.0d)/100.0d);

			productService.save(p);
		}
	}
}