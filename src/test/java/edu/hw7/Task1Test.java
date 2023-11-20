package edu.hw7;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Task1Test {
    @Test
    public void singleThreadIncrement() {
        Task1.setCounter(0);
        final int toIncrementValue = 10;

        for (int i = 0; i < toIncrementValue; i++) {
            Task1.increment();
        }

        assertThat(Task1.getCounter()).isEqualTo(toIncrementValue);
    }

    @Test
    public void multipleThreadsIncrement() throws InterruptedException {
        Task1.setCounter(0);
        final int toIncrementValue = 1000;
        final int threadCount = 5;

        ArrayList<Thread> threads = new ArrayList<>();
        for (int i = 0; i < threadCount; i++) {
            threads.add(
                new Thread(
                    () -> {
                        for (int j = 0; j < toIncrementValue; j++) {
                            Task1.increment();
                        }
                    }
                )
            );
            threads.getLast().start();
        }

        for (int i = 0; i < threadCount; i++) {
            threads.get(i).join();
        }

        assertThat(Task1.getCounter()).isEqualTo(toIncrementValue * threadCount);
    }
}
