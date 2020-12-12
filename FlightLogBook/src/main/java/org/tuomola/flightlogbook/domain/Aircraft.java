package org.tuomola.flightlogbook.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Data;

/**
 * Domain object representing an Aircraft.
 * @author ptuomola
 */

@Data
@Entity
public class Aircraft {
    
    @Id @GeneratedValue
    private int id;
    
    // Callsign of the aircraft (i.e. OH-KAW)
    @Column(unique = true, nullable = false)
    private String identifier;
    
    // Type of the aircraft based on ICAO DOC8643 Aircraft Type Designators
    // @see https://www.icao.int/publications/DOC8643/Pages/default.aspx
    private String type;

    public void setIdentifier(String identifier) {
        this.identifier = identifier.toUpperCase();
    }
}
