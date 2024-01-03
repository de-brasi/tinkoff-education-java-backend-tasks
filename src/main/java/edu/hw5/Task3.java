package edu.hw5;

import edu.hw5.task3util.DateParserSwitcher;
import java.time.LocalDate;
import java.util.Optional;

public class Task3 {
    private Task3() {
        // not allowed
    }

    public static Optional<LocalDate> parseDate(String string) {
        var parser = new DateParserSwitcher();
        return parser.parse(string);
    }
}
