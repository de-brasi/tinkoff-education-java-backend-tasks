package edu.hw1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


public class Task6Test {
    private final static int MAX_POSSIBLE_RESULT = 7;

    @Test
    @DisplayName("Тест из условия: 3524")
    void test1() {
        final int srcValue = 3524;
        final int correctAnswer = 3;
        final int result = Task6.countK(srcValue);
        assertThat(result).isLessThan(MAX_POSSIBLE_RESULT);
        assertThat(result).isEqualTo(correctAnswer);
    }

    @Test
    @DisplayName("Тест из условия: 6621")
    void test2() {
        final int srcValue = 6621;
        final int correctAnswer = 5;
        final int result = Task6.countK(srcValue);
        assertThat(result).isLessThan(MAX_POSSIBLE_RESULT);
        assertThat(result).isEqualTo(correctAnswer);
    }

    @Test
    @DisplayName("Тест из условия: 6554")
    void test3() {
        final int srcValue = 6554;
        final int correctAnswer = 4;
        final int result = Task6.countK(srcValue);
        assertThat(result).isLessThan(MAX_POSSIBLE_RESULT);
        assertThat(result).isEqualTo(correctAnswer);
    }

    @Test
    @DisplayName("Тест из условия: 1234")
    void test4() {
        final int srcValue = 1234;
        final int correctAnswer = 3;
        final int result = Task6.countK(srcValue);
        assertThat(result).isLessThan(MAX_POSSIBLE_RESULT);
        assertThat(result).isEqualTo(correctAnswer);
    }

    @Test
    @DisplayName("Тест: 6174")
    void test5() {
        final int srcValue = 6174;
        final int correctAnswer = 0;
        final int result = Task6.countK(srcValue);
        assertThat(result).isLessThan(MAX_POSSIBLE_RESULT);
        assertThat(result).isEqualTo(correctAnswer);
    }
}
