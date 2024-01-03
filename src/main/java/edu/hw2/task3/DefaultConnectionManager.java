package edu.hw2.task3;

import java.security.SecureRandom;

public class DefaultConnectionManager implements ConnectionManager {
    @Override
    @SuppressWarnings("MagicNumber")
    public Connection getConnection() {
        if (RANDOM.nextInt(0, 3) == 0) {
            return new FaultyConnection();
        }
        return new StableConnection();
    }

    private static final SecureRandom RANDOM = new SecureRandom();
}
