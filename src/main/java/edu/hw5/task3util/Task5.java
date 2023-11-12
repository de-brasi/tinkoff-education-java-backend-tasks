package edu.hw5.task3util;

import java.util.regex.Pattern;

public class Task5 {
    private Task5() {
        // not allowed
    }

    public static boolean validateRussianCarNumber(String numberToValidate) {
        // А, В, Е, К, М, Н, О, Р, С, Т, У, Х.
        return PATTERN.matcher(numberToValidate).find();
    }

    private static final char[] ALLOWED_SYMBOLS = {'А', 'В', 'Е', 'К', 'М', 'Н', 'О', 'Р', 'С', 'Т', 'У', 'Х'};
    private static final String ALLOWED_SYMBOLS_PATTERN_PART = '[' + new String(ALLOWED_SYMBOLS) + ']';
    private static final Pattern PATTERN = Pattern.compile(
        "^" + ALLOWED_SYMBOLS_PATTERN_PART + "{1}\\d{3}" + ALLOWED_SYMBOLS_PATTERN_PART + "{2}" + "\\d{3}$"
    );
}
