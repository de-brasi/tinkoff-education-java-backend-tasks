package edu.hw1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Task5Test {
    @Test
    @DisplayName("Тест из условия: 11211230 ➞ true")
    void test1() {
        assertThat(Task5.isPalindromeDescendant(11211230)).isEqualTo(true);
    }

    @Test
    @DisplayName("Тест из условия: 13001120 ➞ true")
    void test2() {
        assertThat(Task5.isPalindromeDescendant(13001120)).isEqualTo(true);
    }

    @Test
    @DisplayName("Тест из условия: 23336014 ➞ true")
    void test3() {
        assertThat(Task5.isPalindromeDescendant(23336014)).isEqualTo(true);
    }

    @Test
    @DisplayName("Тест из условия: 11 ➞ true")
    void test4() {
        assertThat(Task5.isPalindromeDescendant(11)).isEqualTo(true);
    }

    @Test
    @DisplayName("Тест: 1 ➞ true")
    void test5() {
        assertThat(Task5.isPalindromeDescendant(1)).isEqualTo(true);
    }

    @Test
    @DisplayName("Тест: 0 ➞ true")
    void test6() {
        assertThat(Task5.isPalindromeDescendant(0)).isEqualTo(true);
    }

    @Test
    @DisplayName("Тест: 123 ➞ true (так как 123 ➞ 33 ➞ true)")
    void test7() {
        assertThat(Task5.isPalindromeDescendant(123)).isEqualTo(true);
    }

    @Test
    @DisplayName("Тест: 349 ➞ false")
    void test8() {
        assertThat(Task5.isPalindromeDescendant(349)).isEqualTo(false);
    }

    @Test
    @DisplayName("Тест: 123321 ➞ true")
    void test9() {
        assertThat(Task5.isPalindromeDescendant(123321)).isEqualTo(true);
    }

    @Test
    @DisplayName("Тест: 12321 ➞ true")
    void test10() {
        assertThat(Task5.isPalindromeDescendant(12321)).isEqualTo(true);
    }
}
