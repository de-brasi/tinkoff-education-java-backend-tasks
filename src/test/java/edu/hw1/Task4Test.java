package edu.hw1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Task4Test {
    @Test
    @DisplayName("Тест из условия: \"123456\") ➞ \"214365\"")
    void test1() {
        assertThat(Task4.fixString("123456")).isEqualTo("214365");
    }

    @Test
    @DisplayName("Тест из условия: \"hTsii  s aimex dpus rtni.g\" ➞ \"This is a mixed up string.\"")
    void test2() {
        assertThat(Task4.fixString("hTsii  s aimex dpus rtni.g")).isEqualTo("This is a mixed up string.");
    }

    @Test
    @DisplayName("Тест из условия: \"badce\" ➞ \"abcde\"")
    void test3() {
        assertThat(Task4.fixString("badce")).isEqualTo("abcde");
    }
}
