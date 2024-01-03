package edu.hw5;

import java.util.regex.Pattern;

public class Task8 {
    private Task8() {
        // not allowed
    }

    public static boolean validateSubtask1(String toValidate) {
        // нечетной длины
        Pattern pattern = Pattern.compile(
            String.format("^%s(%s%s)*$", ALPHABET_CHAR, ALPHABET_CHAR, ALPHABET_CHAR)
        );
        return pattern.matcher(toValidate).find();
    }

    public static boolean validateSubtask2(String toValidate) {
        // начинается с 0 и имеет нечетную длину, или начинается с 1 и имеет четную длину
        Pattern startsWithZeroPattern = Pattern.compile(
            String.format("^0(%s%s)*$", ALPHABET_CHAR, ALPHABET_CHAR)
        );
        Pattern startsWithOnePattern = Pattern.compile(
            String.format("^1%s(%s%s)*$", ALPHABET_CHAR, ALPHABET_CHAR, ALPHABET_CHAR)
        );
        return startsWithZeroPattern.matcher(toValidate).find()
            || startsWithOnePattern.matcher(toValidate).find();
    }

    public static boolean validateSubtask3(String toValidate) {
        // количество 0 кратно 3
        Pattern pattern = Pattern.compile("^1*01*01*01*(1*01*01*01*)*$");
        return pattern.matcher(toValidate).find();
    }

    public static boolean validateSubtask4(String toValidate) {
        // любая строка, кроме 11 или 111
        Pattern pattern = Pattern.compile(
            String.format("^(?!11$|111$)%s*$", ALPHABET_CHAR)
        );
        return pattern.matcher(toValidate).find();
    }

    private static final String ALPHABET_CHAR = "[0|1]";
}
