package org.tuomola.flightlogbook.dao;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.tuomola.flightlogbook.domain.Flight;
import org.tuomola.flightlogbook.domain.Pilot;

/**
 *
 * @author ptuomola
 */
public interface FlightRepository extends JpaRepository<Flight, Long> {
    public List<Flight> getByPic(Pilot pic);
}
