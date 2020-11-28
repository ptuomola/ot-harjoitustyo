package org.tuomola.flightlogbook.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tuomola.flightlogbook.dao.PilotRepository;
import org.tuomola.flightlogbook.domain.Pilot;

/**
 *
 * @author ptuomola
 */
@Service
public class PilotService {

    @Autowired
    private final PilotRepository pr;
    
    @Autowired
    private final PasswordService ps;

    public PilotService(PilotRepository pr, PasswordService ps) {
        this.pr = pr;
        this.ps = ps; 
    }

    public boolean isUsernameInUse(String username) {
        if (pr.findByUserNameIgnoreCase(username) == null) {
            return false;
        } else {
            return true;
        }
    }
    
    public Pilot savePilot(Pilot p) {
        return pr.save(p);
    }    

    public Pilot loginPilot(String username, String password) {
        Pilot p = pr.findByUserNameIgnoreCase(username);
        if (p == null) {
            return null;
        }
        
        String encryptedPassword = ps.encrypt(password);
        if (p.getPassword() != null && p.getPassword().equals(encryptedPassword)) {
            return p;
        }
        
        return null;
    }
}
