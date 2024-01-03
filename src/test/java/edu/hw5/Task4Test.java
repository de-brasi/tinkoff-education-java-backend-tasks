package edu.hw5;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Task4Test {
    @Test
    @DisplayName("Тест, валидный пароль: aF SDs fsa ~aSDasd")
    public void test1() {
        final String password = "aF SDs fsa ~aSDasd";
        assertThat(Task4.validatePassword(password)).isTrue();
    }

    @Test
    @DisplayName("Тест, валидный пароль: jkABDBN              !        asdasd//,lmapf")
    public void test2() {
        final String password = "jkABDBN              !        asdasd//,lmapf";
        assertThat(Task4.validatePassword(password)).isTrue();
    }

    @Test
    @DisplayName("Тест, валидный пароль: someValIdPasSwOrd___@")
    public void test3() {
        final String password = "someValIdPasSwOrd___@";
        assertThat(Task4.validatePassword(password)).isTrue();
    }

    @Test
    @DisplayName("Тест, валидный пароль: ###########")
    public void test4() {
        final String password = "###########";
        assertThat(Task4.validatePassword(password)).isTrue();
    }

    @Test
    @DisplayName("Тест, валидный пароль: $!#%$!/>")
    public void test5() {
        final String password = "$!#%$!/>";
        assertThat(Task4.validatePassword(password)).isTrue();
    }

    @Test
    @DisplayName("Тест, валидный пароль: ^%%%%$")
    public void test6() {
        final String password = "^%%%%$";
        assertThat(Task4.validatePassword(password)).isTrue();
    }

    @Test
    @DisplayName("Тест, валидный пароль: ^")
    public void test7() {
        final String password = "^";
        assertThat(Task4.validatePassword(password)).isTrue();
    }

    @Test
    @DisplayName("Тест, валидный пароль: &")
    public void test8() {
        final String password = "&";
        assertThat(Task4.validatePassword(password)).isTrue();
    }

    @Test
    @DisplayName("Тест, валидный пароль: *")
    public void test9() {
        final String password = "*";
        assertThat(Task4.validatePassword(password)).isTrue();
    }

    @Test
    @DisplayName("Тест, валидный пароль: |")
    public void test10() {
        final String password = "|";
        assertThat(Task4.validatePassword(password)).isTrue();
    }

    @Test
    @DisplayName("Тест, невалидный пароль: какой-то неправильный пароль :(")
    public void test11() {
        final String password = "какой-то неправильный пароль :(";
        assertThat(Task4.validatePassword(password)).isFalse();
    }

    @Test
    @DisplayName("Тест, невалидный пароль: \\(-__-)/")
    public void test12() {
        final String password = "\\(-__-)/";
        assertThat(Task4.validatePassword(password)).isFalse();
    }

    @Test
    @DisplayName("Тест, невалидный пароль: *to many spaces*")
    public void test13() {
        final String password = "                     ";
        assertThat(Task4.validatePassword(password)).isFalse();
    }

    @Test
    @DisplayName("Тест, невалидный пароль: 1+1 == 3 ?")
    public void test14() {
        final String password = "1+1 == 3 ?";
        assertThat(Task4.validatePassword(password)).isFalse();
    }
}
