
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Department;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Integer> {

	@Query("select d from Department d where d.market.id=?1")
	Collection<Department> findDepartmentsByMarket(int marketId);

}
