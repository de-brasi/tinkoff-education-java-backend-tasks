package edu.hw7;

import java.util.concurrent.atomic.AtomicInteger;

public class Task1 {
    private Task1() {}

    public static int getCounter() {
        return counter.get();
    }

    public static void setCounter(int counter) {
        Task1.counter.set(counter);
    }

    public static void increment() {
        Task1.counter.incrementAndGet();
    }

    public static AtomicInteger counter = new AtomicInteger(0);
}
