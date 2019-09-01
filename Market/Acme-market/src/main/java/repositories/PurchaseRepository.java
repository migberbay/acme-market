
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Customer;
import domain.Purchase;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Integer> {

	@Query("select p from Purchase p where p.customer = ?1")
	Collection<Purchase> findByPrincipal(Customer c);
	
	@Query("select p from Purchase p where p.status = 'PENDING' and p.isFinal = true")
	Collection<Purchase> findUnasigned();
	
	@Query("select avg(c.purchases.size) from Customer c")
	Double getAvgPurchasesPerCustomer();
	
	@Query("select min(c.purchases.size) from Customer c")
	Integer getMinPurchasesPerCustomer();
	
	@Query("select max(c.purchases.size) from Customer c")
	Integer getMaxPurchasesPerCustomer();
	
	@Query("select stddev(c.purchases.size) from Customer c")
	Double getStdevPurchasesPerCustomer();
	
	@Query("select avg(d.purchases.size) from DeliveryBoy d join d.purchases p where p.status='DELIVERED'")
	Double getAvgDeliveredPurchasesPerDeliveryBoy();
	
	@Query("select min(d.purchases.size) from DeliveryBoy d join d.purchases p where p.status='DELIVERED'")
	Integer getMinDeliveredPurchasesPerDeliveryBoy();
	
	@Query("select max(d.purchases.size) from DeliveryBoy d join d.purchases p where p.status='DELIVERED'")
	Integer getMaxDeliveredPurchasesPerDeliveryBoy();
	
	@Query("select stddev(d.purchases.size) from DeliveryBoy d join d.purchases p where p.status='DELIVERED'")
	Double getStdevDeliveredPurchasesPerDeliveryBoy();
}
