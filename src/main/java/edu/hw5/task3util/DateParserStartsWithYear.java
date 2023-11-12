package edu.hw5.task3util;

import java.time.LocalDate;
import java.util.Optional;
import java.util.regex.Pattern;

public class DateParserStartsWithYear extends DateParser {
    public DateParserStartsWithYear() {
        pattern = Pattern.compile("^(\\d{4})-(\\d{2})-(\\d+)$");
    }

    @SuppressWarnings("MagicNumber")
    @Override
    public Optional<LocalDate> parse(String date) {
        var matcher = pattern.matcher(date);

        if (!matcher.find()) {
            return Optional.empty();
        }

        int year = Integer.parseInt(matcher.group(1));
        int month = Integer.parseInt(matcher.group(2));
        int day = Integer.parseInt(matcher.group(3));

        return Optional.of(LocalDate.of(year, month, day));
    }
}
