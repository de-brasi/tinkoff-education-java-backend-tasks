package edu.hw11;

import edu.hw11.util.ArithmeticUtils;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Task2AndTest {
    @Test
    public void test() {
        assertThat(ArithmeticUtils.sum(1, 2)).isEqualTo(3);

        RuntimeMethodInterceptor.interceptMethod(
            ArithmeticUtils.class,
            ArithmeticUtilsInterceptor.class,
            "sum"
        );

        assertThat(ArithmeticUtils.sum(1, 2)).isEqualTo(2);
    }

    public static class ArithmeticUtilsInterceptor {
        public static int multiply(int a, int b) {
            return a * b;
        }
    }
}
