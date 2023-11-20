package edu.hw7;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.math.BigInteger;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Task2Test {
    @Test
    @DisplayName("0!")
    public void test1() {
        assertThat(Task2.getFactorial(0)).isEqualTo(1);
    }

    @Test
    @DisplayName("1!")
    public void test2() {
        assertThat(Task2.getFactorial(1)).isEqualTo(1);
    }

    @Test
    @DisplayName("10!")
    public void test3() {
        final int factorialArgument = 10;
        int expectedRes = 1;
        for (int i = 1; i < factorialArgument + 1; i++) {
            expectedRes *= i;
        }
        assertThat(Task2.getFactorial(factorialArgument)).isEqualTo(expectedRes);
    }

    @Test
    @DisplayName("time comparison for big value")
    public void test4() {
        final int factorialArgument = 100000;

        var begin = System.nanoTime();
        BigInteger dummyFactorial = BigInteger.ONE;
        for (int i = 1; i < factorialArgument + 1; i++) {
            dummyFactorial = dummyFactorial.multiply(BigInteger.valueOf(i));
        }
        final var dummyDuration = System.nanoTime() - begin;

        begin = System.nanoTime();
        Task2.getFactorial(factorialArgument);
        var parallelDuration = System.nanoTime() - begin;

        assertThat(parallelDuration).isLessThan(dummyDuration);
    }
}
