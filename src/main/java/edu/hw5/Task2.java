package edu.hw5;

import java.time.DayOfWeek;
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
        // todo: dummy solution; do something better
        // todo задание:
        //  После этого используя TemporalAdjuster, напишите функцию,
        //  которая для заданной даты ищет следующую ближайшую пятницу 13.
        LocalDate result = curDate.plusDays(1);
        while (!(result.getDayOfMonth() == 13 && result.getDayOfWeek() == DayOfWeek.FRIDAY)) {
            result = result.plusDays(1);
        }

        return result;
    }
}
