package org.tuomola.flightlogbook.domain;

/**
 *
 * @author ptuomola
 */
public class Aircraft {
    private String identifier;
    private String type;

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
