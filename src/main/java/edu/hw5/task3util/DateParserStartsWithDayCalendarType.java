package edu.hw5.task3util;

import java.time.LocalDate;
import java.util.Optional;
import java.util.regex.Pattern;

public class DateParserStartsWithDayCalendarType extends DateParser{
    public DateParserStartsWithDayCalendarType() {
        pattern = Pattern.compile("^(\\d+)/(\\d+)/(\\d+)$");
    }

    @Override
    public Optional<LocalDate> parse(String date) {
        var matcher = pattern.matcher(date);

        if (!matcher.find()) {
            return Optional.empty();
        }

        int day = Integer.parseInt(matcher.group(1));
        int month = Integer.parseInt(matcher.group(2));
        int year = Integer.parseInt(matcher.group(3));

        return Optional.of(
            LocalDate.of(
                (year < 100) ? 2000 + year : year,
                month,
                day
            )
        );
    }
}
