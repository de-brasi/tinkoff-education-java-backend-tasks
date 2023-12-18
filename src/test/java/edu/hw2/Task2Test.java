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
        var rect = new Square(10);
        assertThat(rect.area()).isEqualTo(100);
    }

    @Test
    @DisplayName("Changing side value")
    void test3() {
        Rectangle square = new Square(10);
        square.setHeight(2);
        assertThat(square.area()).isEqualTo(4);
    }

    @ParameterizedTest
    @MethodSource("rectangles")
    void rectangleArea(Rectangle rect) {
        rect.setWidth(20);
        rect.setHeight(10);

        if (rect instanceof Square) {
            assertThat(rect.area()).isEqualTo(100.0);
        } else {
            assertThat(rect.area()).isEqualTo(200.0);
        }
    }

    private static Arguments[] rectangles() {
        return new Arguments[]{
            Arguments.of(new Rectangle()),
            Arguments.of(new Square())
        };
    }
}
