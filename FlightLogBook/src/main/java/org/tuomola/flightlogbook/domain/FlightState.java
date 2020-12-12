package org.tuomola.flightlogbook.domain;

/**
 * Enumeration for the different states that a Flight can be in.
 * @author ptuomola
 */
public enum FlightState {
    INITIAL, PRE_TAXI, FLIGHT, POST_TAXI, ENDED;
}
