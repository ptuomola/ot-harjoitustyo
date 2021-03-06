package org.tuomola.flightlogbook.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Domain object representing an Airport.
 * @author ptuomola
 */

@Data
@Entity
public class Airport {
    
    @Id @GeneratedValue
    @EqualsAndHashCode.Exclude private int id;

    @Column(unique = true, nullable = false)
    private String code;
    
    private String name;

    public void setCode(String code) {
        this.code = code.toUpperCase();
    }
}
