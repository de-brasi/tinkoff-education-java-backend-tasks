package edu.hw5;

import java.time.DayOfWeek;
import java.time.Month;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

public class Task2 {
    private Task2() {
        // not allowed
    }

    public static List<LocalDate> getAllFridaysThe13Th (int year) {
        ArrayList<LocalDate> result = new ArrayList<>();

        LocalDate curDate = getNearestFridaysThe13Th(LocalDate.of(year, 1, 1));
        while (curDate.getYear() == year) {
            result.add(curDate);
            curDate = getNearestFridaysThe13Th(curDate);
        }

        return result;
    }

    public static LocalDate getNearestFridaysThe13Th (LocalDate curDate) {
        TemporalAdjuster nextFriday15Th = date -> {
            Temporal res = date;
            int dayOfMonth = res.get(ChronoField.DAY_OF_MONTH);
            if (dayOfMonth > 13) {
                res = res.minus(dayOfMonth - 13, ChronoUnit.DAYS);
                res = res.plus(1, ChronoUnit.MONTHS);
            } else if (dayOfMonth < 13) {
                res = res.plus(13 - dayOfMonth, ChronoUnit.DAYS);
            } else {
                // day equal 13
                res = res.plus(1, ChronoUnit.MONTHS);
            }

            while (res.get(ChronoField.DAY_OF_WEEK) != DayOfWeek.FRIDAY.getValue()) {
                res = res.plus(1, ChronoUnit.MONTHS);
            }

            return res;
        };

        return curDate.with(nextFriday15Th);
    }
}
