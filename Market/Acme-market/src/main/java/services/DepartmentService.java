package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.DepartmentRepository;
import security.LoginService;
import domain.Department;


@Service
@Transactional
public class DepartmentService {

	//Managed Repository -----
	
	@Autowired
	private DepartmentRepository departmentRepository;
	
	//Supporting Services -----
	
	@Autowired
	private MarketService marketService;
	
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

}