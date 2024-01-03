package edu.hw5;

import java.util.regex.Pattern;

public class Task7 {
    private Task7() {
        // not allowed
    }

    public static boolean validateSubtask1(String toValidate) {
        // содержит не менее 3 символов, причем третий символ равен 0
        Pattern pattern = Pattern.compile(
            String.format("^%s%s0%s*$", ALPHABET_CHAR, ALPHABET_CHAR, ALPHABET_CHAR)
        );
        return pattern.matcher(toValidate).find();
    }

    public static boolean validateSubtask2(String toValidate) {
        //  начинается и заканчивается одним и тем же символом
        Pattern pattern = Pattern.compile(
            String.format("^(%s)%s*(\\1()?)*$", ALPHABET_CHAR, ALPHABET_CHAR)
        );
        return pattern.matcher(toValidate).find();
    }

    public static boolean validateSubtask3(String toValidate) {
        //  длина не менее 1 и не более 3
        Pattern pattern = Pattern.compile(
            String.format("^%s{1,3}$", ALPHABET_CHAR)
        );
        return pattern.matcher(toValidate).find();
    }

    private static final String ALPHABET_CHAR = "[0|1]";
}
