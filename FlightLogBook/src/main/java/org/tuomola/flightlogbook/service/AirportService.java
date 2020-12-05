package org.tuomola.flightlogbook.service;

import java.time.Duration;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tuomola.flightlogbook.dao.AirportRepository;
import org.tuomola.flightlogbook.dao.FlightRepository;
import org.tuomola.flightlogbook.dao.PilotAircraftDAO;
import org.tuomola.flightlogbook.dao.PilotAirportDAO;
import org.tuomola.flightlogbook.domain.Airport;
import org.tuomola.flightlogbook.domain.Flight;
import org.tuomola.flightlogbook.domain.Pilot;

/**
 *
 * @author ptuomola
 */
@Service
public class AirportService {

    @Autowired
    private final AirportRepository ar;

    @Autowired
    private final FlightRepository fr;
    
    public AirportService(AirportRepository ar, FlightRepository fr) {
        this.ar = ar;
        this.fr = fr;
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
    
    public Collection<PilotAirportDAO> getAllAirportsWithVisits(Pilot p) {
        List<Flight> flights = fr.getByPic(p);

        if(flights == null) {
            return null;
        }
        
        HashMap<String, PilotAirportDAO> airportMap = new HashMap<>();
        
        for (Flight f : flights) {
            processAirport(airportMap, f.getOrigin(), f.getFlightDate(), dao -> dao.incrementDepartures());
            processAirport(airportMap, f.getDestination(), f.getArrivalDate(), dao -> dao.incrementArrivals());
        }

        return airportMap.values();
    }

    private void processAirport(HashMap<String, PilotAirportDAO> airportMap, Airport a, Date visitDate, Consumer <PilotAirportDAO> increment)
    {
        if(a == null) { 
            return;
        }
        
        PilotAirportDAO dao = airportMap.get(a.getCode());

        if (dao == null) {
            dao = new PilotAirportDAO(a);
            airportMap.put(a.getCode(), dao);
        }

        dao.setLastVisitIfBefore(visitDate);
        increment.accept(dao);
    }
}
