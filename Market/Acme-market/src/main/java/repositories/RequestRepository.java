
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Request;

@Repository
public interface RequestRepository extends JpaRepository<Request, Integer> {

	@Query("select r from Request r where r.market.id=?1")
	Collection<Request> getRequestsByMarket(int marketId);
	
	@Query("select r from Request r where r.provider.id=?1")
	Collection<Request> getRequestsByProvider(int providerId);
	
	@Query("select avg(m.requests.size) from Market m")
	Double getAvgRequestsPerMarket();
	
	@Query("select min(m.requests.size) from Market m")
	Integer getMinRequestsPerMarket();
	
	@Query("select max(m.requests.size) from Market m")
	Integer getMaxRequestsPerMarket();
	
	@Query("select stddev(m.requests.size) from Market m")
	Double getStdevRequestsPerMarket();
	
	@Query("select avg(p.requests.size) from Provider p")
	Double getAvgRequestsPerProvider();
	
	@Query("select min(p.requests.size) from Provider p")
	Integer getMinRequestsPerProvider();
	
	@Query("select max(p.requests.size) from Provider p")
	Integer getMaxRequestsPerProvider();
	
	@Query("select stddev(p.requests.size) from Provider p")
	Double getStdevRequestsPerProvider();


}
