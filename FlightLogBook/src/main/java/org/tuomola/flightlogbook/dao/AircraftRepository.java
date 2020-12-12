package org.tuomola.flightlogbook.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tuomola.flightlogbook.domain.Aircraft;

/**
 * JPA repository for domain object Aircraft.
 * @author ptuomola
 */

public interface AircraftRepository extends JpaRepository<Aircraft, Long> {
    
    /**
     * Retrieve an Aircraft by its identifier.
     * @param identifier Aircraft's identifier
     * @return Matching Aircraft domain object (if found)
     */
    Aircraft findOneByIdentifier(String identifier);
}
