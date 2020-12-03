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
public class Airport {
    
    @Id @GeneratedValue
    private int id;

    @Column(unique = true, nullable = false)
    private String code;
    
    private String name;

    public void setCode(String code) {
        this.code = code.toUpperCase();
    }
}
