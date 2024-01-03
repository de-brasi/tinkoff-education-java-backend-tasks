package edu.hw5;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Task7Test {
    // Subtask 1: содержит не менее 3 символов, причем третий символ равен 0
    @Test
    @DisplayName("Тест задания 1 (содержит не менее 3 символов, причем третий символ равен 0): " +
        "валидная строка '000'")
    public void testSubtask1ValidString1() {
        final String toValidate = "000";
        assertThat(Task7.validateSubtask1(toValidate)).isTrue();
    }

    @Test
    @DisplayName("Тест задания 1 (содержит не менее 3 символов, причем третий символ равен 0): " +
        "валидная строка '1101'")
    public void testSubtask1ValidString2() {
        final String toValidate = "1101";
        assertThat(Task7.validateSubtask1(toValidate)).isTrue();
    }

    @Test
    @DisplayName("Тест задания 1 (содержит не менее 3 символов, причем третий символ равен 0): " +
        "валидная строка '110111'")
    public void testSubtask1ValidString3() {
        final String toValidate = "110111";
        assertThat(Task7.validateSubtask1(toValidate)).isTrue();
    }

    @Test
    @DisplayName("Тест задания 1 (содержит не менее 3 символов, причем третий символ равен 0): " +
        "валидная строка '1100000000000000000'")
    public void testSubtask1ValidString4() {
        final String toValidate = "1100000000000000000";
        assertThat(Task7.validateSubtask1(toValidate)).isTrue();
    }

    @Test
    @DisplayName("Тест задания 1 (содержит не менее 3 символов, причем третий символ равен 0): " +
        "НЕ валидная строка '  0'")
    public void testSubtask1InvalidString1() {
        final String toValidate = "  0";
        assertThat(Task7.validateSubtask1(toValidate)).isFalse();
    }

    @Test
    @DisplayName("Тест задания 1 (содержит не менее 3 символов, причем третий символ равен 0): " +
        "НЕ валидная строка '00'")
    public void testSubtask1InvalidString2() {
        final String toValidate = "00";
        assertThat(Task7.validateSubtask1(toValidate)).isFalse();
    }

    @Test
    @DisplayName("Тест задания 1 (содержит не менее 3 символов, причем третий символ равен 0): " +
        "НЕ валидная строка '000invalid'")
    public void testSubtask1InvalidString3() {
        final String toValidate = "000invalid";
        assertThat(Task7.validateSubtask1(toValidate)).isFalse();
    }

    @Test
    @DisplayName("Тест задания 1 (содержит не менее 3 символов, причем третий символ равен 0): НЕ валидная строка ''")
    public void testSubtask1InvalidString4() {
        final String toValidate = "111";
        assertThat(Task7.validateSubtask1(toValidate)).isFalse();
    }


    // Subtask 2: начинается и заканчивается одним и тем же символом
    @Test
    @DisplayName("Тест задания 2 (начинается и заканчивается одним и тем же символом): валидная строка '1'")
    public void testSubtask2ValidString1() {
        final String toValidate = "1";
        assertThat(Task7.validateSubtask2(toValidate)).isTrue();
    }

    @Test
    @DisplayName("Тест задания 2 (начинается и заканчивается одним и тем же символом): валидная строка '0'")
    public void testSubtask2ValidString2() {
        final String toValidate = "0";
        assertThat(Task7.validateSubtask2(toValidate)).isTrue();
    }

    @Test
    @DisplayName("Тест задания 2 (начинается и заканчивается одним и тем же символом): валидная строка '101'")
    public void testSubtask2ValidString3() {
        final String toValidate = "101";
        assertThat(Task7.validateSubtask2(toValidate)).isTrue();
    }

    @Test
    @DisplayName("Тест задания 2 (начинается и заканчивается одним и тем же символом): валидная строка '10101'")
    public void testSubtask2ValidString4() {
        final String toValidate = "10101";
        assertThat(Task7.validateSubtask2(toValidate)).isTrue();
    }

    @Test
    @DisplayName("Тест задания 2 (начинается и заканчивается одним и тем же символом): валидная строка '01010'")
    public void testSubtask2ValidString5() {
        final String toValidate = "01010";
        assertThat(Task7.validateSubtask2(toValidate)).isTrue();
    }

    @Test
    @DisplayName("Тест задания 2 (начинается и заканчивается одним и тем же символом): НЕ валидная строка 'a'")
    public void testSubtask2InvalidString1() {
        final String toValidate = "a";
        assertThat(Task7.validateSubtask2(toValidate)).isFalse();
    }

    @Test
    @DisplayName("Тест задания 2 (начинается и заканчивается одним и тем же символом): НЕ валидная строка ' '")
    public void testSubtask2InvalidString2() {
        final String toValidate = " ";
        assertThat(Task7.validateSubtask2(toValidate)).isFalse();
    }

    @Test
    @DisplayName("Тест задания 2 (начинается и заканчивается одним и тем же символом): " +
        "НЕ валидная строка 'palindrome_emordnilap'")
    public void testSubtask2InvalidString3() {
        final String toValidate = "palindrome_emordnilap";
        assertThat(Task7.validateSubtask2(toValidate)).isFalse();
    }

    @Test
    @DisplayName("Тест задания 2 (начинается и заканчивается одним и тем же символом): " +
        "НЕ валидная ПУСТАЯ строка ''")
    public void testSubtask2InvalidString4() {
        final String toValidate = "";
        assertThat(Task7.validateSubtask2(toValidate)).isFalse();
    }


    // Subtask 3: длина не менее 1 и не более 3
    @Test
    @DisplayName("Тест задания 3 (длина не менее 1 и не более 3): валидная строка '1'")
    public void testSubtask3ValidString1() {
        final String toValidate = "1";
        assertThat(Task7.validateSubtask3(toValidate)).isTrue();
    }

    @Test
    @DisplayName("Тест задания 3 (длина не менее 1 и не более 3): валидная строка '10'")
    public void testSubtask3ValidString2() {
        final String toValidate = "10";
        assertThat(Task7.validateSubtask3(toValidate)).isTrue();
    }

    @Test
    @DisplayName("Тест задания 3 (длина не менее 1 и не более 3): валидная строка '010'")
    public void testSubtask3ValidString3() {
        final String toValidate = "010";
        assertThat(Task7.validateSubtask3(toValidate)).isTrue();
    }

    @Test
    @DisplayName("Тест задания 3 (длина не менее 1 и не более 3): валидная строка '101'")
    public void testSubtask3ValidString4() {
        final String toValidate = "101";
        assertThat(Task7.validateSubtask3(toValidate)).isTrue();
    }

    @Test
    @DisplayName("Тест задания 3 (длина не менее 1 и не более 3): НЕ валидная ПУСТАЯ строка ''")
    public void testSubtask3InvalidString1() {
        final String toValidate = "";
        assertThat(Task7.validateSubtask3(toValidate)).isFalse();
    }

    @Test
    @DisplayName("Тест задания 3 (длина не менее 1 и не более 3): НЕ валидная строка '1111'")
    public void testSubtask3InvalidString2() {
        final String toValidate = "1111";
        assertThat(Task7.validateSubtask3(toValidate)).isFalse();
    }

    @Test
    @DisplayName("Тест задания 3 (длина не менее 1 и не более 3): НЕ валидная строка '00invalid'")
    public void testSubtask3InvalidString3() {
        final String toValidate = "00invalid";
        assertThat(Task7.validateSubtask3(toValidate)).isFalse();
    }

    @Test
    @DisplayName("Тест задания 3 (длина не менее 1 и не более 3): НЕ валидная строка 'aa'")
    public void testSubtask3InvalidString4() {
        final String toValidate = "aa";
        assertThat(Task7.validateSubtask3(toValidate)).isFalse();
    }
}
