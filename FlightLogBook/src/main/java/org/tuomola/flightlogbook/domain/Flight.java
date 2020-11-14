package org.tuomola.flightlogbook.domain;

import java.time.Duration;
import java.util.Date;

/**
 *
 * @author ptuomola
 */
public class Flight {
    private Aircraft aircraft;

    private Pilot pic;
    private Pilot copilot;
    
    private Airport origin;
    private Airport destination;
    
    private Date departureTime;
    private Date arrivalTime;
    
    private Date takeOffTime;
    private Date landingTime;
    
    private int numLandings;
    private int numTakeOffs;

    private FlightState flightState = FlightState.INITIAL;
    
    public void startTaxi()
    {
        if(flightState != FlightState.INITIAL)
            return;
        
        flightState = FlightState.PRE_TAXI;
        departureTime = new Date();
    }
    
    public void takeOff()
    {
        if(flightState != FlightState.PRE_TAXI)
            return;
        
        flightState = FlightState.FLIGHT;
        takeOffTime = new Date();
        numTakeOffs++;
    }
    
    public void land()
    {
        if(flightState != FlightState.FLIGHT)
            return;
        
        flightState = FlightState.POST_TAXI;
        numLandings++;
        landingTime = new Date();
    }
    
    public void touchAndGo()
    {
        if(flightState != FlightState.FLIGHT)
            return;
        
        numLandings++;
        numTakeOffs++;
    }
    
    public void stopFlight()
    {
        flightState = FlightState.ENDED;
        arrivalTime = new Date();
    }
    
    public Duration getDuration()
    {
        return Duration.between(departureTime.toInstant(), arrivalTime.toInstant());
    }
    
    public Duration getFlightDuration()
    {
        return Duration.between(takeOffTime.toInstant(), arrivalTime.toInstant());
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

    public Date getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Date departureTime) {
        this.departureTime = departureTime;
    }

    public Date getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(Date arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public Date getTakeOffTime() {
        return takeOffTime;
    }

    public void setTakeOffTime(Date takeOffTime) {
        this.takeOffTime = takeOffTime;
    }

    public Date getLandingTime() {
        return landingTime;
    }

    public void setLandingTime(Date landingTime) {
        this.landingTime = landingTime;
    }

    public int getNumLandings() {
        return numLandings;
    }

    public void setNumLandings(int numLandings) {
        this.numLandings = numLandings;
    }

    public int getNumTakeoffs() {
        return numTakeOffs;
    }

    public void setNumTakeoffs(int numTakeoffs) {
        this.numTakeOffs = numTakeoffs;
    }

    @Override
    public String toString() {
        return "Flight: Aircraft=" + aircraft + ", pic=" + pic + ", copilot=" + copilot + ", origin=" + origin + ", destination=" + destination + ", departureTime=" + departureTime + ", arrivalTime=" + arrivalTime + ", takeOffTime=" + takeOffTime + ", landingTime=" + landingTime + ", numLandings=" + numLandings + ", numTakeOffs=" + numTakeOffs + ", flightState=" + flightState + "\nDuration: " + getDuration() + " Flight time: " + getFlightDuration();
    }
}
