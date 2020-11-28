package org.tuomola.flightlogbook.domain;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 *
 * @author ptuomola
 */
public class DateHelper {
    
    private static DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    public static String toTimeString(Instant instant)
    {
        if(instant == null) return "";
        
        return timeFormatter.format(instant.atOffset(ZoneOffset.UTC));
    }
}
