package org.tuomola.flightlogbook.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tuomola.flightlogbook.domain.Airport;

/**
 * JPA repository for domain object Airport.
 * @author ptuomola
 */
public interface AirportRepository extends JpaRepository<Airport, Long> {
    /**
     * Find an Airport by its code. 
     * @param code Code of the airport to be retrieved
     * @return Airport object matching the code (if any)
     */
    
    Airport findOneByCode(String code);
}
