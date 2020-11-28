package org.tuomola.flightlogbook.ui.util;

import java.time.Duration;
import org.apache.commons.lang3.time.DurationFormatUtils;

/**
 *
 * @author ptuomola
 */
public class FormatHelper {
    public static String formatDuration(Duration duration)
    {
        return DurationFormatUtils.formatDuration(duration.toMillis(), "H 'h' mm 'm'", true);
    }
}
