package org.tuomola.flightlogbook.dao;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.tuomola.flightlogbook.domain.Aircraft;
import org.tuomola.flightlogbook.domain.Pilot;

/**
 *
 * @author ptuomola
 */

public interface AircraftRepository extends JpaRepository<Aircraft, Long> {
    Aircraft findOneByIdentifier(String identifier);
}
