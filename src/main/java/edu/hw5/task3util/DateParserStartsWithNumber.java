package edu.hw5.task3util;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

public class DateParserStartsWithNumber extends DateParser{
    public DateParserStartsWithNumber() {
        handlersChain = new ArrayList<>(
            Arrays.asList(
                new DateParserStartsWithYear(),
                new DateParserStartsWithDay()
            )
        );
    }
}
