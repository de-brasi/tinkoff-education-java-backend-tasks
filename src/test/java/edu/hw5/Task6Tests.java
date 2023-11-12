package edu.hw5;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Task6Tests {
    @Test
    @DisplayName("Строка 'some string' содержит подстроку 'string'")
    public void test1() {
        final String source = "some string";
        final String substring = "string";
        assertThat(Task6.isSubstring(source, substring)).isTrue();
    }

    @Test
    @DisplayName("Строка 'aaaBBBaaa' содержит подстроку 'BBB'")
    public void test2() {
        final String source = "aaaBBBaaa";
        final String substring = "BBB";
        assertThat(Task6.isSubstring(source, substring)).isTrue();
    }

    @Test
    @DisplayName("Строка 'starts with' содержит подстроку 'starts'")
    public void test3() {
        final String source = "starts with";
        final String substring = "starts";
        assertThat(Task6.isSubstring(source, substring)).isTrue();
    }

    @Test
    @DisplayName("Строка 'unique' содержит подстроку 'unique'")
    public void test4() {
        final String source = "unique";
        final String substring = "unique";
        assertThat(Task6.isSubstring(source, substring)).isTrue();
    }

    @Test
    @DisplayName("Строка 'one ! symbol' содержит подстроку '!'")
    public void test5() {
        final String source = "one ! symbol";
        final String substring = "!";
        assertThat(Task6.isSubstring(source, substring)).isTrue();
    }

    @Test
    @DisplayName("Строка 'one ! symbol' НЕ содержит подстроку '?'")
    public void test6() {
        final String source = "one ! symbol";
        final String substring = "?";
        assertThat(Task6.isSubstring(source, substring)).isFalse();
    }

    @Test
    @DisplayName("Строка 'one two' НЕ содержит подстроку 'three'")
    public void test7() {
        final String source = "one two";
        final String substring = "three";
        assertThat(Task6.isSubstring(source, substring)).isFalse();
    }

    @Test
    @DisplayName("Строка 'some long' НЕ содержит подстроку 'some long sentence'")
    public void test8() {
        final String source = "some long";
        final String substring = "some long sentence";
        assertThat(Task6.isSubstring(source, substring)).isFalse();
    }
}
