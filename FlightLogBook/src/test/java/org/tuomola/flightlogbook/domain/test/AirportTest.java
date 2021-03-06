package org.tuomola.flightlogbook.domain.test;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.util.Collection;
import java.util.Date;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.apache.commons.lang3.RandomStringUtils;
import org.tuomola.flightlogbook.domain.Flight;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.tuomola.flightlogbook.domain.Airport;
import org.tuomola.flightlogbook.domain.FlightLog;
import org.tuomola.flightlogbook.domain.Pilot;
import org.tuomola.flightlogbook.service.AirportService;
import org.tuomola.flightlogbook.service.FlightLogService;
import org.tuomola.flightlogbook.service.FlightService;
import org.tuomola.flightlogbook.dto.PilotAirportDTO;
import org.tuomola.flightlogbook.service.PilotService;

/**
 *
 * @author ptuomola
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class AirportTest {
    
    @Autowired 
    private FlightLogService fls;
    
    @Autowired
    private FlightService fs;
    
    @Autowired
    private PilotService ps;
    
    @Autowired
    private AirportService aps;

    @Test
    public void testNoAirportsVisited()
    {
        Pilot p = new Pilot();
        p.setUserName(RandomStringUtils.randomAlphanumeric(10));
        ps.savePilot(p);
        FlightLog fl = fls.findOrCreateLog(p);
        Collection<PilotAirportDTO> airportsVisited = aps.getAllAirportsWithVisits(p);
        assertThat(airportsVisited.isEmpty(), is(true));
    }

    @Test
    public void testSingleDepartureAirportVisited()
    {
        Pilot p = new Pilot();
        p.setUserName(RandomStringUtils.randomAlphanumeric(10));
        ps.savePilot(p);
        FlightLog fl = fls.findOrCreateLog(p);
        Flight flight = fls.addNewFlight(fl);
        String originCode = RandomStringUtils.randomAlphanumeric(4).toUpperCase();
        fs.setOrigin(flight, originCode);
        flight.setDepartureTime(LocalDateTime.of(2020, Month.NOVEMBER, 1, 12, 0, 0).atZone(ZoneId.systemDefault()).toInstant());
        fs.saveFlight(flight);
        Collection<PilotAirportDTO> airportsVisited = aps.getAllAirportsWithVisits(p);
        assertThat(airportsVisited.size(), is(1));
        PilotAirportDTO visitedAirport = airportsVisited.iterator().next();
        assertThat(visitedAirport.getCode(), equalTo(originCode));
        assertThat(visitedAirport.getNumDepartures(), is(1));
        assertThat(visitedAirport.getNumArrivals(), is(0));
        assertThat(visitedAirport.getLastVisit().equals(Date.from(LocalDateTime.of(2020, Month.NOVEMBER, 1, 12, 0, 0).atZone(ZoneId.systemDefault()).toInstant())), is(true));
    }
    
    @Test
    public void testSingleArrivalAirportVisited()
    {
        Pilot p = new Pilot();
        p.setUserName(RandomStringUtils.randomAlphanumeric(10));
        ps.savePilot(p);
        FlightLog fl = fls.findOrCreateLog(p);
        Flight flight = fls.addNewFlight(fl);
        String destinationCode = RandomStringUtils.randomAlphanumeric(4).toUpperCase();
        fs.setDestination(flight, destinationCode);
        flight.setArrivalTime(LocalDateTime.of(2020, Month.NOVEMBER, 1, 12, 0, 0).atZone(ZoneId.systemDefault()).toInstant());
        fs.saveFlight(flight);
        Collection<PilotAirportDTO> airportsVisited = aps.getAllAirportsWithVisits(p);
        assertThat(airportsVisited.size(), is(1));
        PilotAirportDTO visitedAirport = airportsVisited.iterator().next();
        assertThat(visitedAirport.getCode(), equalTo(destinationCode));
        assertThat(visitedAirport.getNumDepartures(), is(0));
        assertThat(visitedAirport.getNumArrivals(), is(1));
        assertThat(visitedAirport.getLastVisit().equals(Date.from(LocalDateTime.of(2020, Month.NOVEMBER, 1, 12, 0, 0).atZone(ZoneId.systemDefault()).toInstant())), is(true));
    }
    
    @Test
    public void testMultipleAirportVisits()
    {
        Pilot p = new Pilot();
        p.setUserName(RandomStringUtils.randomAlphanumeric(10));
        ps.savePilot(p);
        FlightLog fl = fls.findOrCreateLog(p);
        String destinationCode = RandomStringUtils.randomAlphanumeric(4).toUpperCase();
        String originCode = RandomStringUtils.randomAlphanumeric(4).toUpperCase();

        for(int i = 0; i < 10; i++) {
            Flight flight = fls.addNewFlight(fl);
            fs.setOrigin(flight, originCode);
            fs.setDestination(flight, destinationCode);
            flight.setDepartureTime(LocalDateTime.of(2020, Month.NOVEMBER, 1+i, 12, 0, 0).atZone(ZoneId.systemDefault()).toInstant());
            flight.setArrivalTime(LocalDateTime.of(2020, Month.NOVEMBER, 2+i, 12, 0, 0).atZone(ZoneId.systemDefault()).toInstant());
            fs.saveFlight(flight);
        }

        Collection<PilotAirportDTO> airportsVisited = aps.getAllAirportsWithVisits(p);
        assertThat(airportsVisited.size(), is(2));
        PilotAirportDTO visitedAirport = airportsVisited.iterator().next();

        if(visitedAirport.getCode().equals(destinationCode)) {
            assertThat(visitedAirport.getNumDepartures(), is(0));
            assertThat(visitedAirport.getNumArrivals(), is(10));
            assertThat(visitedAirport.getLastVisit().equals(Date.from(LocalDateTime.of(2020, Month.NOVEMBER, 11, 12, 0, 0).atZone(ZoneId.systemDefault()).toInstant())), is(true));
        } else {
            assertThat(visitedAirport.getCode(), equalTo(originCode));
            assertThat(visitedAirport.getNumDepartures(), is(10));
            assertThat(visitedAirport.getNumArrivals(), is(0));
            assertThat(visitedAirport.getLastVisit().equals(Date.from(LocalDateTime.of(2020, Month.NOVEMBER, 10, 12, 0, 0).atZone(ZoneId.systemDefault()).toInstant())), is(true));
        }   
    }
    
    @Test
    public void testAirport() 
    {
        EqualsVerifier.forClass(Airport.class).verify();
    }
    
}
