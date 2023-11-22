package edu.hw2.task3;

public class FaultyConnection implements Connection {
    @Override
    public void execute(String command) {
        throw new ConnectionException();
    }

    @Override
    public void close() throws ConnectionException {}
}
