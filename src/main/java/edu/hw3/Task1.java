package edu.hw3;

public class Task1 {
    private Task1() {
        // not allowed
    }

    private static final int MIN_LOWERCASE_CODE = 97;
    private static final int MAX_LOWERCASE_CODE = 122;

    private static final int MIN_UPPERCASE_CODE = 65;
    private static final int MAX_UPPERCASE_CODE = 90;

    public static String atbash(final String src) {
        var toEncodeContent = src.toCharArray();

        int curLetterCode;
        for (int i = 0; i < toEncodeContent.length; i++) {
            curLetterCode = (int) toEncodeContent[i];

            if (MIN_LOWERCASE_CODE <= curLetterCode && curLetterCode <= MAX_LOWERCASE_CODE) {
                toEncodeContent[i] = encodeLowerCase(curLetterCode);
            } else if (MIN_UPPERCASE_CODE <= curLetterCode && curLetterCode <= MAX_UPPERCASE_CODE) {
                toEncodeContent[i] = encodeUpperCase(curLetterCode);
            }
        }

        return String.copyValueOf(toEncodeContent);
    }

    private static char encodeLowerCase(int toEncode) {
        return (char) (MAX_LOWERCASE_CODE - (toEncode - MIN_LOWERCASE_CODE));
    }

    private static char encodeUpperCase(int toEncode) {
        return (char) (MAX_UPPERCASE_CODE - (toEncode - MIN_UPPERCASE_CODE));
    }
}
