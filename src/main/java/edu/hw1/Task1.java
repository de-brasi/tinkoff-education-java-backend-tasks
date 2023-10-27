package edu.hw1;

public class Task1 {
    private final static int SECONDS_PER_MINUTE = 60;

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
        // проверить что только цифры и ':', секунд меньше 60
        boolean checkingResult = (!toCheck.isEmpty());
        for (char symbol: toCheck.toCharArray()) {
            // Check string's content
            if (!(
                symbol == ':' ||
                    (48 <= (int)symbol && (int)symbol <= 57))
            ) {
                checkingResult = false;
                break;
            }
        }

        if (checkingResult) {
            final int secondsCount = Integer.parseInt(toCheck.split(":")[1]);
            checkingResult = (0 <= secondsCount && secondsCount < 60);
        }

        return checkingResult;
    }
}
