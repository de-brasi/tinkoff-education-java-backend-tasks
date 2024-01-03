package edu.hw5.task3util;

import java.util.ArrayList;
import java.util.Arrays;

public class DateParserStartsWithDay extends DateParser {
    public DateParserStartsWithDay() {
        handlersChain = new ArrayList<>(
            Arrays.asList(
                new DateParserStartsWithDayCalendarType(),
                new DateParserStartsWithDayWithWords()
            )
        );
    }
}
