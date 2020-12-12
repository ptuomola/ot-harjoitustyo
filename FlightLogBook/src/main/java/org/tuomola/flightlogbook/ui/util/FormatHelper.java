package org.tuomola.flightlogbook.ui.util;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import org.apache.commons.lang3.time.DurationFormatUtils;

/**
 * Helper class for formatting date, duration and time values. 
 * @author ptuomola
 */
public class FormatHelper {
    private static final SimpleDateFormat FORMATTER = new SimpleDateFormat("dd.MM.yyyy");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss").withZone(ZoneId.of("Z"));

    /**
     * Format a duration to a string HH:mm:ss.
     * @param duration duration to be formatted
     * @return Duration as a string
     */
    public static String formatDuration(Duration duration) {
        if (duration == null) {
            return "";
        }
        
        return DurationFormatUtils.formatDuration(duration.toMillis(), "HH:mm:ss", true);
    }
    
    /**
     * Format a date to a string in format dd.MM.yyyy.
     * @param date date to be formatted
     * @return Date formatted as a string
     */
    public static String formatDate(Date date) {
        if (date == null) {
            return "";
        }
        
        return FORMATTER.format(date);
    }

    /**
     * Format a time to a string in format HH:mm:ss.
     * @param instant Time instant to be formatted
     * @return Instant formatted as a string
     */
    public static String formatTime(Instant instant) {
        if (instant == null) {
            return "";
        }
        
        return TIME_FORMATTER.format(instant);
    }
}
