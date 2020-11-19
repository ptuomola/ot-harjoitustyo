package org.tuomola.flightlogbook.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tuomola.flightlogbook.domain.Flight;

/**
 *
 * @author ptuomola
 */
public interface FlightRepository extends JpaRepository<Flight, Long> {
}
