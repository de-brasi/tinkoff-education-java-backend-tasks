package edu.hw1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Task7Test {
    @Test
    @DisplayName("Тест из условия: rotateRight(8, 1) -> 4)")
    void test1() {
        final int srcValue = 8;
        final int shiftCount = 1;
        final int correctAnswer = 4;
        final int result = Task7.rotateRight(srcValue, shiftCount);
        assertThat(result).isEqualTo(correctAnswer);
    }

    @Test
    @DisplayName("Тест из условия: rotateLeft(16, 1) -> 1)")
    void test2() {
        final int srcValue = 16;
        final int shiftCount = 1;
        final int correctAnswer = 1;
        final int result = Task7.rotateLeft(srcValue, shiftCount);
        assertThat(result).isEqualTo(correctAnswer);
    }

    @Test
    @DisplayName("Тест из условия: rotateLeft(17, 2) -> 6)")
    void test3() {
        final int srcValue = 17;
        final int shiftCount = 2;
        final int correctAnswer = 6;
        final int result = Task7.rotateLeft(srcValue, shiftCount);
        assertThat(result).isEqualTo(correctAnswer);
    }

    @Test
    @DisplayName("Тест: rotateLeft(127, 1) -> 127)")
    void test4() {
        final int srcValue = 127;
        final int shiftCount = 1;
        final int correctAnswer = 127;
        final int result = Task7.rotateLeft(srcValue, shiftCount);
        assertThat(result).isEqualTo(correctAnswer);
    }

    @Test
    @DisplayName("Тест: rotateLeft(127, 100) -> 127)")
    void test5() {
        final int srcValue = 127;
        final int shiftCount = 100;
        final int correctAnswer = 127;
        final int result = Task7.rotateLeft(srcValue, shiftCount);
        assertThat(result).isEqualTo(correctAnswer);
    }

    @Test
    @DisplayName("Тест: rotateRight(127, 1) -> 127)")
    void test6() {
        final int srcValue = 127;
        final int shiftCount = 1;
        final int correctAnswer = 127;
        final int result = Task7.rotateRight(srcValue, shiftCount);
        assertThat(result).isEqualTo(correctAnswer);
    }

    @Test
    @DisplayName("Тест: rotateRight(127, 100) -> 127)")
    void test7() {
        final int srcValue = 127;
        final int shiftCount = 100;
        final int correctAnswer = 127;
        final int result = Task7.rotateRight(srcValue, shiftCount);
        assertThat(result).isEqualTo(correctAnswer);
    }
}
