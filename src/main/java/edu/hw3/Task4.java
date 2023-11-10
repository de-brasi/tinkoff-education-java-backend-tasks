package edu.hw3;

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

    private final static Integer MAX_VALUE = 3999;
    private final static Integer MIN_VALUE = 1;

    private final static Integer THOUSAND_VAL = 1000;
    private final static Integer HUNDRED_VAL = 100;
    private final static Integer DECIMAL_VAL = 10;

    private final static Integer INTERMEDIATE_NUMBER = 5;

    private final static Integer UNIQUE_VALUE_FOUR = 4;
    private final static Integer UNIQUE_VALUE_NINE = 9;

    public static String convertToRoman(int src) {
        int number = src;
        if (number < MIN_VALUE || number > MAX_VALUE) {
            throw new IllegalArgumentException("Число должно быть в диапазоне от 1 до 3999");
        }

        var result = new StringBuilder();

        int thousandsCount = number / THOUSAND_VAL;
        number %= THOUSAND_VAL;
        int hundredsCount = number / HUNDRED_VAL;
        number %= HUNDRED_VAL;
        int tensCount = number / DECIMAL_VAL;
        number %= DECIMAL_VAL;
        int onesCount = number;

        result.append(gotRepresentation(thousandsCount, "", THOUSAND));
        result.append(gotRepresentation(hundredsCount, FIVE_HUNDRED, HUNDRED));
        result.append(gotRepresentation(tensCount, FIFTY, TEN));

        if (onesCount == UNIQUE_VALUE_FOUR) {
            result.append("IV");
        } else if (onesCount == UNIQUE_VALUE_NINE) {
            result.append("IX");
        } else {
            result.append(gotRepresentation(onesCount, FIVE, ONE));
        }

        return result.toString();
    }

    private static StringBuilder gotRepresentation(int count, String biggestRepresentation, String representation) {
        int unitCounts = count;
        var result = new StringBuilder();

        if (unitCounts >= INTERMEDIATE_NUMBER) {
            result.append(biggestRepresentation);
            unitCounts -= INTERMEDIATE_NUMBER;
        }

        for (int i = 0; i < unitCounts; i++) {
            result.append(representation);
        }

        return result;
    }
}
