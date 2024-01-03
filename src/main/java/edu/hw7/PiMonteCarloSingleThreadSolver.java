// Task 4

package edu.hw7;

import edu.hw7.util.Coordinate;
import java.security.SecureRandom;
import java.util.Random;

public class PiMonteCarloSingleThreadSolver {
    public float calculatePiValue(int iterationCount) {
        assert iterationCount > 0;

        Coordinate newDot;

        for (int i = 0; i < iterationCount; i++) {
            newDot = new Coordinate(
                rand.nextFloat(2 * RADIUS),
                rand.nextFloat(2 * RADIUS)
            );

            if (coordinateInCircle(newDot)) {
                ++circleCount;
            }
            ++totalCount;
        }

        return getPiValue();
    }

    private static boolean coordinateInCircle(Coordinate toCheck) {
        return Math.pow((toCheck.x() - CIRCLE_CENTRE.x()), 2)
            + Math.pow((toCheck.y() - CIRCLE_CENTRE.y()), 2)
            <= Math.pow(RADIUS, 2);
    }

    @SuppressWarnings("MagicNumber")
    private float getPiValue() {
        assert totalCount != 0;
        return (4 * (float) circleCount) / totalCount;
    }

    private int totalCount = 0;
    private int circleCount = 0;

    private final Random rand = new SecureRandom();
    private static final int RADIUS = 1;
    private static final Coordinate CIRCLE_CENTRE =
        new Coordinate(
            (float) RADIUS,
            (float) RADIUS
        );
}
