package org.tuomola.flightlogbook.domain;

import java.time.Duration;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.function.BinaryOperator;
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
        if (flights == null) {
            flights = new ArrayList<>();
        }

        flights.add(flight);
    }
    
    public Duration getTotalTime() {
        return (flights == null) ? Duration.ZERO : flights.stream().map(f -> f.getDuration()).filter(Objects::nonNull).reduce(Duration.ZERO, (a,b) -> (a.plus(b)));
    }

    public Duration getTotalFlightTime() {
        return (flights == null) ? Duration.ZERO : flights.stream().map(f -> f.getFlightDuration()).filter(Objects::nonNull).reduce(Duration.ZERO, (a,b) -> (a.plus(b)));
    }
    
    public Duration getFlightTimeDuringLast(Period last) {
        Date cutoffDate = DateHelper.toUTCDate(LocalDate.now().minus(last));

        return (flights == null) ? 
                Duration.ZERO : 
                flights.stream()
                        .filter(f -> f.getArrivalTime() != null && f.getArrivalTime().after(cutoffDate))
                        .map(f -> f.getFlightDuration())
                        .filter(Objects::nonNull)
                        .reduce(Duration.ZERO, (a,b) -> (a.plus(b)));
    }

    public int getTotalLandings() {
        return (flights == null) ? 0 : flights.stream().map(f -> f.getNumLandings()).reduce(0, (a,b) -> (a+b));
    }

    public int getLandingsDuringLast(Period ofMonths) {
        Date cutOffDate = DateHelper.toUTCDate(LocalDate.now().minus(ofMonths));
        
        return (flights == null) ? 
                0 : 
                flights
                    .stream()
                    .filter(f -> f.getArrivalTime() != null && f.getArrivalTime().after(cutOffDate))
                    .map(f -> f.getNumLandings())
                    .reduce(0, (a,b) -> (a+b));
    }

    public int getTotalTakeOffs() {
        return (flights == null) ? 0 : flights.stream().map(f -> f.getNumTakeOffs()).reduce(0, (a,b) -> (a+b));
    }
    
    public int getTakeOffsDuringLast(Period ofMonths) {
        Date cutOffDate = DateHelper.toUTCDate(LocalDate.now().minus(ofMonths));
        
        return (flights == null) ? 
                0 : 
                flights
                    .stream()
                    .filter(f -> f.getArrivalTime() != null)
                    .filter(f -> f.getArrivalTime().after(cutOffDate))
                    .map(f -> f.getNumTakeOffs())
                    .reduce(0, (a,b) -> (a+b));
    }
    
    @Override
    public String toString() {
        return "Owner: " + logOwner + "\nFlights:\n" + flights;
    }
}
