package edu.hw3;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Task5Test {
    @Test
    @DisplayName("Тест из условия: " +
        "parseContacts([ \"John Locke\", \"Thomas Aquinas\", " +
        "\"David Hume\", \"Rene Descartes\" ], \"ASC\")")
    public void test1() {
        final var source = new String[] {"John Locke", "Thomas Aquinas", "David Hume", "Rene Descartes"};
        final var order = "ASC";

        final var expectedResult = new PersonInfo[] {
            new PersonInfo("Thomas", "Aquinas"),
            new PersonInfo("Rene", "Descartes"),
            new PersonInfo("David", "Hume"),
            new PersonInfo("John", "Locke")
        };


        final var actualResult = Task5.parseContacts(source, order);

        assertThat(expectedResult).isEqualTo(actualResult);
    }

    @Test
    @DisplayName("Тест из условия: " +
        "parseContacts([\"Paul Erdos\", \"Leonhard Euler\", \"Carl Gauss\"], \"DESC\")")
    public void test2() {
        final var source = new String[] {"Paul Erdos", "Leonhard Euler", "Carl Gauss"};
        final var order = "DESC";

        final var expectedResult = new PersonInfo[] {
            new PersonInfo("Carl", "Gauss"),
            new PersonInfo("Leonhard", "Euler"),
            new PersonInfo("Paul", "Erdos"),
        };

        final var actualResult = Task5.parseContacts(source, order);

        assertThat(expectedResult).isEqualTo(actualResult);
    }

    @Test
    @DisplayName("Тест из условия: " +
        "parseContacts([], \"DESC\")")
    public void test3() {
        final var source = new String[] {};
        final var order = "DESC";

        final var expectedResult = new PersonInfo[] {};

        final var actualResult = Task5.parseContacts(source, order);

        assertThat(expectedResult).isEqualTo(actualResult);
    }

    @Test
    @DisplayName("Тест из условия: " +
        "parseContacts(null, \"DESC\")")
    public void test4() {
        final String[] source = null;
        final var order = "DESC";

        final PersonInfo[] expectedResult = new PersonInfo[] {};

        final var actualResult = Task5.parseContacts(source, order);

        assertThat(expectedResult).isEqualTo(actualResult);
    }

    @Test
    @DisplayName("Тест пустое имя: " +
        "parseContacts([\"Adam\", \"James Bond\"], \"DESC\")")
    public void test5() {
        final String[] source = {"Adam", "James Bond"};
        final var order = "ASC";

        final PersonInfo[] expectedResult = new PersonInfo[] {
            new PersonInfo("Adam", ""),
            new PersonInfo("James", "Bond"),
        };

        final var actualResult = Task5.parseContacts(source, order);

        assertThat(expectedResult).isEqualTo(actualResult);
    }
}
