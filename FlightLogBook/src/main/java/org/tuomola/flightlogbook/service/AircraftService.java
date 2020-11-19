package org.tuomola.flightlogbook.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tuomola.flightlogbook.dao.AircraftRepository;
import org.tuomola.flightlogbook.domain.Aircraft;

/**
 *
 * @author ptuomola
 */
@Service
public class AircraftService {

    @Autowired
    private final AircraftRepository ar;

    public AircraftService(AircraftRepository ar) {
        this.ar = ar;
    }

    public Aircraft saveAircraft(Aircraft ac){
        return ar.save(ac);
    }
}
