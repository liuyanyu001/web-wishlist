package com.wishlist.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * Created by root on 15.02.2016.
 */
public class DateUtil {

    public static long dateTimeToMillis(LocalDateTime date){
        return date.toEpochSecond(ZoneOffset.UTC);
    }

    public static LocalDateTime fromMillis(long millis){
        Instant instant = Instant.ofEpochMilli(millis);
        return LocalDateTime.ofInstant(instant, ZoneOffset.systemDefault());
    }

}
