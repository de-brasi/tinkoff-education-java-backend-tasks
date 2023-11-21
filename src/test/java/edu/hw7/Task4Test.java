package edu.hw7;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class Task4Test {
    @Test
    @DisplayName("Single thread solution evaluation")
    public void test1() {
        var solver = new PiMonteCarloSingleThreadSolver();

        var pi10_000Simulations = solver.calculatePiValue(10_000);
        var pi100_000Simulations = solver.calculatePiValue(100_000);
        var pi1_000_000Simulations = solver.calculatePiValue(1_000_000);
        var pi10_000_000Simulations = solver.calculatePiValue(10_000_000);

        LOGGER.info(
            "Single thread solution; "
                + "Simulations count=10_000; "
                + "PI value=" + pi10_000Simulations + "; "
                + "Relative bias (%)=" + getRelativeBias(pi10_000Simulations, PI)
        );
        LOGGER.info(
            "Single thread solution; "
                + "Simulations count=100_000; "
                + "PI value=" + pi100_000Simulations + "; "
                + "Relative bias (%)=" + getRelativeBias(pi100_000Simulations, PI)
        );
        LOGGER.info(
            "Single thread solution; "
                + "Simulations count=1_000_000; "
                + "PI value=" + pi1_000_000Simulations + "; "
                + "Relative bias (%)=" + getRelativeBias(pi1_000_000Simulations, PI)
        );
        LOGGER.info(
            "Single thread solution; "
                + "Simulations count=10_000_000; "
                + "PI value=" + pi10_000_000Simulations + "; "
                + "Relative bias (%)=" + getRelativeBias(pi10_000_000Simulations, PI)
        );
    }

    @Test
    @DisplayName("Two threads solution evaluation")
    public void test2() {
        var solver = new PiMonteCarloMultiThreadSolver();

        final int threadsCount = 2;

        var pi10_000Simulations = solver.calculatePiValue(10_000, threadsCount);
        var pi100_000Simulations = solver.calculatePiValue(100_000, threadsCount);
        var pi1_000_000Simulations = solver.calculatePiValue(1_000_000, threadsCount);
        var pi10_000_000Simulations = solver.calculatePiValue(10_000_000, threadsCount);

        LOGGER.info(
            "2 threads solution; "
                + "Simulations count=10_000; "
                + "PI value=" + pi10_000Simulations + "; "
                + "Relative bias (%)=" + getRelativeBias(pi10_000Simulations, PI)
        );
        LOGGER.info(
            "2 threads solution; "
                + "Simulations count=100_000; "
                + "PI value=" + pi100_000Simulations + "; "
                + "Relative bias (%)=" + getRelativeBias(pi100_000Simulations, PI)
        );
        LOGGER.info(
            "2 threads solution; "
                + "Simulations count=1_000_000; "
                + "PI value=" + pi1_000_000Simulations + "; "
                + "Relative bias (%)=" + getRelativeBias(pi1_000_000Simulations, PI)
        );
        LOGGER.info(
            "2 threads solution; "
                + "Simulations count=10_000_000; "
                + "PI value=" + pi10_000_000Simulations + "; "
                + "Relative bias (%)=" + getRelativeBias(pi10_000_000Simulations, PI)
        );
    }

    @Test
    @DisplayName("Ten threads solution evaluation")
    public void test3() {
        var solver = new PiMonteCarloMultiThreadSolver();

        final int threadsCount = 10;

        var pi10_000Simulations = solver.calculatePiValue(10_000, threadsCount);
        var pi100_000Simulations = solver.calculatePiValue(100_000, threadsCount);
        var pi1_000_000Simulations = solver.calculatePiValue(1_000_000, threadsCount);
        var pi10_000_000Simulations = solver.calculatePiValue(10_000_000, threadsCount);

        LOGGER.info(
            "10 threads solution; "
                + "Simulations count=10_000; "
                + "PI value=" + pi10_000Simulations + "; "
                + "Relative bias (%)=" + getRelativeBias(pi10_000Simulations, PI)
        );
        LOGGER.info(
            "10 threads solution; "
                + "Simulations count=100_000; "
                + "PI value=" + pi100_000Simulations + "; "
                + "Relative bias (%)=" + getRelativeBias(pi100_000Simulations, PI)
        );
        LOGGER.info(
            "10 threads solution; "
                + "Simulations count=1_000_000; "
                + "PI value=" + pi1_000_000Simulations + "; "
                + "Relative bias (%)=" + getRelativeBias(pi1_000_000Simulations, PI)
        );
        LOGGER.info(
            "10 threads solution; "
                + "Simulations count=10_000_000; "
                + "PI value=" + pi10_000_000Simulations + "; "
                + "Relative bias (%)=" + getRelativeBias(pi10_000_000Simulations, PI)
        );
    }

    @Test
    @DisplayName("Different threads count work time comparison (10_000_000 iterations)")
    public void test4() {
        var solver = new PiMonteCarloMultiThreadSolver();
        final int iterationCount = 10_000_000;

        for (int i = 1; i <= 10; i++) {
            var startTime = System.nanoTime();
            solver.calculatePiValue(iterationCount, i);
            var durationNanoSeconds = System.nanoTime() - startTime;
            LOGGER.info(
                "Thread counts="+ i +";"
                    + "\tIteration counts=" + iterationCount + ";"
                    + "\tDuration in seconds=" + durationNanoSeconds/NANOSECONDS_PER_SECOND + ";"
            );
        }
    }

    @Test
    @DisplayName("Different threads count work time comparison (1_000_000_000 iterations)")
    public void test5() {
        var solver = new PiMonteCarloMultiThreadSolver();
        final int iterationCount = 1_000_000_000;

        for (int i = 1; i <= 8; i++) {
            var startTime = System.nanoTime();
            solver.calculatePiValue(iterationCount, i);
            var durationNanoSeconds = System.nanoTime() - startTime;
            LOGGER.info(
                "Thread counts="+ i +";"
                    + "\tIteration counts=" + iterationCount + ";"
                    + "\tDuration in seconds=" + durationNanoSeconds/NANOSECONDS_PER_SECOND + ";"
            );
        }
    }

    @Test
    @DisplayName("Average efficiency gain (from 1 to 8 threads; count of threads less or equal CPU core count)")
    public void test6() {
        var solver = new PiMonteCarloMultiThreadSolver();
        final int iterationCount = 1_000_000_000;
        final int maxThreadCount = 8;

        long[] timings = new long[maxThreadCount];

        for (int i = 1; i <= maxThreadCount; i++) {
            var startTime = System.nanoTime();
            solver.calculatePiValue(iterationCount, i);
            var durationNanoSeconds = System.nanoTime() - startTime;
            timings[i - 1] = durationNanoSeconds;
        }

        long commonTimingDifference = 0;
        for (int i = 1; i < timings.length; i++) {
            commonTimingDifference += (timings[i - 1] - timings[i]);
        }

        LOGGER.info(
            "Average efficiency gain per thread=" +
            commonTimingDifference / (timings.length - 1) / NANOSECONDS_PER_SECOND
            + " seconds"
        );
    }

    private static float getRelativeBias(float experimentValue, float realValue) {
        return Math.abs(experimentValue - realValue) / realValue * 100;
    }

    private final static Logger LOGGER = LogManager.getLogger();
    private final float PI = (float) 3.14159265359;
    private final int NANOSECONDS_PER_SECOND = 1_000_000_000;
}
