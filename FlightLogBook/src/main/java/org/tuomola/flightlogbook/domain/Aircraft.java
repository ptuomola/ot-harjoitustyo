package org.tuomola.flightlogbook.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Data;

/**
 *
 * @author ptuomola
 */

@Data
@Entity
public class Aircraft {
    
    @Id @GeneratedValue
    private int id;
    
    @Column(unique = true, nullable = false)
    private String identifier;
    
    private String type;

    public void setIdentifier(String identifier) {
        this.identifier = identifier.toUpperCase();
    }
}
