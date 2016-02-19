package com.wishlist.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

public class DateUtil {

    public static long dateTimeToMillis(LocalDateTime date){
        return date.toEpochSecond(ZoneOffset.UTC);
    }

    public static LocalDateTime fromMillis(long millis){
        Instant instant = Instant.ofEpochMilli(millis);
        return LocalDateTime.ofInstant(instant, ZoneOffset.systemDefault());
    }

    public static long getCurrentTimeMinusMinutes(int minutes){
        Instant instant = Instant.ofEpochMilli(System.currentTimeMillis());
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneOffset.systemDefault());
        localDateTime = localDateTime.minusMinutes(minutes);
        ZonedDateTime zdt = localDateTime.atZone(ZoneOffset.systemDefault());
        return zdt.toInstant().toEpochMilli();
    }

}
