package org.tuomola.flightlogbook.dao;

import java.time.Duration;
import java.util.Date;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author ptuomola
 */

@Data
@NoArgsConstructor
public class PilotAircraftDAO {
    private String identifier;
    private String type;
    private Date lastFlight;
    private Duration hoursFlown;
}
