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
public class Aircraft {
    
    @Id @GeneratedValue
    private int id;
    
    @Column(unique=true, nullable=false)
    private String identifier;
    
    private String type;

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier.toUpperCase();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Aircraft{" + "identifier=" + identifier + ", type=" + type + '}';
    }
}
