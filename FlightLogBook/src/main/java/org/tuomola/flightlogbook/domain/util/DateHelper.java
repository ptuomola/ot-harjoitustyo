package org.tuomola.flightlogbook.domain.util;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author ptuomola
 */
public class DateHelper {
    
    private static DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    public static String toTimeString(Instant instant) {
        if (instant == null) {
            return "";
        }
        
        return timeFormatter.format(instant.atOffset(ZoneOffset.UTC));
    }
}
