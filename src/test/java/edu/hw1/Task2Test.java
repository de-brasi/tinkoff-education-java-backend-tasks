package edu.hw1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Task2Test {
    @Test
    @DisplayName("1 цифра")
    void numbersWithOneCharacter() {
        final int[] testedNumbers = new int[] {-1, 0, 1, 5, 9};
        final int correctAnswer = 1;

        for (var testedValue: testedNumbers) {
            assertThat(Task2.getCountOfCharacters(testedValue)).isEqualTo(correctAnswer);
        }
    }

    @Test
    @DisplayName("2 цифры")
    void numbersWithTwoCharacters() {
        final int[] testedNumbers = new int[] {-10, 11, 15, 99};
        final int correctAnswer = 2;

        for (var testedValue: testedNumbers) {
            assertThat(Task2.getCountOfCharacters(testedValue)).isEqualTo(correctAnswer);
        }
    }

    @Test
    @DisplayName("Тест из условия; 3 цифры")
    void numberFromTaskThreeCharacters() {
        final int testedNumber = 544;
        final int correctAnswer = 3;

        assertThat(Task2.getCountOfCharacters(testedNumber)).isEqualTo(correctAnswer);
    }

    @Test
    @DisplayName("Тест из условия; 4 цифры")
    void numberFromTaskFourCharacters() {
        final int testedNumber = 4666;
        final int correctAnswer = 4;

        assertThat(Task2.getCountOfCharacters(testedNumber)).isEqualTo(correctAnswer);
    }
}
