// Task 1

package edu.hw5;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class TimeSequencesAnalyserTest {
    @Test
    @DisplayName("Тест из условия: 3ч 40м")
    public void test1() {
        List<String> source = Stream
            .of("2022-03-12, 20:20 - 2022-03-12, 23:50",
                "2022-04-01, 21:30 - 2022-04-02, 01:20")
            .collect(Collectors.toList());
        final Duration expectedResult = Duration.ofHours(3).plusMinutes(40);

        final Duration actualResult = TimeSequencesAnalyser.getAverageDuration(source);

        assertThat(expectedResult).isEqualTo(actualResult);
    }

    @Test
    @DisplayName("Тест: пустая последовательность")
    public void test2() {
        List<String> source = Stream.<String>empty().collect(Collectors.toList());
        final Duration expectedResult = Duration.ZERO;

        final Duration actualResult = TimeSequencesAnalyser.getAverageDuration(source);

        assertThat(expectedResult).isEqualTo(actualResult);
    }

    @Test
    @DisplayName("Тест: последовательность из одного интервала")
    public void test3() {
        List<String> source = Stream
            .of("2022-03-12, 20:20 - 2022-03-12, 23:50")
            .collect(Collectors.toList());
        final Duration expectedResult = Duration.ofHours(3).plusMinutes(30);

        final Duration actualResult = TimeSequencesAnalyser.getAverageDuration(source);

        assertThat(expectedResult).isEqualTo(actualResult);
    }

    @Test
    @DisplayName("Тест: большой набор последовательностей из коротких интервалов")
    public void test4() {
        List<String> source = Stream
            .of(
                "2022-01-01, 20:20 - 2022-01-01, 20:21",
                "2022-01-01, 20:20 - 2022-01-01, 20:23",
                "2022-01-01, 20:21 - 2022-01-01, 20:22",
                "2022-01-01, 20:21 - 2022-01-01, 20:24",
                "2022-01-01, 20:22 - 2022-01-01, 20:23",
                "2022-01-01, 20:22 - 2022-01-01, 20:25",
                "2022-01-01, 20:20 - 2022-01-01, 20:21",
                "2022-01-01, 20:20 - 2022-01-01, 20:23",
                "2022-01-01, 20:21 - 2022-01-01, 20:22",
                "2022-01-01, 20:21 - 2022-01-01, 20:24",
                "2022-01-01, 20:22 - 2022-01-01, 20:23",
                "2022-01-01, 20:22 - 2022-01-01, 20:25",
                "2022-01-01, 20:20 - 2022-01-01, 20:21",
                "2022-01-01, 20:20 - 2022-01-01, 20:23",
                "2022-01-01, 20:21 - 2022-01-01, 20:22",
                "2022-01-01, 20:21 - 2022-01-01, 20:24",
                "2022-01-01, 20:22 - 2022-01-01, 20:23",
                "2022-01-01, 20:22 - 2022-01-01, 20:25"
            )
            .collect(Collectors.toList());
        final Duration expectedResult = Duration.ofMinutes(2);

        final Duration actualResult = TimeSequencesAnalyser.getAverageDuration(source);

        assertThat(expectedResult).isEqualTo(actualResult);
    }
}
