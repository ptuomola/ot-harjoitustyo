package org.tuomola.flightlogbook.service;

import org.tuomola.flightlogbook.dto.PilotAirportDTO;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tuomola.flightlogbook.dao.AirportRepository;
import org.tuomola.flightlogbook.dao.FlightRepository;
import org.tuomola.flightlogbook.domain.Airport;
import org.tuomola.flightlogbook.domain.Flight;
import org.tuomola.flightlogbook.domain.Pilot;

/**
 * Business logic related to handling of airports.
 * @author ptuomola
 */
@Service
public class AirportService {

    @Autowired
    private final AirportRepository ar;

    @Autowired
    private final FlightRepository fr;
    
    /**
     * Constructor.
     * @param ar AirportRepository to be used
     * @param fr FlightRepository to be used
     */
    public AirportService(AirportRepository ar, FlightRepository fr) {
        this.ar = ar;
        this.fr = fr;
    }

    /**
     * Save single airport into the repository.
     * @param ap Airport to be saved
     * @return Saved airport
     */
    public Airport saveAirport(Airport ap) {
        return ar.save(ap);
    }
    
    /**
     * Find an airport based on the code, or create one if it does not exist.
     * @param code Code of the airport to be retrieved
     * @return Airport corresponding to the code
     */
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

    /**
     * Get all airports from the repository.
     * @return list of all airports currently known
     */
    public List<Airport> getAllAirports() {
        return ar.findAll();
    }
    
    /**
     * Get value objects representing all airports visited by a pilot.
     * @param p Pilot whose visits are to be retrieved
     * @return List of airports visited by the pilot
     */
    public Collection<PilotAirportDTO> getAllAirportsWithVisits(Pilot p) {
        List<Flight> flights = fr.getByPic(p);

        if (flights == null) {
            return null;
        }
        
        HashMap<String, PilotAirportDTO> airportMap = new HashMap<>();
        
        for (Flight f : flights) {
            processAirport(airportMap, f.getOrigin(), f.getFlightDate(), dao -> dao.incrementDepartures());
            processAirport(airportMap, f.getDestination(), f.getArrivalDate(), dao -> dao.incrementArrivals());
        }

        return airportMap.values();
    }

    private void processAirport(HashMap<String, PilotAirportDTO> airportMap, Airport a, Date visitDate, Consumer<PilotAirportDTO> increment) {
        if (a == null) { 
            return;
        }
        
        PilotAirportDTO dao = airportMap.get(a.getCode());

        if (dao == null) {
            dao = new PilotAirportDTO(a);
            airportMap.put(a.getCode(), dao);
        }

        dao.setLastVisitIfBefore(visitDate);
        increment.accept(dao);
    }
}
