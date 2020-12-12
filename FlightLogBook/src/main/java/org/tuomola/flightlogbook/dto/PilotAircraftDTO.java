package org.tuomola.flightlogbook.dto;

import java.time.Duration;
import java.util.Date;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.tuomola.flightlogbook.domain.Aircraft;

/**
 * Data Transfer Object for returning aircraft flown by a Pilot.
 * @author ptuomola
 */

@Data
@NoArgsConstructor
public class PilotAircraftDTO {
    private String identifier;
    private String type;
    private Date lastFlight;
    private Duration hoursFlown;

    /**
     * Constructor.
     * @param aircraft Aircraft that has been flown
     */
    public PilotAircraftDTO(Aircraft aircraft) {
        identifier = aircraft.getIdentifier();
        type = aircraft.getType();
        lastFlight = null;
        hoursFlown = Duration.ZERO;
    }

    /**
     * Add to time flown on this aircraft.
     * @param duration Time to be added
     */
    public void addToHoursFlown(Duration duration) {
        if (duration == null) {
            return;
        }
        
        hoursFlown = hoursFlown.plus(duration);
    }

    /**
     * Set the last date flown if the previous last date is earlier.
     * @param flightDate Last date to set to
     */
    public void setLastDateIfBefore(Date flightDate) {
        if (flightDate == null) {
            return;
        }
        
        if (lastFlight == null || lastFlight.before(flightDate)) {
            lastFlight = flightDate;
        }
    }
}
