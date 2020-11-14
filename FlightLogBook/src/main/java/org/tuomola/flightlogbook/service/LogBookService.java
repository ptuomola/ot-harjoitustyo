package org.tuomola.flightlogbook.service;

import org.tuomola.flightlogbook.domain.Flight;
import org.tuomola.flightlogbook.domain.FlightLog;
import org.tuomola.flightlogbook.domain.Pilot;

/**
 *
 * @author ptuomola
 */
public class LogBookService {
    
    public FlightLog createLog(String name)
    {
        Pilot pilot = new Pilot(name);
        return new FlightLog(pilot);
    }
    
    
}
