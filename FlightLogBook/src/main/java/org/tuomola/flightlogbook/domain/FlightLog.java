package org.tuomola.flightlogbook.domain;

import java.time.Duration;
import java.time.Instant;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import lombok.Data;

/**
 * Domain object representing a FlightLog.
 * @author ptuomola
 */

@Data
@Entity
public class FlightLog {
    
    @Id @GeneratedValue
    private int id;

    @OneToOne(optional = false)
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    private Pilot logOwner;
    
    @OneToMany(fetch = FetchType.EAGER)
    private List<Flight> flights;
    
    /**
     * Add a flight to this flight log.
     * @param flight Flight to be added
     */
    public void addFlight(Flight flight) {
        if (flights == null) {
            flights = new ArrayList<>();
        }

        flights.add(flight);
    }
    
    /**
     * Get total duration of all flights in this flight log.
     * @return Duration of all flights in the flight log
     */
    public Duration getTotalTime() {
        return (flights == null) ? Duration.ZERO : flights.stream().map(f -> f.getDuration()).filter(Objects::nonNull).reduce(Duration.ZERO, (a, b) -> (a.plus(b)));
    }

    /**
     * Get total flight time of all flights in this log.
     * @return Total flight time of all flights in this log.
     */
    public Duration getTotalFlightTime() {
        return (flights == null) ? Duration.ZERO : flights.stream().map(f -> f.getFlightDuration()).filter(Objects::nonNull).reduce(Duration.ZERO, (a, b) -> (a.plus(b)));
    }
    
    /**
     * Get the flight time in the last x days/months/years.
     * @param last Time backwards from today during which the flights should be counted
     * @return Flight time during the period provided
     */
    public Duration getFlightTimeDuringLast(Period last) {
        Instant cutoffDate = Instant.now().minus(last);

        return (flights == null) ? 
                Duration.ZERO : 
                flights.stream()
                        .filter(f -> f.getArrivalTime() != null && f.getArrivalTime().isAfter(cutoffDate))
                        .map(f -> f.getFlightDuration())
                        .filter(Objects::nonNull)
                        .reduce(Duration.ZERO, (a, b) -> (a.plus(b)));
    }

    /**
     * Get total number of flights in this log.
     * @return Total number of flights
     */
    public int getTotalFlights() {
        return (flights == null) ? 0 : flights.size();
    }
    
    /**
     * Get total number of landings in this log.
     * @return Total number of landings
     */
    public int getTotalLandings() {
        return (flights == null) ? 0 : flights.stream().map(f -> f.getNumLandings()).reduce(0, (a, b) -> (a + b));
    }

    /**
     * Get number of flights within the last x days/months/weeks.
     * @param ofMonths Period to consider from today backwards in time
     * @return Number of flights in the period given
     */
    public int getFlightsDuringLast(Period ofMonths) {
        Instant cutoffDate = Instant.now().minus(ofMonths);
        
        return (flights == null) ? 
                0 : 
                flights
                    .stream()
                    .filter(f -> (f.getArrivalTime() != null && f.getArrivalTime().isAfter(cutoffDate)))
                    .map(f -> 1)
                    .reduce(0, (a, b) -> (a + b));
    }
    
    /**
     * Get number of landings within the last x days/months/weeks.
     * @param ofMonths Period to consider from today backwards in time
     * @return Number of landings in the period given
     */
    public int getLandingsDuringLast(Period ofMonths) {
        Instant cutoffDate = Instant.now().minus(ofMonths);
        
        return (flights == null) ? 
                0 : 
                flights
                    .stream()
                    .filter(f -> (f.getArrivalTime() != null && f.getArrivalTime().isAfter(cutoffDate)))
                    .map(f -> f.getNumLandings())
                    .reduce(0, (a, b) -> (a + b));
    }

    /**
     * Total number of takeoffs in the flight log.
     * @return Number of takeoffs in the log
     */
    public int getTotalTakeOffs() {
        return (flights == null) ? 0 : flights.stream().map(f -> f.getNumTakeOffs()).reduce(0, (a, b) -> (a + b));
    }
    
    /**
     * Get number of takeoffs during the last x days/months/weeks.
     * @param ofMonths Period to consider from today backwards
     * @return Number of takeoffs in the period given
     */
    public int getTakeOffsDuringLast(Period ofMonths) {
        Instant cutoffDate = Instant.now().minus(ofMonths);
        
        return (flights == null) ? 
                0 : 
                flights
                    .stream()
                    .filter(f -> f.getArrivalTime() != null)
                    .filter(f -> f.getArrivalTime().isAfter(cutoffDate))
                    .map(f -> f.getNumTakeOffs())
                    .reduce(0, (a, b) -> (a + b));
    }
}
