package edu.hw5;

import java.util.regex.Pattern;

public class Task6 {
    private Task6() {
        // not allowed
    }

    public static boolean isSubstring(String source, String potentialSubstring) {
        Pattern pattern = Pattern.compile(
            "(" + EVERYTHING + ")(" + escapeSpecialCharacters(potentialSubstring) + ")(" + EVERYTHING + ")"
        );
        return pattern.matcher(source).find();
    }

    private static String escapeSpecialCharacters(String toEscape) {
        StringBuilder escapedString = new StringBuilder();

        for (char curChar : toEscape.toCharArray()) {
            if (SPECIAL_CHARACTERS.indexOf(curChar) != -1) {
                escapedString.append('\\');
            }
            escapedString.append(curChar);
        }

        return escapedString.toString();
    }

    private static final String EVERYTHING = ".*";
    private static final String SPECIAL_CHARACTERS = ".+*?^$()[]{}|\\";
}
