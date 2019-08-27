
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Market;
import domain.Curricula;
import domain.Market;
import domain.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
	
	@Query("select p from Department d join d.market m where p.department.id=d.id and m.id=?1")
	Collection<Product> getProductsByMarket(int marketId);

	@Query("select p from Product p where p.provider.id=?1")
	Collection<Product> getProductsByProvider(int providerId);

	@Query("select m.request.product from Market m where m.request.status = ACCEPTED and m = ?1 and m.request.product.stock >0")
	Collection<Product> findValidProductsByMarket(Market market);
}
