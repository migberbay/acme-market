
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Department;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Integer> {

}
