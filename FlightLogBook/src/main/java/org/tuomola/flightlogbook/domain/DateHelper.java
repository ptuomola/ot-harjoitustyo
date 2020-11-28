package org.tuomola.flightlogbook.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Date;

/**
 *
 * @author ptuomola
 */
class DateHelper {

    public static Date toUTCDate(LocalDate localDate) {
        return toUTCDateTime(localDate.atStartOfDay());
    }

    public static Date toUTCDateTime(LocalDateTime localDateTime) {
        final ZonedDateTime zonedtime = localDateTime.atZone(ZoneId.systemDefault());
        final ZonedDateTime utcTime = zonedtime.withZoneSameInstant(ZoneOffset.UTC);
        return Date.from(utcTime.toInstant());
    }
}
