package org.tuomola.flightlogbook.service;

import java.time.Duration;
import java.util.Date;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.tuomola.flightlogbook.domain.Airport;

/**
 *
 * @author ptuomola
 */

@Data
@NoArgsConstructor
public class PilotAirportVO {
    private String code;
    private String name;
    private Date lastVisit;
    private int numDepartures;
    private int numArrivals;

    public PilotAirportVO(Airport airport) {
        code = airport.getCode();
        name = airport.getName();
        numDepartures = 0;
        numArrivals = 0;
        lastVisit = null;
    }

    public void setLastVisitIfBefore(Date flightDate) {
        if (flightDate == null) {
            return;
        }
        
        if (lastVisit == null || lastVisit.before(flightDate)) {
            lastVisit = flightDate;
        }
    }

    public void incrementDepartures() {
        numDepartures++; 
    }

    public void incrementArrivals() {
        numArrivals++;
    }
}
