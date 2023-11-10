package edu.hw3;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Task6Test {
    @Test
    @DisplayName("Тест. Пустая очередь")
    public void test1() {
        final var queue = new Task6.Market();

        assertThat(queue.mostValuableStock()).isEqualTo(null);
    }

    @Test
    @DisplayName("Тест. Проверка приоритета")
    public void test2() {
        final var queue = new Task6.Market();

        queue.add(
            new Stock(0, "Newborn startup")
        );

        queue.add(
            new Stock(10, "Some Green Bank")
        );

        queue.add(
            new Stock(20, "Some Yellow Bank")
        );

        queue.add(
            new Stock(30, "Another Yellow Bank")
        );

        queue.add(
            new Stock(-1, "loan from a neighbor")
        );

        queue.remove(new Stock(30, "Not Existing Yellow Bank"));

        assertThat(queue.mostValuableStock().getPrice()).isEqualTo(30);
        queue.remove(new Stock(30, "Another Yellow Bank"));

        assertThat(queue.mostValuableStock().getPrice()).isEqualTo(20);
        queue.remove(new Stock(20, "Some Yellow Bank"));

        assertThat(queue.mostValuableStock().getPrice()).isEqualTo(10);
        queue.remove(new Stock(10, "Some Green Bank"));

        assertThat(queue.mostValuableStock().getPrice()).isEqualTo(0);
        queue.remove(new Stock(0, "Newborn startup"));

        assertThat(queue.mostValuableStock().getPrice()).isEqualTo(-1);
        queue.remove(new Stock(-1, "loan from a neighbor"));
    }
}
