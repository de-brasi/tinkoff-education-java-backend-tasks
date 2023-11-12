package edu.hw5;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Task5Test {
    @Test
    @DisplayName("Тест из примера, валидный номер: А123ВЕ777")
    public void test1() {
        final String carNumber = "А123ВЕ777";
        assertThat(edu.hw5.Task5.validateRussianCarNumber(carNumber)).isTrue();
    }

    @Test
    @DisplayName("Тест из примера, валидный номер: О777ОО177")
    public void test2() {
        final String carNumber = "О777ОО177";
        assertThat(edu.hw5.Task5.validateRussianCarNumber(carNumber)).isTrue();
    }

    @Test
    @DisplayName("Тест из примера, невалидный номер: 123АВЕ777")
    public void test3() {
        final String carNumber = "123АВЕ777";
        assertThat(edu.hw5.Task5.validateRussianCarNumber(carNumber)).isFalse();
    }

    @Test
    @DisplayName("Тест из примера, невалидный номер: А123ВГ77")
    public void test4() {
        final String carNumber = "А123ВГ77";
        assertThat(edu.hw5.Task5.validateRussianCarNumber(carNumber)).isFalse();
    }

    @Test
    @DisplayName("Тест из примера, невалидный номер: А123ВЕ7777")
    public void test5() {
        final String carNumber = "А123ВЕ7777";
        assertThat(edu.hw5.Task5.validateRussianCarNumber(carNumber)).isFalse();
    }

    @Test
    @DisplayName("Тест, валидный номер в строке: <space>А123ВЕ777<space>")
    public void test6() {
        final String carNumber = " А123ВЕ777 ";
        assertThat(edu.hw5.Task5.validateRussianCarNumber(carNumber)).isFalse();
    }

    @Test
    @DisplayName("Тест, валидный номер в строке: Ф А123ВЕ777 Ф")
    public void test7() {
        final String carNumber = "Ф А123ВЕ777 Ф";
        assertThat(edu.hw5.Task5.validateRussianCarNumber(carNumber)).isFalse();
    }

    @Test
    @DisplayName("Тест, валидный номер в строке: какая-тоА123ВЕ777строка")
    public void test8() {
        final String carNumber = "какая-тоА123ВЕ777строка";
        assertThat(edu.hw5.Task5.validateRussianCarNumber(carNumber)).isFalse();
    }

    @Test
    @DisplayName("Тест, валидный номер в строке: какая-тоА123ВЕ777")
    public void test9() {
        final String carNumber = "какая-тоА123ВЕ777";
        assertThat(edu.hw5.Task5.validateRussianCarNumber(carNumber)).isFalse();
    }

    @Test
    @DisplayName("Тест, валидный номер в строке: А123ВЕ777строка")
    public void test10() {
        final String carNumber = "А123ВЕ777строка";
        assertThat(Task5.validateRussianCarNumber(carNumber)).isFalse();
    }
}
