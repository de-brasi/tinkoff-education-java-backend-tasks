package edu.hw5;

import java.util.regex.Pattern;

public class Task4 {
    private Task4() {
        // not allowed
    }

    public static boolean validatePassword(String password) {
        // Need presence: ~ ! @ # $ % ^ & * |
        var matcher = PASSWORD_REGEX.matcher(password);
        return matcher.find();
    }

    @SuppressWarnings("MultipleStringLiterals")
    private static final Pattern PASSWORD_REGEX = Pattern.compile(
            "[^~!@#$%^&*|]*"        // everything except special symbols
            + "[~!@#$%^&*|]+"       // special symbol
            + "[^~!@#$%^&*|]*"      // everything except special symbols
    );
}
