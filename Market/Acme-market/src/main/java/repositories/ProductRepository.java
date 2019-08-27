
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Curricula;
import domain.Market;
import domain.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

	@Query("select m.request.product from Market m where m.request.status = ACCEPTED and m = ?1 and m.request.product.stock >0")
	Collection<Product> findValidProductsByMarket(Market market);
}
