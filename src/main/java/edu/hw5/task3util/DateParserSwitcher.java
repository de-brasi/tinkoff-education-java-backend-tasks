package edu.hw5.task3util;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;


public class DateParserSwitcher extends DateParser{
    public DateParserSwitcher() {
        handlersChain = new ArrayList<>(
            Arrays.asList(
                new DateParserStartsWithNumber(),
                new DateParserStartsWithWord()
            )
        );
    }
}
