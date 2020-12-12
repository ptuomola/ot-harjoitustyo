package org.tuomola.flightlogbook.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tuomola.flightlogbook.domain.Pilot;

/**
 * JPA repository for domain object Pilot.
 * @author ptuomola
 */
public interface PilotRepository extends JpaRepository<Pilot, Long> {
    /**
     * Find pilots with matching username (case insensitive).
     * @param userName userName to be searched for
     * @return Pilot whose username matches the provided one (if any)
     */
    public Pilot findByUserNameIgnoreCase(String userName);
}
