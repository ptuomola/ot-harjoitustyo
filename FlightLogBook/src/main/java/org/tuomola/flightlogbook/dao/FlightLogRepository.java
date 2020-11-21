package org.tuomola.flightlogbook.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tuomola.flightlogbook.domain.FlightLog;
import org.tuomola.flightlogbook.domain.Pilot;

/**
 *
 * @author ptuomola
 */
public interface FlightLogRepository extends JpaRepository<FlightLog, Long> {
    public FlightLog findByLogOwner(Pilot owner);
}
