package org.tuomola.flightlogbook.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tuomola.flightlogbook.dao.AircraftRepository;
import org.tuomola.flightlogbook.domain.Aircraft;

/**
 *
 * @author ptuomola
 */
@Service
public class AircraftService {

    @Autowired
    private final AircraftRepository ar;

    public AircraftService(AircraftRepository ar) {
        this.ar = ar;
    }

    public Aircraft saveAircraft(Aircraft ac){
        return ar.save(ac);
    }
    
    public Aircraft findOrCreateAircraft(String identifier){
        identifier = identifier.toUpperCase();
        
        Aircraft aircraft = ar.findOneByIdentifier(identifier);
        
        if(aircraft == null)
        {
            aircraft = new Aircraft();
            aircraft.setIdentifier(identifier);
            
            ar.save(aircraft);
        }
        
        return aircraft;
    }

    public List<Aircraft> getAllAircraft() {
        return ar.findAll();
    }
}
