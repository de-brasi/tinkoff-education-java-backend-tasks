package edu.hw2.task3;

public final class PopularCommandExecutor {
    public PopularCommandExecutor(int attemptsCount, ConnectionManagerType type) {
        maxAttempts = attemptsCount;

        manager = switch (type) {
            case FAULTY -> new FaultyConnectionManager();
            case DEFAULT -> new DefaultConnectionManager();
        };
    }

    private final ConnectionManager manager;
    private final int maxAttempts;

    public void updatePackages() {
        tryExecute("apt update && apt upgrade -y");
    }

    void tryExecute(String command) {
        Exception lastException = null;
        for (int i = 0; i < maxAttempts; i++) {
            try (Connection connectionAttempt = manager.getConnection()) {
                connectionAttempt.execute(command);
                connectionAttempt.close();
                return;
            } catch (Exception exception) {
                lastException = exception;
            }
        }
        throw new ConnectionException(lastException);
    }
}
