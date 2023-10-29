package edu.hw3;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.MapAssert.assertThatMap;

public class Task3Test {
    @Test
    @DisplayName("Тест из условия: freqDict([\"a\", \"bb\", \"a\", \"bb\"]) → {\"bb\": 2, \"a\": 2}")
    public void test1() {
        final String[] source = {"a", "bb", "a", "bb"};
        final var expectedResult = new HashMap<String, Integer>() {{
            put("a", 2);
            put("bb", 2);
        }};

        final var actualResult = Task3.freqDict(source);

        assertThatMap(expectedResult).isEqualTo(actualResult);
    }

    @Test
    @DisplayName("Тест из условия: freqDict([\"this\", \"and\", \"that\", \"and\"]) → {\"that\": 1, \"and\": 2, \"this\": 1}")
    public void test2() {
        final String[] source = {"this", "and", "that", "and"};
        final var expectedResult = new HashMap<String, Integer>() {{
            put("and", 2);
            put("this", 1);
            put("that", 1);
        }};

        final var actualResult = Task3.freqDict(source);

        assertThatMap(expectedResult).isEqualTo(actualResult);
    }

    @Test
    @DisplayName("Тест из условия: freqDict([\"код\", \"код\", \"код\", \"bug\"]) → {\"код\": 3, \"bug\": 1}")
    public void test3() {
        final String[] source = {"код", "код", "код", "bug"};
        final var expectedResult = new HashMap<String, Integer>() {{
            put("код", 3);
            put("bug", 1);
        }};

        final var actualResult = Task3.freqDict(source);

        assertThatMap(expectedResult).isEqualTo(actualResult);
    }

    @Test
    @DisplayName("Тест из условия: freqDict([1, 1, 2, 2]) → {1: 2, 2: 2}")
    public void test4() {
        final Integer[] source = {1, 1, 2, 2};
        final var expectedResult = new HashMap<Integer, Integer>() {{
            put(1, 2);
            put(2, 2);
        }};

        final var actualResult = Task3.freqDict(source);

        assertThatMap(expectedResult).isEqualTo(actualResult);
    }

    @Test
    @DisplayName("Тест, пустая коллекция: freqDict([]) → {}")
    public void test5() {
        final String[] source = {};
        final var expectedResult = new HashMap<String, Integer>();

        final var actualResult = Task3.freqDict(source);

        assertThatMap(expectedResult).isEqualTo(actualResult);
    }
}
