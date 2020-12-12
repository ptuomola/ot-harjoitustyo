package org.tuomola.flightlogbook.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tuomola.flightlogbook.domain.FlightLog;
import org.tuomola.flightlogbook.domain.Pilot;

/**
 * JPA repository for the domain object FlightLog.
 * @author ptuomola
 */
public interface FlightLogRepository extends JpaRepository<FlightLog, Long> {
    /**
     * Retrieve a FlightLog for a specific owner.
     * @param owner Owner whose flightLog should be retrieved
     * @return FlightLog belonging to the owner (if any)
     */
    public FlightLog findByLogOwner(Pilot owner);
}
