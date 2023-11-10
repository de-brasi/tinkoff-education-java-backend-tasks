package edu.hw1;

public class Task5 {
    private Task5() {
        // not allowed
    }

    private final static int MIN_NUM_WITH_DESCENDANT = 10;
    private final static int RADIX = 10;

    public static boolean isPalindromeDescendant(int src) {
        int toCheck = src;

        while (toCheck >= MIN_NUM_WITH_DESCENDANT
            && makeDescendant(toCheck) >= MIN_NUM_WITH_DESCENDANT
            && !isPalindrome(toCheck)) {
            toCheck = makeDescendant(toCheck);
        }

        return isPalindrome(toCheck);
    }

    private static boolean isPalindrome(int toCheck) {
        final var toCheckContent = Integer.toString(toCheck).toCharArray();

        for (int i = 0; i < toCheckContent.length / 2; i++) {
            if (toCheckContent[i] != toCheckContent[toCheckContent.length - 1 - i]) {
                return false;
            }
        }

        return true;
    }

    private static int makeDescendant(int source) {
        assert source >= MIN_NUM_WITH_DESCENDANT;

        int result = 0;
        final var sourceContent = Integer.toString(source).toCharArray();

        int curIndex = 0;
        int tmpSum;
        for (int i = 0; i < sourceContent.length / 2; i++) {
            tmpSum = ((int) sourceContent[curIndex] - (int) '0');
            if (curIndex + 1 < sourceContent.length) {
                tmpSum += ((int) sourceContent[curIndex + 1] - (int) '0');
            }

            if (tmpSum < RADIX) {
                result *= RADIX;
            } else {
                result *= RADIX * RADIX;
            }
            result += tmpSum;
            curIndex += 2;
        }

        if (curIndex < sourceContent.length) {
            assert (curIndex + 1 == sourceContent.length);
            result *= RADIX;
            result += (int) sourceContent[curIndex] - (int) '0';
        }

        return result;
    }
}
