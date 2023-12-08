package edu.hw2;

import edu.hw2.task2.Rectangle;
import edu.hw2.task2.Square;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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
        Rectangle rectangleLike = new Square(10);
        var changedSide = rectangleLike.setHeight(2);
        assertThat(changedSide.area()).isEqualTo(4);
    }
}
