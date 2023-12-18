package edu.hw11.util;

public class FibExample {
    public static long fib(int n) {
        long fib1 = 0;
        long fib2 = 1;
        long fibSum;

        if (n == 1) {
            return fib1;
        } else if (n == 2) {
            return fib2;
        }

        int i = 0;
        while (i < (n - 2)) {
            fibSum = fib1 + fib2;
            fib1 = fib2;
            fib2 = fibSum;
            i++;
        }

        return fib2;
    }

    public static void main(String[] args) {
        for (int i = 1; i < 10; i++) {
            System.out.println(fib(i));
        }
    }
}
