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
import org.tuomola.flightlogbook.domain.FlightLog;
import org.tuomola.flightlogbook.domain.Pilot;
import org.tuomola.flightlogbook.service.AirportService;
import org.tuomola.flightlogbook.service.FlightLogService;
import org.tuomola.flightlogbook.service.FlightService;
import org.tuomola.flightlogbook.service.PasswordService;
import org.tuomola.flightlogbook.dto.PilotAirportDTO;
import org.tuomola.flightlogbook.service.PilotService;

/**
 *
 * @author ptuomola
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class PilotTest {

    @Autowired
    private PilotService ps;
    
    @Autowired
    private PasswordService pwds;

    @Test
    public void testUserNameNotInUse()
    {
        assertThat(ps.isUsernameInUse(RandomStringUtils.randomAlphanumeric(11)), is(false));
    }
    
    @Test
    public void testLoginPilot()
    {
        String username = RandomStringUtils.randomAlphanumeric(10);
        String password = RandomStringUtils.randomAlphanumeric(10);
        
        Pilot p = new Pilot();
        p.setUserName(username);
        p.setPassword(pwds.encrypt(password));
        p.setQualifications(null);
        ps.savePilot(p);

        Pilot loggedInP = ps.loginPilot(username, password);
        loggedInP.setQualifications(null);
        assertThat(loggedInP, equalTo(p));
    }
    
    @Test
    public void testPasswordEncrypt()
    {
        String passwordToEncrypt = "Test1234!";
        String encryptedPassword = pwds.encrypt(passwordToEncrypt);
        assertThat(encryptedPassword, equalTo(pwds.encrypt(passwordToEncrypt)));
    }
    
    @Test
    public void testPasswordValidator()
    {
        assertThat(pwds.isValid("test1234"), is(false));
        assertThat(pwds.isValid("Test1234"), is(false));
        assertThat(pwds.isValid("Test1234!"), is(true));
    }
    
    @Test
    public void testCompositeName()
    {
        Pilot p1 = new Pilot();
        p1.setFullName("Test tester");
        p1.setUserName("test1234");
        assertThat(p1.getCompositeName(), equalTo("test1234 (Test tester)"));
    }
    
    @Test
    public void testPilotDAO() 
    {
        Pilot p1 = new Pilot();
        Pilot p2 = new Pilot();
        
        p1.setDateOfBirth(new Date());
        p2.setDateOfBirth(new Date());
        
        p1.setEmail("test@test.org");
        p2.setEmail("test@test.org");
        
        p1.setFullName("Test tester");
        p2.setFullName("Test tester");
        
        p1.setPassword("test1234");
        p2.setPassword("test1234");
        
        p1.setUserName("test1234");
        p2.setUserName("test1234");
        assertThat(p1, equalTo(p2));
        assertThat(p1.hashCode(), is(p2.hashCode()));
    }
}
