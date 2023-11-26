package edu.hw8.task1;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public Server() {}

    public void Run() {
        try (ServerSocket server = new ServerSocket(PORT)) {

            Socket socket;

            while (true) {
                LOGGER.info("Server: wait for client");
                socket = server.accept();
                LOGGER.info("Server: client got");

                try(
                    var reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    var writer = new PrintWriter(socket.getOutputStream(), true)
                ) {
                    var response = reader.readLine();
                    writer.println(citationBase.getCitation(response));
                }

                try {
                    Thread.sleep(1_000);
                } catch (InterruptedException e) {
                    LOGGER.info(e.getMessage());
                }

                socket.close();
                LOGGER.info("\n");
                LOGGER.info("Server: client handled");
            }
        } catch (IOException ioException) {
            LOGGER.info("Error: " + ioException.getMessage());
        }
    }

    private final NonSynchronizedCitationBase citationBase = new NonSynchronizedCitationBase();
    private final int PORT = 18080;
    private final static Logger LOGGER = LogManager.getLogger();
}
