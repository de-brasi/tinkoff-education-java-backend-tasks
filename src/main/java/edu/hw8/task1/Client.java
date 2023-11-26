package edu.hw8.task1;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.concurrent.TimeoutException;

public class Client {
    public Client() {}

    public void Run() {
        try {
            Socket client = tryMakeClientSocketInLoop(CONNECTION_TIMEOUT_MILLISECONDS);
            LOGGER.info("Client: port created");

            var writer = new PrintWriter(client.getOutputStream(), true);
            var reader = new BufferedReader(new InputStreamReader(client.getInputStream()));

            writer.println("личности");
            var res = reader.readLine();

            LOGGER.info("Answer is: " + res);

            client.close();
        } catch (IOException e) {
            LOGGER.info("Error: " + e.getMessage());
        } catch (TimeoutException timeoutException) {
            LOGGER.info(timeoutException.getMessage());
        }
    }

    private Socket tryMakeClientSocketInLoop(int timeoutMillis) throws TimeoutException {
        var startTime = System.nanoTime();
        while (System.nanoTime() - startTime <= timeoutMillis * 1_000_000L) {
            try {
                return new Socket(InetAddress.getByName(HOST), PORT);
            } catch (IOException ignored) {}
        }
        throw new TimeoutException("Can't connect to server with host=%s; port=%s".formatted(HOST, PORT));
    }

    private final static Logger LOGGER = LogManager.getLogger();
    private final String HOST = "localhost";
    private final int PORT = 18080;
    private final static int CONNECTION_TIMEOUT_MILLISECONDS = 100;
}
