package edu.hw5.task3util;

import java.util.ArrayList;
import java.util.Arrays;

public class DateParserSwitcher extends DateParser {
    public DateParserSwitcher() {
        handlersChain = new ArrayList<>(
            Arrays.asList(
                new DateParserStartsWithNumber(),
                new DateParserStartsWithWord()
            )
        );
    }
}
