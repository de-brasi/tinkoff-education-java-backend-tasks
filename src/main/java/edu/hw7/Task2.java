package edu.hw7;

import java.math.BigInteger;
import java.util.stream.Stream;

public class Task2 {
    private Task2() {}

    public static BigInteger getFactorial(int num) {
        if (num < 0) {
            throw new IllegalArgumentException("Illegal argument got for factorial computing");
        }
        return Stream
            .iterate(BigInteger.ONE, i -> i.add(BigInteger.ONE))
            .limit(num)
            .toList()
            .parallelStream()
            .reduce(BigInteger.ONE, BigInteger::multiply);
    }
}
