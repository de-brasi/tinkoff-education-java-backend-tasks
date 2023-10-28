package edu.hw1;

public class Task4 {
    public static String fixString(String toFix) {
        var toFixChars = toFix.toCharArray();
        char tmp;
        int curIndex = 0;
        for (int i = 0; i < toFixChars.length / 2; i++) {
            tmp = toFixChars[curIndex];
            toFixChars[curIndex] = toFixChars[curIndex + 1];
            toFixChars[curIndex + 1] = tmp;

            curIndex += 2;
        }
        return new String(toFixChars);
    }
}
