package edu.hw1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Task3Test {
    @Test
    @DisplayName("Вложение пустого массива в пустой")
    void test1() {
        final int[] encase = {};
        final int[] nested = {};

        assertThat(Task3.checkNestingPossibility(encase, nested)).isEqualTo(true);
    }

    @Test
    @DisplayName("Вложение пустого массива в наполненный")
    void test2() {
        final int[] encase = {1, 2};
        final int[] nested = {};

        assertThat(Task3.checkNestingPossibility(encase, nested)).isEqualTo(true);
    }

    @Test
    @DisplayName("Вложение наполненного массива в пустой")
    void test3() {
        final int[] encase = {};
        final int[] nested = {1, 2};

        assertThat(Task3.checkNestingPossibility(encase, nested)).isEqualTo(false);
    }

    @Test
    @DisplayName("Корректное вложение")
    void test4() {
        final int[] encase = {0, 1, 2, 3, 4};
        final int[] nested = {2, 3};

        assertThat(Task3.checkNestingPossibility(encase, nested)).isEqualTo(true);
    }

    @Test
    @DisplayName("Некорректное вложение")
    void test5() {
        final int[] encase = {1, 2, 3, 4};
        final int[] nested = {0, 1, 2};

        assertThat(Task3.checkNestingPossibility(encase, nested)).isEqualTo(false);
    }

    @Test
    @DisplayName("Корректное вложение массива из одного элемента в массив большего размера")
    void test6() {
        final int[] encase = {1, 3};
        final int[] nested = {2};

        assertThat(Task3.checkNestingPossibility(encase, nested)).isEqualTo(true);
    }

    @Test
    @DisplayName("По-умолчанию некорректное вложение массива из большего размера в массив из одного элемента")
    void test7() {
        final int[] encase = {100};
        final int[] nested = {1, 2, 3};

        assertThat(Task3.checkNestingPossibility(encase, nested)).isEqualTo(false);
    }
}
