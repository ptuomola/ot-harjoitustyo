package org.tuomola.flightlogbook.service;

import java.time.Duration;
import java.time.ZoneOffset;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tuomola.flightlogbook.dao.AircraftRepository;
import org.tuomola.flightlogbook.dao.FlightRepository;
import org.tuomola.flightlogbook.domain.Aircraft;
import org.tuomola.flightlogbook.domain.Flight;
import org.tuomola.flightlogbook.domain.Pilot;

/**
 *
 * @author ptuomola
 */
@Service
public class AircraftService {

    @Autowired
    private final AircraftRepository ar;
    
    @Autowired
    private final FlightRepository fr;
    
    public AircraftService(AircraftRepository ar, FlightRepository fr) {
        this.ar = ar;
        this.fr = fr;
    }

    public Aircraft saveAircraft(Aircraft ac) {
        return ar.save(ac);
    }
    
    public Aircraft findOrCreateAircraft(String identifier) {
        identifier = identifier.toUpperCase();
        
        Aircraft aircraft = ar.findOneByIdentifier(identifier);
        
        if (aircraft == null) {
            aircraft = new Aircraft();
            aircraft.setIdentifier(identifier);
            
            ar.save(aircraft);
        }
        
        return aircraft;
    }

    public List<Aircraft> getAllAircraft() {
        return ar.findAll();
    }
    
    public Collection<PilotAircraftVO> getAllAircraftWithFlightData(Pilot p) {
        List<Flight> flights = fr.getByPic(p);

        if (flights == null) {
            return null;
        }
        
        HashMap<String, PilotAircraftVO> aircraftMap = new HashMap<>();
        
        for (Flight f : flights) {
            if (f.getAircraft() == null) {
                continue;
            }
            
            PilotAircraftVO dao = aircraftMap.get(f.getAircraft().getIdentifier());
            
            if (dao == null) {
                dao = new PilotAircraftVO(f.getAircraft());
                aircraftMap.put(f.getAircraft().getIdentifier(), dao);
            }
            
            dao.addToHoursFlown(f.getDuration());
            dao.setLastDateIfBefore(f.getFlightDate());            
        }
        
        return aircraftMap.values();
    }
}
