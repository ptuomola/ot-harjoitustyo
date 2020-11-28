package org.tuomola.flightlogbook.domain.test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import org.apache.commons.lang3.RandomStringUtils;
import org.tuomola.flightlogbook.domain.Flight;
import org.tuomola.flightlogbook.domain.FlightState;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.tuomola.flightlogbook.domain.FlightLog;
import org.tuomola.flightlogbook.domain.Pilot;
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
public class FlightTest {
    
    @Autowired 
    private FlightLogService fls;
    
    @Autowired
    private FlightService fs;
    
    @Autowired
    private PilotService ps;

    @Test
    public void testFlightStateMachine()
    {
        Pilot p = new Pilot(RandomStringUtils.randomAlphanumeric(10));
        ps.savePilot(p);
        FlightLog fl = fls.findOrCreateLog(p);
        Flight flight = fls.addNewFlight(fl);
        assertThat(flight.getFlightState(), equalTo(FlightState.INITIAL));
        fs.startTaxi(flight);
        assertThat(flight.getFlightState(), equalTo(FlightState.PRE_TAXI));
        fs.takeOff(flight);
        assertThat(flight.getFlightState(), equalTo(FlightState.FLIGHT));
        fs.land(flight);
        assertThat(flight.getFlightState(), equalTo(FlightState.POST_TAXI));
        fs.stopFlight(flight);
        assertThat(flight.getFlightState(), equalTo(FlightState.ENDED));
    }
    
    @Test
    public void testCountLandingTakeoff()
    {
        Pilot p = new Pilot(RandomStringUtils.randomAlphanumeric(10));
        ps.savePilot(p);
        FlightLog fl = fls.findOrCreateLog(p);
        Flight flight = fls.addNewFlight(fl);
        assertThat(flight.getNumLandings(), is(0));
        assertThat(flight.getNumTakeOffs(), is(0));
        fs.startTaxi(flight);
        fs.takeOff(flight);
        assertThat(flight.getNumLandings(), is(0));
        assertThat(flight.getNumTakeOffs(), is(1));
        fs.land(flight);
        assertThat(flight.getNumLandings(), is(1));
        assertThat(flight.getNumTakeOffs(), is(1));
    }
    
    @Test
    public void testTouchAndGoNumLandingsTakeOffs()
    {
        Pilot p = new Pilot(RandomStringUtils.randomAlphanumeric(10));
        ps.savePilot(p);
        FlightLog fl = fls.findOrCreateLog(p);
        Flight flight = fls.addNewFlight(fl);
        assertThat(flight.getNumLandings(), is(0));
        assertThat(flight.getNumTakeOffs(), is(0));
        fs.startTaxi(flight);
        fs.takeOff(flight);
        assertThat(flight.getNumLandings(), is(0));
        assertThat(flight.getNumTakeOffs(), is(1));
        fs.touchAndGo(flight);
        assertThat(flight.getNumLandings(), is(1));
        assertThat(flight.getNumTakeOffs(), is(2));
        fs.touchAndGo(flight);
        assertThat(flight.getNumLandings(), is(2));
        assertThat(flight.getNumTakeOffs(), is(3));
        fs.land(flight);
        assertThat(flight.getNumLandings(), is(3));
        assertThat(flight.getNumTakeOffs(), is(3));
    }
    
    @Test
    public void testDurationFromStates() throws InterruptedException
    {
        Pilot p = new Pilot(RandomStringUtils.randomAlphanumeric(10));
        ps.savePilot(p);
        FlightLog fl = fls.findOrCreateLog(p);
        Flight flight = fls.addNewFlight(fl);
        assertThat(flight.getDuration(), is(nullValue()));
        fs.startTaxi(flight);
        fs.takeOff(flight);
        Thread.sleep(5100L);
        fs.land(flight);
        fs.stopFlight(flight);
        assertThat(flight.getDuration(), greaterThan(Duration.of(5, ChronoUnit.SECONDS)));
    }
    
    @Test
    public void testFlightDurationFromStates() throws InterruptedException
    {
        Pilot p = new Pilot(RandomStringUtils.randomAlphanumeric(10));
        ps.savePilot(p);
        FlightLog fl = fls.findOrCreateLog(p);
        Flight flight = fls.addNewFlight(fl);
        assertThat(flight.getDuration(), is(nullValue()));
        fs.startTaxi(flight);
        fs.takeOff(flight);
        Thread.sleep(5100L);
        fs.land(flight);
        fs.stopFlight(flight);
        assertThat(flight.getFlightDuration(), greaterThan(Duration.of(5, ChronoUnit.SECONDS)));
    }
        
    @Test
    public void testDurationBySetting() throws InterruptedException
    {
        Pilot p = new Pilot(RandomStringUtils.randomAlphanumeric(10));
        ps.savePilot(p);
        FlightLog fl = fls.findOrCreateLog(p);
        Flight flight = fls.addNewFlight(fl);
        assertThat(flight.getDuration(), is(nullValue()));
        flight.setDepartureTime(LocalDateTime.of(2020, Month.NOVEMBER, 1, 12, 0, 0).atZone(ZoneId.systemDefault()).toInstant());
        flight.setArrivalTime(LocalDateTime.of(2020, Month.NOVEMBER, 1, 13, 0, 0).atZone(ZoneId.systemDefault()).toInstant());
        assertThat(flight.getDuration(), equalTo(Duration.of(1, ChronoUnit.HOURS)));
    }

    @Test
    public void testFlightDurationBySetting() throws InterruptedException
    {
        Pilot p = new Pilot(RandomStringUtils.randomAlphanumeric(10));
        ps.savePilot(p);
        FlightLog fl = fls.findOrCreateLog(p);
        Flight flight = fls.addNewFlight(fl);
        flight.setTakeOffTime(LocalDateTime.of(2020, Month.NOVEMBER, 1, 12, 0, 0).atZone(ZoneId.systemDefault()).toInstant());
        flight.setLandingTime(LocalDateTime.of(2020, Month.NOVEMBER, 1, 13, 0, 0).atZone(ZoneId.systemDefault()).toInstant());
        assertThat(flight.getFlightDuration(), equalTo(Duration.of(1, ChronoUnit.HOURS)));
    }
    
    @Test
    public void testSetOrigin() {
        String originCode = RandomStringUtils.randomAlphanumeric(4).toUpperCase();
        Pilot p = new Pilot(RandomStringUtils.randomAlphanumeric(10));
        ps.savePilot(p);
        FlightLog fl = fls.findOrCreateLog(p);
        Flight flight = fls.addNewFlight(fl);
        fs.setOrigin(flight, originCode);
        assertThat(flight.getOrigin().getCode(), equalTo(originCode));
    }
    
    @Test
    public void testSetDestination() {
        String destinationCode = RandomStringUtils.randomAlphanumeric(4).toUpperCase();
        Pilot p = new Pilot(RandomStringUtils.randomAlphanumeric(10));
        ps.savePilot(p);
        FlightLog fl = fls.findOrCreateLog(p);
        Flight flight = fls.addNewFlight(fl);
        fs.setDestination(flight, destinationCode);
        assertThat(flight.getDestination().getCode(), equalTo(destinationCode));
    }
    
    @Test
    public void testSetAircraft() {
        String aircraftId = "OH-" + RandomStringUtils.randomAlphanumeric(3).toUpperCase();
        Pilot p = new Pilot(RandomStringUtils.randomAlphanumeric(10));
        ps.savePilot(p);
        FlightLog fl = fls.findOrCreateLog(p);
        Flight flight = fls.addNewFlight(fl);
        fs.setAircraft(flight, aircraftId);
        assertThat(flight.getAircraft().getIdentifier(), equalTo(aircraftId));
        
    }

}
