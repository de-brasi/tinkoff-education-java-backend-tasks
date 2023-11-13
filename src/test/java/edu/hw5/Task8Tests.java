package edu.hw5;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Task8Tests {
    // Subtask 1: нечетной длины
    @Test
    @DisplayName("Subtask-1 Тест: валидная строка '0'")
    public void testSubtask1ValidString1() {
        final String toValidate = "0";
        assertThat(Task8.validateSubtask1(toValidate)).isTrue();
    }

    @Test
    @DisplayName("Subtask-1 Тест: валидная строка '010'")
    public void testSubtask1ValidString2() {
        final String toValidate = "010";
        assertThat(Task8.validateSubtask1(toValidate)).isTrue();
    }

    @Test
    @DisplayName("Subtask-1 Тест: валидная строка '11111'")
    public void testSubtask1ValidString3() {
        final String toValidate = "11111";
        assertThat(Task8.validateSubtask1(toValidate)).isTrue();
    }

    @Test
    @DisplayName("Subtask-1 Тест: валидная строка '1'")
    public void testSubtask1ValidString4() {
        final String toValidate = "1";
        assertThat(Task8.validateSubtask1(toValidate)).isTrue();
    }

    @Test
    @DisplayName("Subtask-1 Тест: валидная строка '110'")
    public void testSubtask1ValidString5() {
        final String toValidate = "110";
        assertThat(Task8.validateSubtask1(toValidate)).isTrue();
    }

    @Test
    @DisplayName("Subtask-1 Тест: валидная строка '1111111110'")
    public void testSubtask1ValidString6() {
        final String toValidate = "111111110";
        assertThat(Task8.validateSubtask1(toValidate)).isTrue();
    }

    @Test
    @DisplayName("Subtask-1 Тест: НЕ валидная строка '3'")
    public void testSubtask1InvalidString1() {
        final String toValidate = "3";
        assertThat(Task8.validateSubtask1(toValidate)).isFalse();
    }

    @Test
    @DisplayName("Subtask-1 Тест: НЕ валидная строка '11'")
    public void testSubtask1InvalidString2() {
        final String toValidate = "11";
        assertThat(Task8.validateSubtask1(toValidate)).isFalse();
    }

    @Test
    @DisplayName("Subtask-1 Тест: НЕ валидная строка '1o1'")
    public void testSubtask1InvalidString3() {
        final String toValidate = "1o1";
        assertThat(Task8.validateSubtask1(toValidate)).isFalse();
    }

    @Test
    @DisplayName("Subtask-1 Тест: НЕ валидная строка '11110000'")
    public void testSubtask1InvalidString4() {
        final String toValidate = "11110000";
        assertThat(Task8.validateSubtask1(toValidate)).isFalse();
    }

    @Test
    @DisplayName("Subtask-1 Тест: НЕ валидная (пустая) строка ''")
    public void testSubtask1InvalidString5() {
        final String toValidate = "";
        assertThat(Task8.validateSubtask1(toValidate)).isFalse();
    }

    @Test
    @DisplayName("Subtask-1 Тест: НЕ валидная строка ' '")
    public void testSubtask1InvalidString6  () {
        final String toValidate = " ";
        assertThat(Task8.validateSubtask1(toValidate)).isFalse();
    }


    // Subtask 2: начинается с 0 и имеет нечетную длину, или начинается с 1 и имеет четную длину
    @Test
    @DisplayName("Subtask-2 Тест: валидная строка '0'")
    public void testSubtask2ValidString1() {
        final String toValidate = "0";
        assertThat(Task8.validateSubtask2(toValidate)).isTrue();
    }

    @Test
    @DisplayName("Subtask-2 Тест: валидная строка '011'")
    public void testSubtask2ValidString2() {
        final String toValidate = "011";
        assertThat(Task8.validateSubtask2(toValidate)).isTrue();
    }

    @Test
    @DisplayName("Subtask-2 Тест: валидная строка '010'")
    public void testSubtask2ValidString3() {
        final String toValidate = "010";
        assertThat(Task8.validateSubtask2(toValidate)).isTrue();
    }

    @Test
    @DisplayName("Subtask-2 Тест: валидная строка '11'")
    public void testSubtask2ValidString4() {
        final String toValidate = "11";
        assertThat(Task8.validateSubtask2(toValidate)).isTrue();
    }

    @Test
    @DisplayName("Subtask-2 Тест: валидная строка '10'")
    public void testSubtask2ValidString5() {
        final String toValidate = "10";
        assertThat(Task8.validateSubtask2(toValidate)).isTrue();
    }

    @Test
    @DisplayName("Subtask-2 Тест: валидная строка '1000'")
    public void testSubtask2ValidString6() {
        final String toValidate = "1000";
        assertThat(Task8.validateSubtask2(toValidate)).isTrue();
    }

    @Test
    @DisplayName("Subtask-2 Тест: НЕ валидная строка '3'")
    public void testSubtask2InvalidString1() {
        final String toValidate = "3";
        assertThat(Task8.validateSubtask2(toValidate)).isFalse();
    }

    @Test
    @DisplayName("Subtask-2 Тест: НЕ валидная строка '1'")
    public void testSubtask2InvalidString2() {
        final String toValidate = "1";
        assertThat(Task8.validateSubtask2(toValidate)).isFalse();
    }

    @Test
    @DisplayName("Subtask-2 Тест: НЕ валидная строка '00'")
    public void testSubtask2InvalidString3() {
        final String toValidate = "00";
        assertThat(Task8.validateSubtask2(toValidate)).isFalse();
    }

    @Test
    @DisplayName("Subtask-2 Тест: НЕ валидная строка '0011'")
    public void testSubtask2InvalidString4() {
        final String toValidate = "0011";
        assertThat(Task8.validateSubtask2(toValidate)).isFalse();
    }

    @Test
    @DisplayName("Subtask-2 Тест: НЕ валидная строка ''")
    public void testSubtask2InvalidString5() {
        final String toValidate = "";
        assertThat(Task8.validateSubtask2(toValidate)).isFalse();
    }

    @Test
    @DisplayName("Subtask-2 Тест: НЕ валидная строка ' '")
    public void testSubtask2InvalidString6() {
        final String toValidate = " ";
        assertThat(Task8.validateSubtask2(toValidate)).isFalse();
    }


    // Subtask 3: количество 0 кратно 3
    @Test
    @DisplayName("Subtask-3 Тест: валидная строка '000'")
    public void testSubtask3ValidString1() {
        final String toValidate = "000";
        assertThat(Task8.validateSubtask3(toValidate)).isTrue();
    }

    @Test
    @DisplayName("Subtask-3 Тест: валидная строка '010101'")
    public void testSubtask3ValidString2() {
        final String toValidate = "000";
        assertThat(Task8.validateSubtask3(toValidate)).isTrue();
    }

    @Test
    @DisplayName("Subtask-3 Тест: валидная строка '000000'")
    public void testSubtask3ValidString3() {
        final String toValidate = "000000";
        assertThat(Task8.validateSubtask3(toValidate)).isTrue();
    }

    @Test
    @DisplayName("Subtask-3 Тест: валидная строка '1011111111111111111111110101'")
    public void testSubtask3ValidString4() {
        final String toValidate = "1011111111111111111111110101";
        assertThat(Task8.validateSubtask3(toValidate)).isTrue();
    }

    @Test
    @DisplayName("Subtask-3 Тест: валидная строка '111000111'")
    public void testSubtask3ValidString5() {
        final String toValidate = "111000111";
        assertThat(Task8.validateSubtask3(toValidate)).isTrue();
    }

    @Test
    @DisplayName("Subtask-3 Тест: НЕ валидная строка '3'")
    public void testSubtask3InvalidString1() {
        final String toValidate = "3";
        assertThat(Task8.validateSubtask3(toValidate)).isFalse();
    }

    @Test
    @DisplayName("Subtask-3 Тест: НЕ валидная строка ''")
    public void testSubtask3InvalidString2() {
        final String toValidate = "";
        assertThat(Task8.validateSubtask3(toValidate)).isFalse();
    }

    @Test
    @DisplayName("Subtask-3 Тест: НЕ валидная строка ' '")
    public void testSubtask3InvalidString3() {
        final String toValidate = " ";
        assertThat(Task8.validateSubtask3(toValidate)).isFalse();
    }

    @Test
    @DisplayName("Subtask-3 Тест: НЕ валидная строка '111'")
    public void testSubtask3InvalidString4() {
        final String toValidate = "111";
        assertThat(Task8.validateSubtask3(toValidate)).isFalse();
    }

    @Test
    @DisplayName("Subtask-3 Тест: НЕ валидная строка '11100'")
    public void testSubtask3InvalidString5() {
        final String toValidate = "11100";
        assertThat(Task8.validateSubtask3(toValidate)).isFalse();
    }

    @Test
    @DisplayName("Subtask-3 Тест: НЕ валидная строка '0'")
    public void testSubtask3InvalidString6() {
        final String toValidate = "0";
        assertThat(Task8.validateSubtask3(toValidate)).isFalse();
    }

    @Test
    @DisplayName("Subtask-3 Тест: НЕ валидная строка '00'")
    public void testSubtask3InvalidString7() {
        final String toValidate = "00";
        assertThat(Task8.validateSubtask3(toValidate)).isFalse();
    }


    // Subtask 4: нечетной длины
    @Test
    @DisplayName("Subtask-4 Тест: валидная строка '0'")
    public void testSubtask4ValidString1() {
        final String toValidate = "0";
        assertThat(Task8.validateSubtask4(toValidate)).isTrue();
    }

    @Test
    @DisplayName("Subtask-4 Тест: валидная строка '0110'")
    public void testSubtask4ValidString2() {
        final String toValidate = "0110";
        assertThat(Task8.validateSubtask4(toValidate)).isTrue();
    }

    @Test
    @DisplayName("Subtask-4 Тест: валидная строка '01110'")
    public void testSubtask4ValidString3() {
        final String toValidate = "01110";
        assertThat(Task8.validateSubtask4(toValidate)).isTrue();
    }

    @Test
    @DisplayName("Subtask-4 Тест: валидная строка '1110'")
    public void testSubtask4ValidString4() {
        final String toValidate = "1110";
        assertThat(Task8.validateSubtask4(toValidate)).isTrue();
    }

    @Test
    @DisplayName("Subtask-4 Тест: НЕ валидная строка '11'")
    public void testSubtask4InvalidString1() {
        final String toValidate = "11";
        assertThat(Task8.validateSubtask4(toValidate)).isFalse();
    }

    @Test
    @DisplayName("Subtask-4 Тест: НЕ валидная строка '111'")
    public void testSubtask4InvalidString2() {
        final String toValidate = "111";
        assertThat(Task8.validateSubtask4(toValidate)).isFalse();
    }

    @Test
    @DisplayName("Subtask-4 Тест: НЕ валидная строка 'a'")
    public void testSubtask4InvalidString3() {
        final String toValidate = "a";
        assertThat(Task8.validateSubtask4(toValidate)).isFalse();
    }

    @Test
    @DisplayName("Subtask-4 Тест: НЕ валидная строка ' '")
    public void testSubtask4InvalidString4() {
        final String toValidate = " ";
        assertThat(Task8.validateSubtask4(toValidate)).isFalse();
    }
}
