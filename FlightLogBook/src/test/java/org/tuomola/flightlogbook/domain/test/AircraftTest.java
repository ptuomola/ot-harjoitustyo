package org.tuomola.flightlogbook.domain.test;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Date;
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
import org.tuomola.flightlogbook.domain.Aircraft;
import org.tuomola.flightlogbook.domain.FlightLog;
import org.tuomola.flightlogbook.domain.Pilot;
import org.tuomola.flightlogbook.service.AircraftService;
import org.tuomola.flightlogbook.service.AirportService;
import org.tuomola.flightlogbook.service.FlightLogService;
import org.tuomola.flightlogbook.service.FlightService;
import org.tuomola.flightlogbook.service.PasswordService;
import org.tuomola.flightlogbook.service.PilotAircraftVO;
import org.tuomola.flightlogbook.service.PilotAirportVO;
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
    private AircraftService as;
    
    @Autowired
    private PilotService ps;
    
    

    @Test
    public void testPilotAircraftVO() 
    {
        PilotAircraftVO vo1 = new PilotAircraftVO();
        PilotAircraftVO vo2 = new PilotAircraftVO();
        
        vo1.setIdentifier("ABCD");
        vo2.setIdentifier("ABCD");
        
        vo1.setType("DV20");
        vo2.setType("DV20");
        
        vo1.setLastFlight(new Date());
        vo2.setLastFlight(new Date());

        vo1.setHoursFlown(Duration.ofHours(1));
        vo2.setHoursFlown(Duration.ofHours(1));
        
        assertThat(vo1, equalTo(vo2));
        assertThat(vo1.hashCode(), is(vo2.hashCode()));
    }
    
    @Test
    public void testAircraftDAO() 
    {
        Aircraft a1 = new Aircraft();
        Aircraft a2 = new Aircraft();
        
        a1.setIdentifier("OH-ABC");
        a2.setIdentifier("OH-ABC");
        
        a1.setType("DV20");
        a2.setType("DV20");
        
        a1.setId(1);
        a2.setId(1);
        
        assertThat(a1, equalTo(a2));
        assertThat(a1.hashCode(), is(a2.hashCode()));
    }
}
