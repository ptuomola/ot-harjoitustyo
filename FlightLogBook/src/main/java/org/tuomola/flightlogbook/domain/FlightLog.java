package org.tuomola.flightlogbook.domain;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 *
 * @author ptuomola
 */

@Entity
public class FlightLog {
    
    @Id @GeneratedValue
    private int id;

    @OneToOne(optional = false)
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    private Pilot logOwner;
    
    @OneToMany(fetch = FetchType.EAGER)
    private List<Flight> flights;

    public FlightLog() {
    }
    
    public FlightLog(Pilot logOwner) {
        this.flights = new ArrayList<>();
        this.logOwner = logOwner;
    }

    public void setLogOwner(Pilot logOwner) {
        this.logOwner = logOwner;
    }

    public Pilot getLogOwner() {
        return logOwner;
    }

    public List<Flight> getFlights() {
        return flights;
    }
    
    public void addFlight(Flight flight) {
        flights.add(flight);
    }
    
    public Duration getTotalTime() {
        Duration totalDuration = Duration.ZERO;
        
        for (Flight flight : flights) {
            if (flight.getDuration() == null) {
                continue;
            }
            
            totalDuration = totalDuration.plus(flight.getDuration());
        }

        return totalDuration;
    }

    public Duration getTotalFlightTime() {
        Duration totalDuration = Duration.ZERO;
        
        for (Flight flight : flights) {
            if (flight.getFlightDuration() == null) {
                continue;
            }
            
            totalDuration = totalDuration.plus(flight.getFlightDuration());
        }

        return totalDuration;    
    }

    public int getTotalLandings() {
        int totalLandings = 0;
        
        for (Flight flight : flights) {
            totalLandings += flight.getNumLandings();
        }
        
        return totalLandings;
    }

    public int getTotalTakeOffs() {
        int totalTakeOffs = 0;
        
        for (Flight flight : flights) {
            totalTakeOffs += flight.getNumTakeOffs();
        }
        
        return totalTakeOffs;
    }

    @Override
    public String toString() {
        return "Owner: " + logOwner + "\nFlights:\n" + flights;
    }
}
