package edu.hw8.task1;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class Client {
    public Client() {}

    public void Run() {
        try {
            Socket client = new Socket(InetAddress.getByName("localhost"), 18080);

            var writer = new PrintWriter(client.getOutputStream(), true);
            var reader = new BufferedReader(new InputStreamReader(client.getInputStream()));

            writer.println("личности");
            var res = reader.readLine();

            LOGGER.info("Answer is: " + res);

            client.close();
        } catch (IOException e) {
            LOGGER.info(e.getMessage());
        }
    }

    public static void main(String[] args) {
        try {
            Socket client = new Socket(InetAddress.getByName("localhost"), 18080);

            var writer = new PrintWriter(client.getOutputStream(), true);
            var reader = new BufferedReader(new InputStreamReader(client.getInputStream()));

            writer.println("абоба");
            var res = reader.readLine();

            LOGGER.info("Answer is: " + res);

            client.close();
        } catch (IOException e) {
            LOGGER.info("Error: " + e.getMessage());
        }
    }

    private final static Logger LOGGER = LogManager.getLogger();
}
