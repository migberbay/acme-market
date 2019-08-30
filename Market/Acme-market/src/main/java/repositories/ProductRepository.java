
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
	
	@Query("select p from Product p join p.department d join d.market m where p.department.id=d.id and m.id=?1")
	Collection<Product> getProductsByMarket(int marketId);

	@Query("select p from Product p where p.provider.id=?1")
	Collection<Product> getProductsByProvider(int providerId);
	
	@Query("select p from Product p where p.department!=null")
	Collection<Product> getMarketProducts();
	
	@Query("select p from Product p where p.department=null")
	Collection<Product> getUnassignedProducts();
	
	@Query("select p from Product p where ((?1 is null or ?1 like '' ) or (p.name like %?1%))")
	Collection<Product> searchProducts(String keyword);

	@Query("select p from Product p where p.department.id=?1")
	Collection<Product> getProductsByDepartment(int departmentId);
	
	@Query("select p from Product p where p.name=?1 and p.provider.id=?2 and p.department=null")
	Collection<Product> getUnasignedProductsByNameAndProvider(String name, int providerId);

}
