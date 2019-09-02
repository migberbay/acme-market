
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import security.UserAccount;

import domain.Customer;
import domain.Provider;

@Repository
public interface ProviderRepository extends JpaRepository<Provider, Integer> {

	@Query("select p from Provider p where p.userAccount = ?1") 
	Provider findByPrincipal(UserAccount principal);
	
	@Query("select p from Provider p order by p.requests.size desc")
	Collection<Provider> getTopProvidersByRequest();
	
}
