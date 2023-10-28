package edu.hw1;

public class Task2 {
    private Task2() {
        // not allowed
    }

    private final static int RADIX = 10;

    public static int getCountOfCharacters(Integer searched) {
        Integer searchedNumber = searched;
        if (searchedNumber.equals(0)) {
            return 1;
        }

        if (searchedNumber < 0) {
            searchedNumber *= -1;
        }

        int charCount = 0;
        while (searchedNumber != 0) {
            ++charCount;
            searchedNumber /= RADIX;
        }

        return charCount;
    }
}
