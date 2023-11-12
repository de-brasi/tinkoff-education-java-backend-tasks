package edu.hw5;

import java.time.LocalDate;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Task3Test {
    @Test
    @DisplayName("Тест из условия: 2020-10-10")
    public void test1() {
        final String dateToParse = "2020-10-10";
        final Optional<LocalDate> expectedResult = Optional.of(LocalDate.of(2020, 10, 10));
        final Optional<LocalDate> actualResult = Task3.parseDate(dateToParse);

        assertThat(expectedResult).isEqualTo(actualResult);
    }

    @Test
    @DisplayName("Тест из условия: 2020-12-2")
    public void test2() {
        final String dateToParse = "2020-12-2";
        final Optional<LocalDate> expectedResult = Optional.of(LocalDate.of(2020, 12, 2));
        final Optional<LocalDate> actualResult = Task3.parseDate(dateToParse);

        assertThat(expectedResult).isEqualTo(actualResult);
    }

    @Test
    @DisplayName("Тест из условия: 1/3/1976")
    public void test3() {
        final String dateToParse = "1/3/1976";
        final Optional<LocalDate> expectedResult = Optional.of(LocalDate.of(1976, 3, 1));
        final Optional<LocalDate> actualResult = Task3.parseDate(dateToParse);

        assertThat(expectedResult).isEqualTo(actualResult);
    }

    @Test
    @DisplayName("Тест из условия: 1/3/20")
    public void test4() {
        final String dateToParse = "1/3/20";
        final Optional<LocalDate> expectedResult = Optional.of(LocalDate.of(2020, 3, 1));
        final Optional<LocalDate> actualResult = Task3.parseDate(dateToParse);

        assertThat(expectedResult).isEqualTo(actualResult);
    }

    @Test
    @DisplayName("Тест из условия: tomorrow")
    public void test5() {
        final String dateToParse = "tomorrow";
        final Optional<LocalDate> expectedResult = Optional.of(LocalDate.now().plusDays(1));
        final Optional<LocalDate> actualResult = Task3.parseDate(dateToParse);

        assertThat(expectedResult).isEqualTo(actualResult);
    }

    @Test
    @DisplayName("Тест из условия: today")
    public void test6() {
        final String dateToParse = "today";
        final Optional<LocalDate> expectedResult = Optional.of(LocalDate.now());
        final Optional<LocalDate> actualResult = Task3.parseDate(dateToParse);

        assertThat(expectedResult).isEqualTo(actualResult);
    }

    @Test
    @DisplayName("Тест из условия: yesterday")
    public void test7() {
        final String dateToParse = "yesterday";
        final Optional<LocalDate> expectedResult = Optional.of(LocalDate.now().minusDays(1));
        final Optional<LocalDate> actualResult = Task3.parseDate(dateToParse);

        assertThat(expectedResult).isEqualTo(actualResult);
    }

    @Test
    @DisplayName("Тест из условия: 1 day ago")
    public void test8() {
        final String dateToParse = "1 day ago";
        final Optional<LocalDate> expectedResult = Optional.of(LocalDate.now().minusDays(1));
        final Optional<LocalDate> actualResult = Task3.parseDate(dateToParse);

        assertThat(expectedResult).isEqualTo(actualResult);
    }

    @Test
    @DisplayName("Тест из условия: 2234 days ago")
    public void test9() {
        final String dateToParse = "2234 days ago";
        final Optional<LocalDate> expectedResult = Optional.of(LocalDate.now().minusDays(2234));
        final Optional<LocalDate> actualResult = Task3.parseDate(dateToParse);

        assertThat(expectedResult).isEqualTo(actualResult);
    }

    @Test
    @DisplayName("Тест неожиданный формат: not today")
    public void test10() {
        final String dateToParse = "not today";
        final Optional<LocalDate> expectedResult = Optional.<LocalDate>empty();
        final Optional<LocalDate> actualResult = Task3.parseDate(dateToParse);

        assertThat(expectedResult).isEqualTo(actualResult);
    }

    @Test
    @DisplayName("Тест неожиданный формат: sometext")
    public void test11() {
        final String dateToParse = "sometext";
        final Optional<LocalDate> expectedResult = Optional.<LocalDate>empty();
        final Optional<LocalDate> actualResult = Task3.parseDate(dateToParse);

        assertThat(expectedResult).isEqualTo(actualResult);
    }

    @Test
    @DisplayName("Тест неожиданный формат: 2020 10 10")
    public void test12() {
        final String dateToParse = "2020 10 10";
        final Optional<LocalDate> expectedResult = Optional.<LocalDate>empty();
        final Optional<LocalDate> actualResult = Task3.parseDate(dateToParse);

        assertThat(expectedResult).isEqualTo(actualResult);
    }
}
