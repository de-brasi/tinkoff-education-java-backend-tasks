package edu.hw2;

import edu.hw2.task2.Rectangle;
import edu.hw2.task2.Square;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Task2Test {
    @Test
    @DisplayName("Rectangle area")
    void test1() {
        var rect = new Rectangle(10, 20);
        assertThat(rect.area()).isEqualTo(200);
    }

    @Test
    @DisplayName("Square area")
    void test2() {
        Rectangle rect = new Square(10);
        assertThat(rect.area()).isEqualTo(100);
    }

    @Test
    @DisplayName("Change square size")
    void test3() {
        Rectangle rect = new Square(10);
        assertThat(rect instanceof Square).isTrue();
        assertThat(rect.area()).isEqualTo(100);

        rect = ((Square) rect).setSize(20);
        assertThat(rect instanceof Square).isTrue();
        assertThat(rect.area()).isEqualTo(400);
    }

    @Test
    @DisplayName("Change square width")
    void test4() {
        Rectangle rect = new Square(10);
        assertThat(rect instanceof Square).isTrue();
        assertThat(rect.area()).isEqualTo(100);

        rect = rect.setWidth(20);
        assertThat(rect instanceof Square).isFalse();
        assertThat(rect.area()).isEqualTo(200);
    }


    @Test
    @DisplayName("Change square height")
    void test5() {
        Rectangle rect = new Square(10);
        assertThat(rect instanceof Square).isTrue();
        assertThat(rect.area()).isEqualTo(100);

        rect = rect.setHeight(20);
        assertThat(rect instanceof Square).isFalse();
        assertThat(rect.area()).isEqualTo(200);
    }

    @ParameterizedTest
    @MethodSource("rectangles")
    void rectangleArea(Rectangle rect) {
        rect = rect.setWidth(20);
        rect = rect.setHeight(10);

        assertThat(rect.area()).isEqualTo(200.0);
    }

    private static Arguments[] rectangles() {
        return new Arguments[]{
            Arguments.of(new Rectangle()),
            Arguments.of(new Square())
        };
    }
}
