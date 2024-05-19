package iiproject.jobhunting.helpers;

import java.time.LocalDateTime;

public class Utils {
    public static long getTimeStampHelper() {
        return System.currentTimeMillis();
    }
    public static LocalDateTime getLocalDateTimeOfNow() {return LocalDateTime.now();}
}
