package edu.hw1;

public class Task2 {
    public static int getCountOfCharacters(Integer searchedNumber) {
        if (searchedNumber.equals(0)) {
            return 1;
        }

        if (searchedNumber < 0) {
            searchedNumber *= -1;
        }

        int charCount = 0;
        while (searchedNumber != 0) {
            ++charCount;
            searchedNumber /= 10;
        }

        return charCount;
    }
}
