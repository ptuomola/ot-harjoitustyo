package org.tuomola.flightlogbook.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tuomola.flightlogbook.domain.Aircraft;

/**
 *
 * @author ptuomola
 */
public interface AircraftRepository extends JpaRepository<Aircraft, Long> {
}
