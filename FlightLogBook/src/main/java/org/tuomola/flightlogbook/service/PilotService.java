package org.tuomola.flightlogbook.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tuomola.flightlogbook.dao.PilotRepository;
import org.tuomola.flightlogbook.domain.Pilot;

/**
 * Business logic related to handling of Pilots.
 * @author ptuomola
 */
@Service
public class PilotService {

    @Autowired
    private final PilotRepository pr;
    
    @Autowired
    private final PasswordService ps;

    /**
     * Constructor.
     * @param pr PilotRepository to be used
     * @param ps PasswordService to be used
     */
    public PilotService(PilotRepository pr, PasswordService ps) {
        this.pr = pr;
        this.ps = ps; 
    }

    /**
     * Check if the provided user name is already in use.
     * @param username username to be checked
     * @return true if the username is already in use, false otherwise
     */
    public boolean isUsernameInUse(String username) {
        if (pr.findByUserNameIgnoreCase(username) == null) {
            return false;
        } else {
            return true;
        }
    }
    
    /**
     * Save the pilot into the repository.
     * @param p Pilot to be saved
     * @return Saved pilot
     */
    public Pilot savePilot(Pilot p) {
        return pr.save(p);
    }    

    /**
     * Check if a username and password combination is valid.
     * @param username username of the user to be logged in
     * @param password cleartext password for the user to be logged in
     * @return Pilot object for the username if username and password match, 
     * null otherwise
     */
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
