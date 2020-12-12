package org.tuomola.flightlogbook.domain.test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.Period;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Date;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.apache.commons.lang3.RandomStringUtils;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThan;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.tuomola.flightlogbook.domain.Aircraft;
import org.tuomola.flightlogbook.domain.Flight;
import org.tuomola.flightlogbook.domain.FlightLog;
import org.tuomola.flightlogbook.domain.Pilot;
import org.tuomola.flightlogbook.dto.PilotAircraftDTO;
import org.tuomola.flightlogbook.dto.PilotAirportDTO;
import org.tuomola.flightlogbook.service.AircraftService;
import org.tuomola.flightlogbook.service.FlightLogService;
import org.tuomola.flightlogbook.service.FlightService;
import org.tuomola.flightlogbook.service.PilotService;

/**
 *
 * @author ptuomola
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class AircraftTest {

    @Autowired
    private AircraftService acs;
    
    @Autowired
    private PilotService ps;
    
    @Autowired
    private FlightLogService fls;
    
    @Autowired
    private FlightService fs;
    
    @Test
    public void testAircraftDAO() 
    {
        EqualsVerifier.forClass(Aircraft.class).verify();
    }
    
    @Test
    public void testNoAircraftFlown()
    {
        Pilot p = new Pilot();
        p.setUserName(RandomStringUtils.randomAlphanumeric(10));
        ps.savePilot(p);
        FlightLog fl = fls.findOrCreateLog(p);
        Collection<PilotAircraftDTO> aircraftFlown = acs.getAllAircraftWithFlightData(p);
        assertThat(aircraftFlown.isEmpty(), is(true));
    }
    
    @Test
    public void testSingleAircraftFlown()
    {
        Pilot p = new Pilot();
        p.setUserName(RandomStringUtils.randomAlphanumeric(10));
        ps.savePilot(p);
        FlightLog fl = fls.findOrCreateLog(p);
        Flight flight = fls.addNewFlight(fl);
        String registration = "OH-" + RandomStringUtils.randomAlphanumeric(3).toUpperCase();
        fs.setAircraft(flight, registration);
        flight.setDepartureTime(LocalDateTime.of(2020, Month.NOVEMBER, 1, 12, 0, 0).atZone(ZoneId.systemDefault()).toInstant());
        flight.setArrivalTime(LocalDateTime.of(2020, Month.NOVEMBER, 1, 13, 0, 0).atZone(ZoneId.systemDefault()).toInstant());
        fs.saveFlight(flight);
        Collection<PilotAircraftDTO> aircraftFlown = acs.getAllAircraftWithFlightData(p);
        assertThat(aircraftFlown.size(), is(1));
        PilotAircraftDTO flownAircraft = aircraftFlown.iterator().next();
        assertThat(flownAircraft.getIdentifier(), equalTo(registration));
        assertThat(flownAircraft.getLastFlight().equals(Date.from(LocalDateTime.of(2020, Month.NOVEMBER, 1, 12, 0, 0).atZone(ZoneId.systemDefault()).toInstant())), is(true));
        assertThat(Duration.of(1, ChronoUnit.HOURS).minus(flownAircraft.getHoursFlown()), lessThan(Duration.ofSeconds(1)));
    }
}
