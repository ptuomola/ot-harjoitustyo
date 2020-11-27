package org.tuomola.flightlogbook.service;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.tuomola.flightlogbook.domain.Pilot;

/**
 *
 * @author ptuomola
 */

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class LoggedInUserService {
    private Pilot loggedInPilot;

    public Pilot getLoggedInPilot() {
        return loggedInPilot;
    }

    public void setLoggedInPilot(Pilot loggedInPilot) {
        this.loggedInPilot = loggedInPilot;
    }
}
