package org.tuomola.flightlogbook.domain;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ptuomola
 */
public class FlightLog {
    private Pilot logOwner;
    List<Flight> flights;

    public FlightLog(Pilot logOwner) {
        this.flights = new ArrayList<>();
        this.logOwner = logOwner;
    }

    public Pilot getLogOwner() {
        return logOwner;
    }

    public List<Flight> getFlights() {
        return flights;
    }
    
    public Flight startFlight() {
        Flight flight = new Flight();
        flight.setPic(this.logOwner);
        flights.add(flight);
        return flight;
    }

    public Duration getTotalTime() {
        Duration totalDuration = Duration.ZERO;
        
        for(Flight flight : flights) {
            totalDuration = totalDuration.plus(flight.getDuration());
        }

        return totalDuration;
    }

    public Duration getTotalFlightTime() {
        Duration totalDuration = Duration.ZERO;
        
        for(Flight flight : flights) {
            totalDuration = totalDuration.plus(flight.getFlightDuration());
        }

        return totalDuration;    
    }

    public int getTotalLandings() {
        int totalLandings = 0;
        
        for(Flight flight : flights) {
            totalLandings += flight.getNumLandings();
        }
        
        return totalLandings;
    }

    public int getTotalTakeOffs() {
        int totalTakeOffs = 0;
        
        for(Flight flight : flights) {
            totalTakeOffs += flight.getNumTakeoffs();
        }
        
        return totalTakeOffs;
    }

    @Override
    public String toString() {
        return "Owner: " + logOwner + "\nFlights:\n" + flights;
    }
}
