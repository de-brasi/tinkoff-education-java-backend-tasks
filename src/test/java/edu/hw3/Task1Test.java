package edu.hw3;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Task1Test {
    @Test
    @DisplayName("Тест из условия: \"Hello world!\"")
    public void test1() {
        final String source = "Hello world!";
        final String expectedResult = "Svool dliow!";

        final String actualResult = Task1.atbash(source);

        assertThat(expectedResult).isEqualTo(actualResult);
    }

    @Test
    @DisplayName("Тест из условия: " +
        "\"Any fool can write code that a computer can understand. " +
        "Good programmers write code that humans can understand. ― Martin Fowler\"")
    public void test2() {
        final String source = "Any fool can write code that a computer can understand. " +
            "Good programmers write code that humans can understand. ― Martin Fowler";
        final String expectedResult = "Zmb ullo xzm dirgv xlwv gszg z xlnkfgvi xzm fmwvihgzmw. " +
            "Tllw kiltiznnvih dirgv xlwv gszg sfnzmh xzm fmwvihgzmw. ― Nzigrm Uldovi";

        final String actualResult = Task1.atbash(source);

        assertThat(expectedResult).isEqualTo(actualResult);
    }

    @Test
    @DisplayName("Тест, только спецсимволы: \".-!, \"")
    public void test3() {
        final String source = ".-!, ";
        final String expectedResult = ".-!, ";

        final String actualResult = Task1.atbash(source);

        assertThat(expectedResult).isEqualTo(actualResult);
    }

    @Test
    @DisplayName("Тест, пустая строка: \"\"")
    public void test4() {
        final String source = "";
        final String expectedResult = "";

        final String actualResult = Task1.atbash(source);

        assertThat(expectedResult).isEqualTo(actualResult);
    }

    @Test
    @DisplayName("Тест, регистры: \"sOmE mEsSaGe\"")
    public void test5() {
        final String source = "sOmE mEsSaGe";
        final String expectedResult = "hLnV nVhHzTv";

        final String actualResult = Task1.atbash(source);

        assertThat(expectedResult).isEqualTo(actualResult);
    }
}
