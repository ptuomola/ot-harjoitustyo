package org.tuomola.flightlogbook.service;

import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tuomola.flightlogbook.dao.FlightRepository;
import org.tuomola.flightlogbook.domain.Aircraft;
import org.tuomola.flightlogbook.domain.Airport;
import org.tuomola.flightlogbook.domain.Flight;
import org.tuomola.flightlogbook.domain.FlightState;

/**
 *
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

    public FlightService(FlightRepository fr, AircraftService as, AirportService aps)
    {
        this.fr = fr;
        this.as = as;
        this.aps = aps;
    }
    
    public void startTaxi(Flight f)
    {
        if(f.getFlightState() != FlightState.INITIAL)
            return;
        
        f.setFlightState(FlightState.PRE_TAXI);
        f.setDepartureTime(new Date());
        
        fr.save(f);
    }
    
    public void takeOff(Flight f)
    {
        if(f.getFlightState() != FlightState.PRE_TAXI)
            return;
        
        f.setFlightState(FlightState.FLIGHT);
        f.setTakeOffTime(new Date());
        f.setNumTakeOffs(f.getNumTakeOffs() + 1);
        
        fr.save(f);
    }
    
    public void land(Flight f)
    {
        if(f.getFlightState() != FlightState.FLIGHT)
            return;
        
        f.setFlightState(FlightState.POST_TAXI);
        f.setNumLandings(f.getNumLandings() + 1);
        f.setLandingTime(new Date());

        fr.save(f);
    }
    
    public void touchAndGo(Flight f)
    {
        if(f.getFlightState() != FlightState.FLIGHT)
            return;
        
        f.setNumLandings(f.getNumLandings() + 1);
        f.setNumTakeOffs(f.getNumTakeOffs() + 1);

        fr.save(f);
    }
    
    public void stopFlight(Flight f)
    {
        f.setFlightState(FlightState.ENDED);
        f.setArrivalTime(new Date());
        
        fr.save(f);
    }

    public void setAircraft(Flight f, String aircraftId) {
        
        Aircraft aircraft = as.findOrCreateAircraft(aircraftId);
        f.setAircraft(aircraft);
        
        fr.save(f);
    }

    public void setOrigin(Flight f, String originCode) {
        Airport airport = aps.findOrCreateAirport(originCode);
        f.setOrigin(airport);
        
        fr.save(f);    
    }

    public void setDestination(Flight f, String destionationCode) {
        Airport airport = aps.findOrCreateAirport(destionationCode);
        f.setDestination(airport);
        
        fr.save(f);    
    }
}
