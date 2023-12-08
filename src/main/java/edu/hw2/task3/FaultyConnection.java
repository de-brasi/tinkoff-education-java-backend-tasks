package edu.hw2.task3;

import java.util.Random;

public class FaultyConnection implements Connection {
    @Override
    public void execute(String command) {
        if (random.nextInt() % 10 == 0) {
            throw new ConnectionException();
        }
    }

    @Override
    public void close() throws ConnectionException {}

    private final Random random = new Random();
}
