package org.tuomola.flightlogbook.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tuomola.flightlogbook.domain.Airport;

/**
 *
 * @author ptuomola
 */
public interface AirportRepository extends JpaRepository<Airport, Long> {
    Airport findOneByCode(String code);
}
