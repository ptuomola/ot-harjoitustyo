package org.tuomola.flightlogbook.dao;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.tuomola.flightlogbook.domain.Flight;
import org.tuomola.flightlogbook.domain.Pilot;

/**
 * JPA repository for the domain object Flight.
 * @author ptuomola
 */
public interface FlightRepository extends JpaRepository<Flight, Long> {
    /**
     * Get Flights flown by a specific pilot.
     * @param pic Pilot whose flights should be retrieved
     * @return List of flights where the PIC is the given pilot
     */
    
    public List<Flight> getByPic(Pilot pic);
}
