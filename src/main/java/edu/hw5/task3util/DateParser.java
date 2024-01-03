package edu.hw5.task3util;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.regex.Pattern;

public class DateParser {
    protected ArrayList<DateParser> handlersChain = null;
    protected Pattern pattern = null;

    public Optional<LocalDate> parse(String date) {
        Optional<LocalDate> res = Optional.empty();

        for (var parser : handlersChain) {
            res = parser.parse(date);
            if (res.isPresent()) {
                break;
            }
        }

        return res;
    }
}
