package com.ballistic.util;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;


public class DateUtils {

    private DateUtils() {
        throw new AssertionError("No instances for you!");
    }

    public static Date toDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public static LocalDate toLocalDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

}
