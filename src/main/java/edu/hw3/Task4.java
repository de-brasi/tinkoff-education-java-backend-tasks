package edu.hw3;

import org.apache.logging.log4j.core.util.KeyValuePair;
import java.util.ArrayList;

public class Task4 {
    private Task4() {
        // not allowed
    }

    private final static String ONE = "I";
    private final static String FIVE = "V";
    private final static String TEN = "X";
    private final static String FIFTY = "L";
    private final static String HUNDRED = "C";
    private final static String FIVE_HUNDRED = "D";
    private final static String THOUSAND = "M";

    public static String convertToRoman(int number) {
        if (number < 1 || number > 3999) {
            throw new IllegalArgumentException("Число должно быть в диапазоне от 1 до 3999");
        }

        var result = new StringBuilder();

        int thousandsCount = number / 1000;
        int hundredsCount = (number % 1000) / 100;
        int tensCount = (number % 100) / 10;
        int onesCount = number % 10;

        result.append(gotRepresentation(thousandsCount, "", THOUSAND));
        result.append(gotRepresentation(hundredsCount, FIVE_HUNDRED, HUNDRED));
        result.append(gotRepresentation(tensCount, FIFTY, TEN));

        if (onesCount == 4) {
            result.append("IV");
        } else if (onesCount == 9) {
            result.append("IX");
        } else {
            result.append(gotRepresentation(onesCount, FIVE, ONE));
        }

        return result.toString();
    }

    private static StringBuilder gotRepresentation(int count, String biggestRepresentation, String representation) {
        int unitCounts = count;
        var result = new StringBuilder();

        if (unitCounts >= 5) {
            result.append(biggestRepresentation);
            unitCounts -= 5;
        }

        for (int i = 0; i < unitCounts; i++) {
            result.append(representation);
        }

        return result;
    }
}
