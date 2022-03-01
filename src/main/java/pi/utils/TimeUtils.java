package pi.utils;

import static java.util.concurrent.TimeUnit.DAYS;
import static java.util.concurrent.TimeUnit.HOURS;
import static java.util.concurrent.TimeUnit.MINUTES;
import static java.util.concurrent.TimeUnit.SECONDS;

import java.util.Arrays;
import java.util.List;

/**
 * Utilities for dealing with dates and times
 */
public class TimeUtils {

    public static final List<Long> times = Arrays.asList(
        DAYS.toMillis(365),
        DAYS.toMillis(30),
        DAYS.toMillis(7),
        DAYS.toMillis(1),
        HOURS.toMillis(1),
        MINUTES.toMillis(1),
        SECONDS.toMillis(1)
    );

    public static final List<String> timesString = Arrays.asList(
        "year", "month", "week", "day", "hour", "min", "sec"
    );
    
    public static CharSequence getRelativeTime(final long date) {
        return toDuration( Math.abs(System.currentTimeMillis() - date) );
    }

    private static String toDuration(long duration) {
        StringBuilder sb = new StringBuilder();
        for(int i=0;i< times.size(); i++) {
            Long current = times.get(i);
            long temp = duration / current;
            if (temp > 0) {
                sb.append(temp)
                  .append(" ")
                  .append(timesString.get(i))
                  .append(temp > 1 ? "s" : "")
                  .append(" ago");
                break;
            }
        }
        return sb.toString().isEmpty() ? "now" : sb.toString();
    }
}
