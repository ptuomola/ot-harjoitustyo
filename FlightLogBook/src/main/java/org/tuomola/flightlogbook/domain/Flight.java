package org.tuomola.flightlogbook.domain;

import java.time.Duration;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

/**
 *
 * @author ptuomola
 */

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
    

    public FlightState getFlightState() {
        return flightState;
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
    
    public Pilot getPic() {
        return pic;
    }

    public void setPic(Pilot pic) {
        this.pic = pic;
    }

    public Pilot getCopilot() {
        return copilot;
    }

    public void setCopilot(Pilot copilot) {
        this.copilot = copilot;
    }    
    
    public Aircraft getAircraft() {
        return aircraft;
    }

    public void setAircraft(Aircraft aircraft) {
        this.aircraft = aircraft;
    }

    public Airport getOrigin() {
        return origin;
    }

    public void setOrigin(Airport origin) {
        this.origin = origin;
    }

    public Airport getDestination() {
        return destination;
    }

    public void setDestination(Airport destination) {
        this.destination = destination;
    }

    public Instant getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Instant departureTime) {
        this.departureTime = departureTime;
    }

    public Instant getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(Instant arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public Instant getTakeOffTime() {
        return takeOffTime;
    }

    public void setTakeOffTime(Instant takeOffTime) {
        this.takeOffTime = takeOffTime;
    }

    public Instant getLandingTime() {
        return landingTime;
    }

    public void setLandingTime(Instant landingTime) {
        this.landingTime = landingTime;
    }

    public int getNumLandings() {
        return numLandings;
    }

    public void setNumLandings(int numLandings) {
        this.numLandings = numLandings;
    }

    public int getNumTakeOffs() {
        return numTakeOffs;
    }

    public void setNumTakeOffs(int numTakeoffs) {
        this.numTakeOffs = numTakeoffs;
    }
    
    public void setFlightState(FlightState flightState) {
        this.flightState = flightState;        
    }


    @Override
    public String toString() {
        return "Flight: Aircraft=" + aircraft + ", pic=" + pic + ", copilot=" + copilot + ", origin=" + origin + ", destination=" + destination + ", departureTime=" + departureTime + ", arrivalTime=" + arrivalTime + ", takeOffTime=" + takeOffTime + ", landingTime=" + landingTime + ", numLandings=" + numLandings + ", numTakeOffs=" + numTakeOffs + ", flightState=" + flightState + "\nDuration: " + getDuration() + " Flight time: " + getFlightDuration();
    }

}
