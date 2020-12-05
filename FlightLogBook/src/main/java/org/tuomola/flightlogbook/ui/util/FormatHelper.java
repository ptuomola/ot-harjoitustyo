package org.tuomola.flightlogbook.ui.util;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import org.apache.commons.lang3.time.DurationFormatUtils;

/**
 *
 * @author ptuomola
 */
public class FormatHelper {
    private static SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
    private static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss").withZone(ZoneId.of("Z"));

   
    public static String formatDuration(Duration duration) {
        if (duration == null) {
            return "";
        }
        
        return DurationFormatUtils.formatDuration(duration.toMillis(), "HH:mm:ss", true);
    }
    
    public static String formatDate(Date date) {
        if (date == null) {
            return "";
        }
        
        return formatter.format(date);
    }

    public static String formatTime(Instant instant) {
        if (instant == null) {
            return "";
        }
        
        return timeFormatter.format(instant);
    }
}
