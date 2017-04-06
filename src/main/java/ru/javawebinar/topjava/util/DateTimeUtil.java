package ru.javawebinar.topjava.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * GKislin
 * 07.01.2015.
 */
public class DateTimeUtil {
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static boolean isBetween(LocalTime lt, LocalTime startTime, LocalTime endTime) {
        return lt.compareTo(startTime) >= 0 && lt.compareTo(endTime) <= 0;
    }

    public static <T extends Comparable<? super T>> boolean isBetween(T lt, T startTime, T endTime) {
        return lt.compareTo(startTime) >= 0 && lt.compareTo(endTime) <= 0;
    }

    public static LocalDate parseLocalDate (String dateString, LocalDate ifNullDate)
    {
        LocalDate localDate;
        if (dateString != null && !(dateString.equals(""))) {
            localDate = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }
        else
        {
            localDate = ifNullDate;
        }
        return localDate;
    }

    public static LocalTime parseLocalTime (String timeString, LocalTime ifNullTime)
    {
        LocalTime localTime;
        if (timeString != null && !(timeString.equals(""))) {
            localTime = LocalTime.parse(timeString, DateTimeFormatter.ofPattern("HH:mm"));
        }
        else
        {
            localTime = ifNullTime;
        }
        return localTime;
    }

    public static String toString(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DATE_TIME_FORMATTER);
    }
}
