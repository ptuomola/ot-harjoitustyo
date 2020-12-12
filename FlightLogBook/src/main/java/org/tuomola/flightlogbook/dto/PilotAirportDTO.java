package org.tuomola.flightlogbook.dto;

import java.util.Date;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.tuomola.flightlogbook.domain.Airport;

/**
 * Data Transfer Object for returning the Airports visited by a Pilot.
 * @author ptuomola
 */

@Data
@NoArgsConstructor
public class PilotAirportDTO {
    private String code;
    private String name;
    private Date lastVisit;
    private int numDepartures;
    private int numArrivals;

    /**
     * Constructor.
     * @param airport Airport visited by a Pilot. 
     */
    public PilotAirportDTO(Airport airport) {
        code = airport.getCode();
        name = airport.getName();
        numDepartures = 0;
        numArrivals = 0;
        lastVisit = null;
    }

    /**
     * Set last visited date, if the existing last visit was earlier. 
     * @param flightDate Last visit date to set to
     */
    public void setLastVisitIfBefore(Date flightDate) {
        if (flightDate == null) {
            return;
        }
        
        if (lastVisit == null || lastVisit.before(flightDate)) {
            lastVisit = flightDate;
        }
    }

    /**
     * Add 1 to number of departures from this airport. 
     */
    public void incrementDepartures() {
        numDepartures++; 
    }

    /**
     * Add 1 to number of arrivals to this airport. 
     */
    public void incrementArrivals() {
        numArrivals++;
    }
}
