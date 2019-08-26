
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.PersonalRecord;

@Repository
public interface PersonalRecordRepository extends JpaRepository<PersonalRecord, Integer> {
	
	@Query("select p from PersonalRecord p where p.curricula.id = ?1")
	PersonalRecord findPersonalRecordByCurricula(int curricula);

}
