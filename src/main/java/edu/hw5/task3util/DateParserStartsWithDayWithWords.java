package edu.hw5.task3util;

import java.time.LocalDate;
import java.util.Optional;
import java.util.regex.Pattern;

public class DateParserStartsWithDayWithWords extends DateParser {
    public DateParserStartsWithDayWithWords() {
        pattern = Pattern.compile("^(\\d+) days* ago$");
    }

    @Override
    public Optional<LocalDate> parse(String date) {
        var matcher = pattern.matcher(date);

        if (!matcher.find()) {
            return Optional.empty();
        }

        int daysCount = Integer.parseInt(matcher.group(1));
        return Optional.of(LocalDate.now().minusDays(daysCount));
    }
}
