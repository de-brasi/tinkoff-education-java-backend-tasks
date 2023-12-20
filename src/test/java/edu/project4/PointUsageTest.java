package edu.project4;

import edu.project4.utils.Point;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class PointUsageTest {
    @Test
    @DisplayName("Test coefficients applying")
    public void test() {
        var beforeCoefficientsApplying = new Point(1, 1);

        //  x       3 0
        //  -   *   ---
        //  y       0 2

        double a = 3;
        double b = 0;
        double c = 0;
        double d = 0;
        double e = 2;
        double f = 0;

        var afterCoefficientsApplying = new Point(3, 2);

        assertThat(beforeCoefficientsApplying.withCoefficients(a, b, c, d, e, f)).isEqualTo(afterCoefficientsApplying);
    }
}
