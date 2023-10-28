package edu.hw1;

public class Task1 {
    private Task1() {
        // not allowed
    }

    private final static int SECONDS_PER_MINUTE = 60;
    private final static int MIN_NUMBER_CHAR_CODE = 48;
    private final static int MAX_NUMBER_CHAR_CODE = 57;


    public static int minutesToSeconds(String time) {
        if (!timeIsValid(time)) {
            return -1;
        }

        var timeInfo = time.split(":");
        final int minutesCount = Integer.parseInt(timeInfo[0]);
        final int secondsCount = Integer.parseInt(timeInfo[1]);

        return minutesCount * SECONDS_PER_MINUTE + secondsCount;
    }

    private static boolean timeIsValid(String toCheck) {
        boolean checkingResult = (!toCheck.isEmpty());
        for (char symbol: toCheck.toCharArray()) {
            // Check string's content
            if (!(
                symbol == ':'
                    || (MIN_NUMBER_CHAR_CODE <= (int) symbol && (int) symbol <= MAX_NUMBER_CHAR_CODE))
            ) {
                checkingResult = false;
                break;
            }
        }

        if (checkingResult) {
            final int secondsCount = Integer.parseInt(toCheck.split(":")[1]);
            checkingResult = (0 <= secondsCount && secondsCount < SECONDS_PER_MINUTE);
        }

        return checkingResult;
    }
}
