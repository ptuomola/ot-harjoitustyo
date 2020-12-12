package org.tuomola.flightlogbook.domain;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Domain object representing a Flight.
 * @author ptuomola
 */

@Data
@Entity
public class Flight {
    @Id @GeneratedValue
    @EqualsAndHashCode.Exclude private int id;

    @OneToOne
    @JoinColumn(name = "aircraft_id", referencedColumnName = "id")
    private Aircraft aircraft;

    @OneToOne
    @JoinColumn(name = "pic_id", referencedColumnName = "id")
    private Pilot pic;

    @OneToOne
    @JoinColumn(name = "copilot_id", referencedColumnName = "id")
    private Pilot copilot;

    @OneToOne
    @JoinColumn(name = "origin_id", referencedColumnName = "id")
    private Airport origin;

    @OneToOne
    @JoinColumn(name = "destination_id", referencedColumnName = "id")
    private Airport destination;
    
    private Instant departureTime;
    private Instant arrivalTime;
    
    private Instant takeOffTime;
    private Instant landingTime;
    
    private int numLandings;
    private int numTakeOffs;

    private FlightState flightState = FlightState.INITIAL;
    
    public Date getFlightDate() {
        return departureTime == null ? null : Date.from(departureTime);
    }
    
    public Date getArrivalDate() {
        return arrivalTime == null ? null : Date.from(arrivalTime);
    }  
    
    public Duration getDuration() {
        return (departureTime == null || arrivalTime == null) ? null : Duration.between(departureTime, arrivalTime);
    }
    
    public Duration getFlightDuration() {
        return (takeOffTime == null || landingTime == null) ? null : Duration.between(takeOffTime, landingTime);
    }
        
    public String getDeptAirportCode() {
        return this.origin == null ? null : origin.getCode();
    }

    public String getArrAirportCode() {
        return this.destination == null ? null : destination.getCode();
    }
    
    public String getAircraftType() {
        return this.aircraft == null ? null : aircraft.getType();
    }
    
    public String getAircraftIdentifier() {
        return this.aircraft == null ? null : aircraft.getIdentifier();
    }
    
    public String getPilotFullName() {
        return this.pic == null ? null : pic.getFullName();
    }
}