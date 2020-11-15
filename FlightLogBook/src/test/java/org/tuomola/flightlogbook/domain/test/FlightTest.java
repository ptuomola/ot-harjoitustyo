package org.tuomola.flightlogbook.domain.test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Date;
import org.junit.Test;
import org.tuomola.flightlogbook.domain.Flight;
import org.tuomola.flightlogbook.domain.FlightState;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 *
 * @author ptuomola
 */
public class FlightTest {
    
    @Test
    public void testFlightStateMachine()
    {
        Flight flight = new Flight();
        assertThat(flight.getFlightState(), equalTo(FlightState.INITIAL));
        flight.startTaxi();
        assertThat(flight.getFlightState(), equalTo(FlightState.PRE_TAXI));
        flight.takeOff();
        assertThat(flight.getFlightState(), equalTo(FlightState.FLIGHT));
        flight.land();
        assertThat(flight.getFlightState(), equalTo(FlightState.POST_TAXI));
        flight.stopFlight();
        assertThat(flight.getFlightState(), equalTo(FlightState.ENDED));
    }
    
    @Test
    public void testCountLandingTakeoff()
    {
        Flight flight = new Flight();
        assertThat(flight.getNumLandings(), is(0));
        assertThat(flight.getNumTakeOffs(), is(0));
        flight.startTaxi();
        flight.takeOff();
        assertThat(flight.getNumLandings(), is(0));
        assertThat(flight.getNumTakeOffs(), is(1));
        flight.land();
        assertThat(flight.getNumLandings(), is(1));
        assertThat(flight.getNumTakeOffs(), is(1));
    }
    
    @Test
    public void testTouchAndGoNumLandingsTakeOffs()
    {
        Flight flight = new Flight();
        assertThat(flight.getNumLandings(), is(0));
        assertThat(flight.getNumTakeOffs(), is(0));
        flight.startTaxi();
        flight.takeOff();
        assertThat(flight.getNumLandings(), is(0));
        assertThat(flight.getNumTakeOffs(), is(1));
        flight.touchAndGo();
        assertThat(flight.getNumLandings(), is(1));
        assertThat(flight.getNumTakeOffs(), is(2));
        flight.touchAndGo();
        assertThat(flight.getNumLandings(), is(2));
        assertThat(flight.getNumTakeOffs(), is(3));
        flight.land();
        assertThat(flight.getNumLandings(), is(3));
        assertThat(flight.getNumTakeOffs(), is(3));
    }
    
    @Test
    public void testDurationFromStates() throws InterruptedException
    {
        Flight flight = new Flight();
        assertThat(flight.getDuration(), is(nullValue()));
        flight.startTaxi();
        flight.takeOff();
        Thread.sleep(5100L);
        flight.land();
        flight.stopFlight();
        assertThat(flight.getDuration(), greaterThan(Duration.of(5, ChronoUnit.SECONDS)));
    }
    
    @Test
    public void testFlightDurationFromStates() throws InterruptedException
    {
        Flight flight = new Flight();
        assertThat(flight.getDuration(), is(nullValue()));
        flight.startTaxi();
        flight.takeOff();
        Thread.sleep(5100L);
        flight.land();
        flight.stopFlight();
        assertThat(flight.getFlightDuration(), greaterThan(Duration.of(5, ChronoUnit.SECONDS)));
    }
        
    @Test
    public void testDurationBySetting() throws InterruptedException
    {
        Flight flight = new Flight();
        assertThat(flight.getDuration(), is(nullValue()));
        flight.setDepartureTime(Date.from(LocalDateTime.of(2020, Month.NOVEMBER, 1, 12, 0, 0).atZone(ZoneId.systemDefault()).toInstant()));
        flight.setArrivalTime(Date.from(LocalDateTime.of(2020, Month.NOVEMBER, 1, 13, 0, 0).atZone(ZoneId.systemDefault()).toInstant()));
        assertThat(flight.getDuration(), equalTo(Duration.of(1, ChronoUnit.HOURS)));
    }

    @Test
    public void testFlightDurationBySetting() throws InterruptedException
    {
        Flight flight = new Flight();
        assertThat(flight.getFlightDuration(), is(nullValue()));
        flight.setTakeOffTime(Date.from(LocalDateTime.of(2020, Month.NOVEMBER, 1, 12, 0, 0).atZone(ZoneId.systemDefault()).toInstant()));
        flight.setLandingTime(Date.from(LocalDateTime.of(2020, Month.NOVEMBER, 1, 13, 0, 0).atZone(ZoneId.systemDefault()).toInstant()));
        assertThat(flight.getFlightDuration(), equalTo(Duration.of(1, ChronoUnit.HOURS)));
    }

}
