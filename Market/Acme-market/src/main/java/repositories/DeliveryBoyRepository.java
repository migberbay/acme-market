
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import security.UserAccount;

import domain.Customer;
import domain.DeliveryBoy;

@Repository
public interface DeliveryBoyRepository extends JpaRepository<DeliveryBoy, Integer> {

	
	@Query("select d from DeliveryBoy d where d.userAccount = ?1") 
	DeliveryBoy findByPrincipal(UserAccount principal);
}
