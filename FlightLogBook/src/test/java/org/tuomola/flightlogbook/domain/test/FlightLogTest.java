package org.tuomola.flightlogbook.domain.test;

import java.time.Duration;
import java.time.Instant;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import org.apache.commons.lang3.RandomStringUtils;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.is;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.tuomola.flightlogbook.domain.Flight;
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
public class FlightLogTest {
    
    @Autowired 
    private FlightLogService fls;
    
    @Autowired
    private FlightService fs;
    
    @Autowired
    private PilotService ps;

    @Test
    public void testEmptyLog() throws InterruptedException
    {
        Pilot p = new Pilot();
        p.setUserName(RandomStringUtils.randomAlphanumeric(10));
        ps.savePilot(p);
        FlightLog fl = fls.findOrCreateLog(p);
        assertThat(fl.getTotalLandings(), is(0));
        assertThat(fl.getTotalTakeOffs(), is(0));
        assertThat(fl.getLandingsDuringLast(Period.ofDays(90)), is(0));
        assertThat(fl.getTakeOffsDuringLast(Period.ofDays(90)), is(0));
        assertThat(fl.getFlightTimeDuringLast(Period.ofDays(365)), is(Duration.ZERO));
        assertThat(fl.getTotalFlightTime(), is(Duration.ZERO));
        assertThat(fl.getTotalTime(), is(Duration.ZERO));
    }
    
    @Test
    public void testAllWithinLast3Months() throws InterruptedException
    {
        Pilot p = new Pilot();
        p.setUserName(RandomStringUtils.randomAlphanumeric(10));
        ps.savePilot(p);
        FlightLog fl = fls.findOrCreateLog(p);
        
        for (int i = 0; i < 10; i++) {
            Flight f = fls.addNewFlight(fl);
            f.setDepartureTime(Instant.now().minus(4, ChronoUnit.HOURS));
            f.setTakeOffTime(Instant.now().minus(3, ChronoUnit.HOURS));
            f.setLandingTime(Instant.now().minus(2, ChronoUnit.HOURS));
            f.setArrivalTime(Instant.now().minus(1, ChronoUnit.HOURS));
            f.setNumLandings(1);
            f.setNumTakeOffs(1);
            fs.saveFlight(f);
        }
        
        assertThat(fl.getTotalLandings(), is(10));
        assertThat(fl.getTotalTakeOffs(), is(10));
        assertThat(fl.getLandingsDuringLast(Period.ofDays(90)), is(10));
        assertThat(fl.getTakeOffsDuringLast(Period.ofDays(90)), is(10));
        assertThat(Duration.of(10, ChronoUnit.HOURS).minus(fl.getFlightTimeDuringLast(Period.ofDays(365))), lessThan(Duration.ofSeconds(1)));
        assertThat(Duration.of(10, ChronoUnit.HOURS).minus(fl.getTotalFlightTime()), lessThan(Duration.ofSeconds(1)));
        assertThat(Duration.of(30, ChronoUnit.HOURS).minus(fl.getTotalTime()), lessThan(Duration.ofSeconds(1)));
    }
    
    @Test
    public void testAllOlderThan12Months() throws InterruptedException
    {
        Pilot p = new Pilot();
        p.setUserName(RandomStringUtils.randomAlphanumeric(10));
        ps.savePilot(p);
        FlightLog fl = fls.findOrCreateLog(p);
        
        for (int i = 0; i < 10; i++) {
            Flight f = fls.addNewFlight(fl);
            f.setDepartureTime(Instant.now().minus(365, ChronoUnit.DAYS).minus(4, ChronoUnit.HOURS));
            f.setTakeOffTime(Instant.now().minus(365, ChronoUnit.DAYS).minus(3, ChronoUnit.HOURS));
            f.setLandingTime(Instant.now().minus(365, ChronoUnit.DAYS).minus(2, ChronoUnit.HOURS));
            f.setArrivalTime(Instant.now().minus(365, ChronoUnit.DAYS).minus(1, ChronoUnit.HOURS));
            f.setNumLandings(1);
            f.setNumTakeOffs(1);
            fs.saveFlight(f);
        }
        
        assertThat(fl.getTotalLandings(), is(10));
        assertThat(fl.getTotalTakeOffs(), is(10));
        assertThat(fl.getLandingsDuringLast(Period.ofDays(90)), is(0));
        assertThat(fl.getTakeOffsDuringLast(Period.ofDays(90)), is(0));
        assertThat(Duration.of(0, ChronoUnit.HOURS).minus(fl.getFlightTimeDuringLast(Period.ofDays(365))), lessThan(Duration.ofSeconds(1)));
        assertThat(Duration.of(10, ChronoUnit.HOURS).minus(fl.getTotalFlightTime()), lessThan(Duration.ofSeconds(1)));
        assertThat(Duration.of(30, ChronoUnit.HOURS).minus(fl.getTotalTime()), lessThan(Duration.ofSeconds(1)));
    }

    
}
