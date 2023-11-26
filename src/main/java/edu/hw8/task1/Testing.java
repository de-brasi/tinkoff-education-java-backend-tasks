package edu.hw8.task1;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

public class Testing {
    public static void main(String[] args) {
        // Server
        var serverRunFuture = CompletableFuture.runAsync(
            () -> {
                var server = new Server();
                server.Run();
            }
        );

        var clientTasks = Stream.generate(
            () -> CompletableFuture.runAsync(
                () -> {
                    var client = new Client();
                    client.Run();
                }
            )
        ).limit(5).toArray(CompletableFuture[]::new);

        CompletableFuture.allOf(clientTasks).join();
        serverRunFuture.cancel(true);
    }

    private final static Logger LOGGER = LogManager.getLogger();
}
