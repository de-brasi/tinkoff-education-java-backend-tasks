package edu.hw1;

import java.util.Arrays;

public class Task3 {
    private Task3() {
        // not allowed
    }

    public static boolean checkNestingPossibility(final int[] encaseArray, final int[] nestingArray) {
        if ((encaseArray == null || encaseArray.length == 0)
            && (nestingArray == null || nestingArray.length == 0)) {
            return true;
        }

        if ((encaseArray != null || encaseArray.length > 0)
            && (nestingArray == null || nestingArray.length == 0)) {
            return true;
        }

        // nestingArray can't be null or empty here
        if (encaseArray == null || encaseArray.length == 0) {
            return false;
        }

        final int minEncaseValue = Arrays.stream(encaseArray).min().getAsInt();
        final int maxEncaseValue = Arrays.stream(encaseArray).max().getAsInt();

        final int minNestingValue = Arrays.stream(nestingArray).min().getAsInt();
        final int maxNestingValue = Arrays.stream(nestingArray).max().getAsInt();

        return (minNestingValue > minEncaseValue) && (maxNestingValue < maxEncaseValue);
    }
}
