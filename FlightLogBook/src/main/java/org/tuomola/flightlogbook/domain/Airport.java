package org.tuomola.flightlogbook.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 *
 * @author ptuomola
 */

@Entity
public class Airport {
    
    @Id @GeneratedValue
    private int id;

    @Column(unique=true, nullable=false)
    private String code;
    
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code.toUpperCase();
    }

    @Override
    public String toString() {
        return "Airport{" + "code=" + code + ", name=" + name + '}';
    }
}
