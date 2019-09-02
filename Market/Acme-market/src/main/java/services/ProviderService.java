package services;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.ProviderRepository;
import security.LoginService;
import security.UserAccount;
import domain.Provider;
import domain.Customer;


@Service
@Transactional
public class ProviderService {

	//Managed Repository -----
	
	@Autowired
	private ProviderRepository providerRepository;
	
	//Supporting Services -----
	
	//Simple CRUD methods -----
	
	public Provider create(UserAccount ua){
		Provider res = new Provider();
		res.setUserAccount(ua);
		
		return res;
	}
	
	public Collection<Provider> findAll(){
		return providerRepository.findAll();
	}
	
	public Provider findOne(int Id){
		return providerRepository.findOne(Id);
	}
	
	public Provider save(Provider a){
		
		Provider saved = providerRepository.saveAndFlush(a);
		return saved;
	}
	
	public void delete(Provider a){
		providerRepository.delete(a);
	}
	
	//Other business methods -----
	
	public Provider getPrincipal(){
		return this.providerRepository.findByPrincipal(LoginService.getPrincipal());
	}
	
	public Collection<Provider> getTopProvidersByRequest(){
		List<Provider> providers = (List<Provider>) this.providerRepository.getTopProvidersByRequest();
		Collection<Provider> result;
		if(providers.size()<10) result = providers;
		else result = providers.subList(0, 10);
		
		return result;
	}

}