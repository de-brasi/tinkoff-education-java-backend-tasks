package edu.hw7;

import edu.hw7.util.Coordinate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.LongAdder;

public class PiMonteCarloMultiThreadSolver {
    public float calculatePiValue(int iterationCount, int threadCount) {
        assert threadCount > 0;
        assert iterationCount > 0;

        int iterationsPerThread = iterationCount / threadCount;

        List<Thread> threadPool = new ArrayList<>();

        for (int i = 0; i < threadCount; i++) {
            threadPool.add(new Thread(
                new WorkerRoutine(iterationsPerThread)
            ));
            threadPool.getLast().start();
        }

        for (var worker : threadPool) {
            try {
                worker.join();
            } catch (InterruptedException interruptedException) {
                throw new RuntimeException("InterruptedException in worker thread got");
            }
        }

        return getPiValue();
    }

    @SuppressWarnings("MagicNumber")
    private float getPiValue() {
        assert totalCount.intValue() != 0;
        return (4 * (float) circleCount.intValue()) / totalCount.intValue();
    }

    private final LongAdder totalCount = new LongAdder();
    private final LongAdder circleCount = new LongAdder();
    private static final int RADIUS = 1;
    private static final Coordinate CIRCLE_CENTRE =
        new Coordinate(
            (float) RADIUS,
            (float) RADIUS
        );

    private class WorkerRoutine implements Runnable {
        WorkerRoutine(int iterationCount) {
            iterations = iterationCount;
        }

        @Override
        public void run() {
            Coordinate newDot;

            for (int i = 0; i < this.iterations; i++) {
                newDot = new Coordinate(
                    ThreadLocalRandom.current().nextFloat(2 * RADIUS),
                    ThreadLocalRandom.current().nextFloat(2 * RADIUS)
                );

                if (coordinateInCircle(newDot)) {
                    circleCount.increment();
                }
                totalCount.increment();
            }
        }

        private static boolean coordinateInCircle(Coordinate toCheck) {
            return Math.pow((toCheck.x() - CIRCLE_CENTRE.x()), 2)
                + Math.pow((toCheck.y() - CIRCLE_CENTRE.y()), 2)
                <= Math.pow(RADIUS, 2);
        }

        private final int iterations;
    }
}
