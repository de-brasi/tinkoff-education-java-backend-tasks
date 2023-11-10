package edu.hw3;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


public class Task2Test {
    @Test
    @DisplayName("Тест из условия: \"()()()\"")
    public void test1() {
        final String source = "()()()";
        final var expectedResult = new String[] {"()", "()", "()"};

        final String[] actualResult = Task2.clusterize(source);

        assertThat(expectedResult).isEqualTo(actualResult);
    }

    @Test
    @DisplayName("Тест из условия: \"((()))\"")
    public void test2() {
        final String source = "((()))";
        final var expectedResult = new String[] {"((()))"};

        final String[] actualResult = Task2.clusterize(source);

        assertThat(expectedResult).isEqualTo(actualResult);
    }

    @Test
    @DisplayName("Тест из условия: \"((()))(())()()(()())\"")
    public void test3() {
        final String source = "((()))(())()()(()())";
        final var expectedResult = new String[] {"((()))", "(())", "()", "()", "(()())"};

        final String[] actualResult = Task2.clusterize(source);

        assertThat(expectedResult).isEqualTo(actualResult);
    }

    @Test
    @DisplayName("Тест из условия: \"((())())(()(()()))\"")
    public void test4() {
        final String source = "((())())(()(()()))";
        final var expectedResult = new String[] {"((())())", "(()(()()))"};

        final String[] actualResult = Task2.clusterize(source);

        assertThat(expectedResult).isEqualTo(actualResult);
    }

    @Test
    @DisplayName("Тест, простой случай с контентом в строках: \"(some)(content)\"")
    public void test5() {
        final String source = "(some)(content)";
        final var expectedResult = new String[] {"(some)", "(content)"};

        final String[] actualResult = Task2.clusterize(source);

        assertThat(expectedResult).isEqualTo(actualResult);
    }

    @Test
    @DisplayName("Тест, случай с вложением, контент в строках: \"(({some}))([content])\"")
    public void test6() {
        final String source = "(({some}))([content])";
        final var expectedResult = new String[] {"(({some}))", "([content])"};

        final String[] actualResult = Task2.clusterize(source);

        assertThat(expectedResult).isEqualTo(actualResult);
    }

    @Test
    @DisplayName("Тест, правильные СП подряд: \"(()())\"")
    public void test7() {
        final String source = "(()())";
        final var expectedResult = new String[] {"(()())"};

        final String[] actualResult = Task2.clusterize(source);

        assertThat(expectedResult).isEqualTo(actualResult);
    }

    @Test
    @DisplayName("Тест, правильные СП подряд: \"((some)())\"")
    public void test8() {
        final String source = "((some)())";
        final var expectedResult = new String[] {"((some)())"};

        final String[] actualResult = Task2.clusterize(source);

        assertThat(expectedResult).isEqualTo(actualResult);
    }
}
