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

/**
 *
 * @author ptuomola
 */

@Data
@Entity
public class Flight {
    @Id @GeneratedValue
    private int id;

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
    
    public Date getFlightDate()
    {
        if(departureTime == null) { 
            return null;
        }
        
        return Date.from(departureTime); 
    }
    
    public Duration getDuration() {
        if (departureTime == null || arrivalTime == null) {
            return null;
        }
        
        return Duration.between(departureTime, arrivalTime);
    }
    
    public Duration getFlightDuration() {
        if (takeOffTime == null || landingTime == null) {
            return null;
        }
        
        return Duration.between(takeOffTime, landingTime);
    }
        
    public String getDeptAirportCode()
    {
        if (this.origin == null) {
            return null;
        }
        
        return origin.getCode();
    }

    public String getArrAirportCode()
    {
        if (this.destination == null) {
            return null;
        }
        
        return destination.getCode();
    }
    
    public String getAircraftType() {
        if (this.aircraft == null) {
            return null;
        }
        
        return aircraft.getType();
    }
    
    public String getAircraftIdentifier() {
        if (this.aircraft == null) {
            return null;
        }
        
        return aircraft.getIdentifier();
    }
    
    public String getPilotFullName() {
        if (this.pic == null) {
            return null;
        }
        
        return pic.getFullName();
    }
}
