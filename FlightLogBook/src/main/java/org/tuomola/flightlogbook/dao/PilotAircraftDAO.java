package org.tuomola.flightlogbook.dao;

import java.time.Duration;
import java.util.Date;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.tuomola.flightlogbook.domain.Aircraft;
import org.tuomola.flightlogbook.domain.Airport;

/**
 *
 * @author ptuomola
 */

@Data
@NoArgsConstructor
public class PilotAircraftDAO {
    private String identifier;
    private String type;
    private Date lastFlight;
    private Duration hoursFlown;

    public PilotAircraftDAO(Aircraft aircraft) {
        identifier = aircraft.getIdentifier();
        type = aircraft.getType();
        lastFlight = null;
        hoursFlown = Duration.ZERO;
    }

    public void addToHoursFlown(Duration duration) {
        if (duration == null) {
            return;
        }
        
        hoursFlown = hoursFlown.plus(duration);
    }

    public void setLastDateIfBefore(Date flightDate) {
        if (flightDate == null) {
            return;
        }
        
        if(lastFlight == null || lastFlight.before(flightDate)) {
            lastFlight = flightDate;
        }
    }
}
