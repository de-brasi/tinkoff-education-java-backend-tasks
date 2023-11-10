package edu.hw1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Task1Test {
    private final static int SECONDS_PER_MINUTE = 60;

    @Test
    @DisplayName("Корректный случай, маленькие числа")
    void regularCaseSmallValues() {
        // given
        final String timeInString = "12:13";
        final int timeInInt = 12 * SECONDS_PER_MINUTE + 13;

        // when
        final var converterSolution = Task1.minutesToSeconds(timeInString);

        //then
        assertThat(converterSolution).isEqualTo(timeInInt);
    }

    @Test
    @DisplayName("Корректный случай, маленькие числа")
    void regularCaseBigValues() {
        // given
        final String timeInString = "100500:59";
        final int timeInInt = 100500 * SECONDS_PER_MINUTE + 59;

        // when
        final var converterSolution = Task1.minutesToSeconds(timeInString);

        //then
        assertThat(converterSolution).isEqualTo(timeInInt);
    }


    @Test
    @DisplayName("Неверное значение числа секунд")
    void incorrectSecondsValue() {
        // given
        final String timeInString = "12:60";
        final int timeInInt = -1;

        // when
        final var converterSolution = Task1.minutesToSeconds(timeInString);

        //then
        assertThat(converterSolution).isEqualTo(timeInInt);
    }

    @Test
    @DisplayName("Некорректное содержание строки")
    void incorrectContent() {
        // given
        final String timeInString = "12:-60";
        final int timeInInt = -1;

        // when
        final var converterSolution = Task1.minutesToSeconds(timeInString);

        //then
        assertThat(converterSolution).isEqualTo(timeInInt);
    }
}
