package edu.hw5.task3util;

import java.time.LocalDate;
import java.util.Optional;

public class DateParserStartsWithWord extends DateParser{
    @Override
    public Optional<LocalDate> parse(String date) {
        return switch (date) {
            case "today" -> Optional.of(LocalDate.now());
            case "tomorrow" -> Optional.of(LocalDate.now().plusDays(1));
            case "yesterday" -> Optional.of(LocalDate.now().minusDays(1));
            default -> Optional.empty();
        };
    }
}
