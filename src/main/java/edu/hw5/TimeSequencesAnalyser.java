// Task 1

package edu.hw5;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.regex.Pattern;

public class TimeSequencesAnalyser {
    private TimeSequencesAnalyser() {
        // not allowed
    }

    public static Duration getAverageDuration(List<String> intervals) {
        var summaryDuration = Duration.ZERO;

        for (String curInterval : intervals) {
            summaryDuration = summaryDuration.plus(getDurationFromString(curInterval));
        }

        return !intervals.isEmpty() ?
            summaryDuration.dividedBy(intervals.size()) :
            summaryDuration;
    }

    private static Duration getDurationFromString(String source) {
        var timesAsString = source.split(" - ");
        assert timesAsString.length == 2;

        return Duration.between(
            getDateTimeFromString(timesAsString[BEGIN_DATETIME_INDEX]),
            getDateTimeFromString(timesAsString[END_DATETIME_INDEX])
        );
    }

    private static LocalDateTime getDateTimeFromString(String source) {
        // yyyy-mm-dd, hh:mm
        var matcher = DATE_TIME_PATTERN.matcher(source);
        if (!matcher.find()) {
            throw new RuntimeException("Incorrect string with datetime for parse");
        }

        return LocalDateTime.of(
            Integer.parseInt(matcher.group(DATE_TIME_MATCHED_GROUP_YEAR_INDEX)),
            Integer.parseInt(matcher.group(DATE_TIME_MATCHED_GROUP_MONTH_INDEX)),
            Integer.parseInt(matcher.group(DATE_TIME_MATCHED_GROUP_DAY_INDEX)),
            Integer.parseInt(matcher.group(DATE_TIME_MATCHED_GROUP_HOURS_INDEX)),
            Integer.parseInt(matcher.group(DATE_TIME_MATCHED_GROUP_MINUTES_INDEX))
        );
    }

    private final static int BEGIN_DATETIME_INDEX = 0;
    private final static int END_DATETIME_INDEX = 1;
    private final static Pattern DATE_TIME_PATTERN =
        Pattern.compile("^(\\d{4})-(\\d{2})-(\\d{2}), (\\d{2}):(\\d{2})$");
    private final static int DATE_TIME_MATCHED_GROUP_YEAR_INDEX = 1;
    private final static int DATE_TIME_MATCHED_GROUP_MONTH_INDEX = 2;
    private final static int DATE_TIME_MATCHED_GROUP_DAY_INDEX = 3;
    private final static int DATE_TIME_MATCHED_GROUP_HOURS_INDEX = 4;
    private final static int DATE_TIME_MATCHED_GROUP_MINUTES_INDEX = 5;
}
