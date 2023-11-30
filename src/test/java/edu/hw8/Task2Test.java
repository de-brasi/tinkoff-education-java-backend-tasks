package edu.hw8;

import edu.hw8.task2.FixedThreadPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.fail;

public class Task2Test {
    @Test
    @DisplayName("Comparing 2 small Fibonacci numbers counting time in one thread and 2-threads ThreadPool")
    public void test1() {
        final int numberToCount1 = 30;
        final int numberToCount2 = 35;
        long consistentComputingTime;
        long parallelComputingTime;
        long startTime;
        final var threadPool = FixedThreadPool.create(2);

        startTime = System.nanoTime();
        new FibonacciCounter(numberToCount1).run();
        new FibonacciCounter(numberToCount2).run();
        consistentComputingTime = System.nanoTime() - startTime;

        startTime = System.nanoTime();
        threadPool.execute(new FibonacciCounter(numberToCount1));
        threadPool.execute(new FibonacciCounter(numberToCount2));
        threadPool.start();
        threadPool.joinScheduled();
        parallelComputingTime = System.nanoTime() - startTime;

        try {
            threadPool.close();
        } catch (Exception e) {
            LOGGER.info("Exception when closing threadPool: " + e);
        }

        assertThat(parallelComputingTime).isLessThan(consistentComputingTime);
    }

    @Test
    @DisplayName("Comparing 4 small Fibonacci numbers counting time in one thread and 2-threads ThreadPool")
    public void test2() {
        final int numberToCount1 = 33;
        final int numberToCount2 = 34;
        final int numberToCount3 = 35;
        final int numberToCount4 = 36;
        long consistentComputingTime;
        long parallelComputingTime;
        long startTime;
        final var threadPool = FixedThreadPool.create(2);

        startTime = System.nanoTime();
        new FibonacciCounter(numberToCount1).run();
        new FibonacciCounter(numberToCount2).run();
        new FibonacciCounter(numberToCount3).run();
        new FibonacciCounter(numberToCount4).run();
        consistentComputingTime = System.nanoTime() - startTime;

        startTime = System.nanoTime();
        threadPool.execute(new FibonacciCounter(numberToCount1));
        threadPool.execute(new FibonacciCounter(numberToCount2));
        threadPool.execute(new FibonacciCounter(numberToCount3));
        threadPool.execute(new FibonacciCounter(numberToCount4));
        threadPool.start();
        threadPool.joinScheduled();
        parallelComputingTime = System.nanoTime() - startTime;

        try {
            threadPool.close();
        } catch (Exception e) {
            LOGGER.info("Exception when closing threadPool: " + e);
        }

        assertThat(parallelComputingTime).isLessThan(consistentComputingTime);
    }

    @Test
    @DisplayName("Comparing small 10 Fibonacci numbers counting time in one thread and 3-threads ThreadPool")
    public void test3() {
        final int[] numbersToCount = {30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40};
        long consistentComputingTime;
        long parallelComputingTime;
        long startTime;
        final var threadPool = FixedThreadPool.create(3);

        startTime = System.nanoTime();
        for (var toCount: numbersToCount) {
            new FibonacciCounter(toCount).run();
        }
        consistentComputingTime = System.nanoTime() - startTime;

        startTime = System.nanoTime();
        for (var toCount: numbersToCount) {
            threadPool.execute(new FibonacciCounter(toCount));
        }
        threadPool.start();
        threadPool.joinScheduled();
        parallelComputingTime = System.nanoTime() - startTime;

        try {
            threadPool.close();
        } catch (Exception e) {
            LOGGER.info("Exception when closing threadPool: " + e);
        }

        assertThat(parallelComputingTime).isLessThan(consistentComputingTime);
    }

    @Test
    @DisplayName("Closing ThreadPool when running")
    public void test4() throws InterruptedException {
        final var threadPool = FixedThreadPool.create(2);
        final int[] toManyRoutineArguments = {
            30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40,
            30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40,
            30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40,
            30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40,
            30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40,
            30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40,
            30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40,
            30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40,
            30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40,
            30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40
        };
        long startTime = System.nanoTime();

        for (var toCount: toManyRoutineArguments) {
            threadPool.execute(new FibonacciCounter(toCount));
        }

        threadPool.start();
        Thread.sleep(500);

        try {
            threadPool.close();
        } catch (Exception e) {
            fail("Unexpected exception: " + e);
        }

        assertThat(System.nanoTime() - startTime).isLessThan(1_000_000_000);
    }

    private static class FibonacciCounter implements Runnable {
        FibonacciCounter(int toCount) {
            fibonacci = toCount;
        }

        @Override
        public void run() {
            getFibonacci(fibonacci);
        }

        private long getFibonacci(int toCount) {
            if (toCount == 1) {
                return 0;
            } else if (toCount == 2) {
                return 1;
            }

            return getFibonacci(toCount - 1) + getFibonacci(toCount - 2);
        }

        private final int fibonacci;
    }

    private final static Logger LOGGER = LogManager.getLogger();
}
