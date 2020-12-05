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
 * Business logic related to handling of aircraft
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

    /**
     * Save a single aircraft to the repository
     * @param ac Aircraft to be saved
     * @return Saved aircraft
     */
    
    public Aircraft saveAircraft(Aircraft ac) {
        return ar.save(ac);
    }
    
    /**
     * Find an aircraft from the repository based on identifier, or create
     * it if it does not exist
     * @param identifier Identifier of the aircraft to look for
     * @return Aircraft corresponding to the identifier
     */
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

    /**
     * Get all aircraft stored in the repository
     * @return List of aircraft currently stored
     */
    public List<Aircraft> getAllAircraft() {
        return ar.findAll();
    }
    
    /**
     * Get a list of value objects containing information about the aircraft
     * flown by the pilot passed in as parameter
     * @param p Pilot whose flights to retrieve
     * @return List of aircraft flown by the pilot
     */
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
