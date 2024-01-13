package com.blubank.doctorappointment.util;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import net.time4j.PlainDate;
import net.time4j.calendar.PersianCalendar;

public class DateUtil {

    public static LocalDate convertFromJalaliToGregorian(Integer year, Integer month, Integer day) {
        PersianCalendar persianCalendar = PersianCalendar.of(year, month, day);
        return LocalDate.parse(persianCalendar.transform(PlainDate.class).toString());
    }

    public static LocalDate convertFromJalaliToGregorian(String date) {
        String[] dateArray = date.split("-");
        PersianCalendar persianCalendar = PersianCalendar.of(Integer.valueOf(dateArray[0]), Integer.valueOf(dateArray[1]), Integer.valueOf(dateArray[2]));
        return LocalDate.parse(persianCalendar.transform(PlainDate.class).toString());
    }

    public static LocalTime convertTime(String time){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        return LocalTime.parse(time, formatter);
    }
}
