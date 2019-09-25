package se.kth.mauritzz.thesis.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class DateUtils {

    public static LocalDateTime getDateTime(long epoch) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(epoch), ZoneId.systemDefault());
    }

}
