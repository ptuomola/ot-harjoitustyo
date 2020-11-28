package org.tuomola.flightlogbook.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tuomola.flightlogbook.dao.FlightLogRepository;
import org.tuomola.flightlogbook.dao.FlightRepository;
import org.tuomola.flightlogbook.dao.PilotRepository;
import org.tuomola.flightlogbook.domain.Flight;
import org.tuomola.flightlogbook.domain.FlightLog;
import org.tuomola.flightlogbook.domain.Pilot;

/**
 *
 * @author ptuomola
 */

@Service
public class FlightLogService {
    
    @Autowired
    private final PilotRepository pr;
    
    @Autowired
    private final FlightLogRepository flr;
    
    @Autowired
    private final FlightRepository fr;
    
    public FlightLogService(PilotRepository pr, FlightLogRepository flr, FlightRepository fr) {
        this.pr = pr;
        this.flr = flr;
        this.fr = fr;
    }
    
    public FlightLog findOrCreateLog(Pilot p) {
        FlightLog fl = flr.findByLogOwner(p);
        
        if (fl == null) {
            System.out.println("New flightlog - creating record");

            fl = new FlightLog();
            fl.setLogOwner(p);
            flr.save(fl);
        }
        
        return fl;
    }
    
    public Flight addNewFlight(FlightLog fl) {
        Flight flight = new Flight();
        flight.setPic(fl.getLogOwner());
        fr.save(flight);

        fl.addFlight(flight);
        flr.save(fl);
        
        return flight;
    }   
}
