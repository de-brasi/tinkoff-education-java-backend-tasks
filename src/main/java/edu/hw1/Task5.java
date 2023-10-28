package edu.hw1;

public class Task5 {
    public static boolean isPalindromeDescendant(int toCheck) {
        while (toCheck >= 10 && makeDescendant(toCheck) >= 10 && !isPalindrome(toCheck)) {
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
        assert source >= 10;

        int result = 0;
        final var sourceContent = Integer.toString(source).toCharArray();

        int curIndex = 0;
        int tmpSum;
        // todo: проверить для двузначных чисел как работает
        for (int i = 0; i < sourceContent.length / 2; i++) {
            // todo: тут кринж из за того что в начале 0
            tmpSum = ((int)sourceContent[curIndex] - (int)'0');
            if (curIndex + 1 < sourceContent.length) {
                tmpSum += ((int)sourceContent[curIndex + 1] - (int)'0');
            }

            if (tmpSum < 10) {
                result *= 10;
            } else {
                result *= 100;
            }
            result += tmpSum;
            curIndex += 2;
        }

        if (curIndex < sourceContent.length) {
            assert (curIndex + 1 == sourceContent.length);
            result *= 10;
            result += (int)sourceContent[curIndex] - (int)'0';
        }

        return result;
    }
}
