package org.tuomola.flightlogbook.service;

import java.time.Instant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tuomola.flightlogbook.dao.FlightRepository;
import org.tuomola.flightlogbook.domain.Aircraft;
import org.tuomola.flightlogbook.domain.Airport;
import org.tuomola.flightlogbook.domain.Flight;
import org.tuomola.flightlogbook.domain.FlightState;

/**
 * Business logic related to handling of Flights.
 * @author ptuomola
 */

@Service
public class FlightService {
    
    @Autowired
    private FlightRepository fr;
    
    @Autowired
    private AircraftService as;

    @Autowired
    private AirportService aps;

    /**
     * Constructor.
     * @param fr FlightRepository to be used
     * @param as AircraftService to be used
     * @param aps AirportService to be used
     */
    public FlightService(FlightRepository fr, AircraftService as, AirportService aps) {
        this.fr = fr;
        this.as = as;
        this.aps = aps;
    }
    
    /**
     * Indicate that the flight has started taxiing.
     * @param f Flight to start taxiing
     */
    public void startTaxi(Flight f) {
        if (f.getFlightState() != FlightState.INITIAL) {
            return;
        }
        
        f.setFlightState(FlightState.PRE_TAXI);
        f.setDepartureTime(Instant.now());
        
        fr.save(f);
    }
    
    /**
     * Indicate that the flight has taken off.
     * @param f Flight to take off
     */
    public void takeOff(Flight f) {
        if (f.getFlightState() != FlightState.PRE_TAXI) {
            return;
        }
        
        f.setFlightState(FlightState.FLIGHT);
        f.setTakeOffTime(Instant.now());
        f.setNumTakeOffs(f.getNumTakeOffs() + 1);
        
        fr.save(f);
    }

    /**
     * Indicate that the flight has landed.
     * @param f Flight to land
     */
    public void land(Flight f) {
        if (f.getFlightState() != FlightState.FLIGHT) {
            return;
        }
        
        f.setFlightState(FlightState.POST_TAXI);
        f.setNumLandings(f.getNumLandings() + 1);
        f.setLandingTime(Instant.now());

        fr.save(f);
    }
    
    /**
     * Indicate that the flight performed a touch-and-go.
     * @param f Flight that has performed a touch-and-go
     */
    public void touchAndGo(Flight f) {
        if (f.getFlightState() != FlightState.FLIGHT) {
            return;
        }
        
        f.setNumLandings(f.getNumLandings() + 1);
        f.setNumTakeOffs(f.getNumTakeOffs() + 1);

        fr.save(f);
    }
    
    /**
     * Indicate that the flight has been completed.
     * @param f Flight that has been completed
     */
    public void stopFlight(Flight f) {
        f.setFlightState(FlightState.ENDED);
        f.setArrivalTime(Instant.now());
        
        fr.save(f);
    }

    /**
     * Indicate which aircraft was used for the flight.
     * @param f Flight to be updated
     * @param aircraftId Aircraft that was used for the flight
     */
    public void setAircraft(Flight f, String aircraftId) {     
        Aircraft aircraft = as.findOrCreateAircraft(aircraftId);
        f.setAircraft(aircraft);
        
        fr.save(f);
    }

    /**
     * Indicate the airport from which the flight originated.
     * @param f Flight to be updated
     * @param originCode Code of the airport from which the flight originated
     */
    public void setOrigin(Flight f, String originCode) {
        Airport airport = aps.findOrCreateAirport(originCode);
        f.setOrigin(airport);
        
        fr.save(f);    
    }

    /**
     * Indicate the airport to which the flight was destined.
     * @param f Flight to be updated
     * @param destionationCode Code of the airport to which the flight was destined
     */
    public void setDestination(Flight f, String destionationCode) {
        Airport airport = aps.findOrCreateAirport(destionationCode);
        f.setDestination(airport);
        
        fr.save(f);    
    }

    /**
     * Save the flight information to the repository.
     * @param f Flight to be saved
     * @return Saved flight
     */
    public Flight saveFlight(Flight f) {
        return fr.save(f);
    }
}
