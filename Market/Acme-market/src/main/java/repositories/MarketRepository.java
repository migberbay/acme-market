
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import security.UserAccount;

import domain.Customer;
import domain.Market;

@Repository
public interface MarketRepository extends JpaRepository<Market, Integer> {

	@Query("select m from Market m where m.userAccount = ?1") 
	Market findByPrincipal(UserAccount principal);
	
}
