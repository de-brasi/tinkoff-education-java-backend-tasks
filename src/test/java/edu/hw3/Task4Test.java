package edu.hw3;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Task4Test {
    @Test
    @DisplayName("Тест из условия: convertToRoman(2) ➞ \"II\"")
    public void test1() {
        final int source = 2;
        final var expectedResult = "II";

        final String actualResult = Task4.convertToRoman(source);

        assertThat(expectedResult).isEqualTo(actualResult);
    }

    @Test
    @DisplayName("Тест из условия: convertToRoman(12) ➞ \"XII\"")
    public void test2() {
        final int source = 12;
        final var expectedResult = "XII";

        final String actualResult = Task4.convertToRoman(source);

        assertThat(expectedResult).isEqualTo(actualResult);
    }

    @Test
    @DisplayName("Тест из условия: convertToRoman(16) ➞ \"XVI\"")
    public void test3() {
        final int source = 16;
        final var expectedResult = "XVI";

        final String actualResult = Task4.convertToRoman(source);

        assertThat(expectedResult).isEqualTo(actualResult);
    }

    @Test
    @DisplayName("Тест: convertToRoman(26) ➞ \"XVI\"")
    public void test4() {
        final int source = 26;
        final var expectedResult = "XXVI";

        final String actualResult = Task4.convertToRoman(source);

        assertThat(expectedResult).isEqualTo(actualResult);
    }

    @Test
    @DisplayName("Тест: convertToRoman(14) ➞ \"XIV\"")
    public void test5() {
        final int source = 14;
        final var expectedResult = "XIV";

        final String actualResult = Task4.convertToRoman(source);

        assertThat(expectedResult).isEqualTo(actualResult);
    }

    @Test
    @DisplayName("Тест: convertToRoman(15) ➞ \"XV\"")
    public void test6() {
        final int source = 15;
        final var expectedResult = "XV";

        final String actualResult = Task4.convertToRoman(source);

        assertThat(expectedResult).isEqualTo(actualResult);
    }

    @Test
    @DisplayName("Тест: convertToRoman(16) ➞ \"XVI\"")
    public void test7() {
        final int source = 16;
        final var expectedResult = "XVI";

        final String actualResult = Task4.convertToRoman(source);

        assertThat(expectedResult).isEqualTo(actualResult);
    }

    @Test
    @DisplayName("Тест: convertToRoman(17) ➞ \"XVII\"")
    public void test8() {
        final int source = 17;
        final var expectedResult = "XVII";

        final String actualResult = Task4.convertToRoman(source);

        assertThat(expectedResult).isEqualTo(actualResult);
    }

    @Test
    @DisplayName("Тест: convertToRoman(19) ➞ \"XIX\"")
    public void test9() {
        final int source = 19;
        final var expectedResult = "XIX";

        final String actualResult = Task4.convertToRoman(source);

        assertThat(expectedResult).isEqualTo(actualResult);
    }
}
