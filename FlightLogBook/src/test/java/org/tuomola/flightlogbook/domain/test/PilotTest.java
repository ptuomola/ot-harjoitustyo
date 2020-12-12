package org.tuomola.flightlogbook.domain.test;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.apache.commons.lang3.RandomStringUtils;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.tuomola.flightlogbook.domain.License;
import org.tuomola.flightlogbook.domain.Pilot;
import org.tuomola.flightlogbook.service.PasswordService;
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
    public void testPilotEquals() 
    {
        EqualsVerifier.forClass(Pilot.class).verify();
    }
    
    @Test
    public void testLicenseEquals()
    {
        EqualsVerifier.forClass(License.class).verify();
    }
}
