package edu.hw8.task1;

import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Testing {
    private Testing() {}

    @SuppressWarnings("UncommentedMain")
    public static void main(String[] args) {
        var serverThread = new Thread(
            () -> {
                var server = new Server();
                server.run(2);
            }
        );
        serverThread.start();

        final int testClientsCount = 5;
        var clientTasks = Stream.generate(
            () -> CompletableFuture.runAsync(
                () -> {
                    var client = new Client();
                    client.run();
                }
            )
        ).limit(testClientsCount).toArray(CompletableFuture[]::new);

        CompletableFuture.allOf(clientTasks).join();
        LOGGER.info("joined");

        try {
            serverThread.join();
        } catch (InterruptedException e) {
            LOGGER.info(e.getMessage());
        }
    }

    private final static Logger LOGGER = LogManager.getLogger();
}
