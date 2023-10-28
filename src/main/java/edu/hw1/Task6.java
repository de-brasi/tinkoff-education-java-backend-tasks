package edu.hw1;

import java.util.Arrays;

public class Task6 {
    private static final int EXPECTED_RESULT = 6174;

    public static int countK(int toCheck) {
        if (toCheck == EXPECTED_RESULT) {
            return 0;
        }

        final int[] toCheckContent = getNumbers(toCheck);

        int[] toCheckContentAscending = Arrays.copyOf(toCheckContent, toCheckContent.length);
        Arrays.sort(toCheckContentAscending);

        int[] toCheckContentDescending = Arrays.copyOf(toCheckContentAscending, toCheckContentAscending.length);
        int tmp;
        for (int i = 0; i < toCheckContentAscending.length / 2; i++) {
            tmp = toCheckContentDescending[i];
            toCheckContentDescending[i] = toCheckContentDescending[toCheckContentAscending.length - 1 - i];
            toCheckContentDescending[toCheckContentAscending.length - 1 - i] = tmp;
        }

        tmp = getNumberFromNumArray(toCheckContentDescending) - getNumberFromNumArray(toCheckContentAscending);

        return countK(tmp) + 1;
    }

    private static int[] getNumbers(int source) {
        final var sourceAsString = Integer.toString(source).toCharArray();
        int[] result = new int[sourceAsString.length];

        for (int i = 0; i < sourceAsString.length; i++) {
            result[i] = (int)sourceAsString[i] - (int)'0';
        }

        return result;
    }

    private static int getNumberFromNumArray(int[] source) {
        int res = 0;

        for (int curNum : source) {
            res *= 10;
            res += curNum;
        }

        return res;
    }
}
