package edu.hw8.task1;

import edu.hw8.task1.NonSynchronizedCitationBase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public Server() {

    }

    public void Run() {
        try (ServerSocket server = new ServerSocket(18080)) {

            Socket socket;

            while (true) {
                socket = server.accept();

                try(
                    var reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    var writer = new PrintWriter(socket.getOutputStream(), true)
                ) {
                    var response = reader.readLine();
                    writer.println(citationBase.getCitation(response));
                }

                socket.close();
            }
        } catch (IOException ioException) {
            LOGGER.info("Error: " + ioException.getMessage());
        }
    }

    private final static NonSynchronizedCitationBase citationBase = new NonSynchronizedCitationBase();
    private final static Logger LOGGER = LogManager.getLogger();
}
