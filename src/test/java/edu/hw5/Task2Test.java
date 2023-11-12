package edu.hw5;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Task2Test {
    @Test
    @DisplayName("Тест из условия: все пятницы 13 за 1925 год")
    public void test1() {
        int year = 1925;
        final List<LocalDate> expectedResult = Stream
            .of(
                LocalDate.of(1925, 2, 13),
                LocalDate.of(1925, 3, 13),
                LocalDate.of(1925, 11, 13)
            )
            .collect(Collectors.toList());;

        final List<LocalDate> actualResult = Task2.getAllFridaysThe13Th(year);

        assertThat(actualResult).isEqualTo(expectedResult);
    }

    @Test
    @DisplayName("Тест из условия: все пятницы 13 за 2024 год")
    public void test2() {
        int year = 2024;
        final List<LocalDate> expectedResult = Stream
            .of(
                LocalDate.of(2024, 9, 13),
                LocalDate.of(2024, 12, 13)
            )
            .collect(Collectors.toList());;

        final List<LocalDate> actualResult = Task2.getAllFridaysThe13Th(year);

        assertThat(actualResult).isEqualTo(expectedResult);
    }

    @Test
    @DisplayName("Тест: все пятницы 13 за 2042 год")
    public void test3() {
        int year = 2042;
        final List<LocalDate> expectedResult = Stream
            .of(
                LocalDate.of(2042, 6, 13)
            )
            .collect(Collectors.toList());;

        final List<LocalDate> actualResult = Task2.getAllFridaysThe13Th(year);

        assertThat(actualResult).isEqualTo(expectedResult);
    }
}
