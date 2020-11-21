package org.tuomola.flightlogbook.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tuomola.flightlogbook.dao.AircraftRepository;
import org.tuomola.flightlogbook.dao.AirportRepository;
import org.tuomola.flightlogbook.domain.Aircraft;
import org.tuomola.flightlogbook.domain.Airport;

/**
 *
 * @author ptuomola
 */
@Service
public class AirportService {

    @Autowired
    private final AirportRepository ar;

    public AirportService(AirportRepository ar) {
        this.ar = ar;
    }

    public Airport saveAirport(Airport ap) {
        return ar.save(ap);
    }
    
    public Airport findOrCreateAirport(String code) {
        code = code.toUpperCase();
        
        Airport airport = ar.findOneByCode(code);
        
        if (airport == null) {
            airport = new Airport();
            airport.setCode(code);
            
            ar.save(airport);
        }
        
        return airport;
    }

    public List<Airport> getAllAirports() {
        return ar.findAll();
    }
}
