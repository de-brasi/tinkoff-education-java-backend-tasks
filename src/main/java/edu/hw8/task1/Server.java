package edu.hw8.task1;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Server {
    public Server() {}

    public void Run(int threadCount) {
        try (
            ServerSocket server = new ServerSocket(PORT);
            ExecutorService threadPool = Executors.newFixedThreadPool(threadCount)
        ) {

            Lock lock = new ReentrantLock();
            Condition freeExecutorsExists = lock.newCondition();

            server.setSoTimeout(1_000);
            Socket socket;

            while (true) {

                // TODO: discard busy waiting
                while (tasksCount.get() >= threadCount) {
                    // busy waiting
                    Thread.sleep(100);
                }

                socket = server.accept();
                LOGGER.info("Server find free executor!");
                threadPool.submit(new ServerRoutine(socket));
                tasksCount.incrementAndGet();
            }
        } catch (IOException ioException) {
            LOGGER.info("Error: " + ioException.getMessage());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private final AtomicInteger tasksCount = new AtomicInteger(0);
    private final NonSynchronizedCitationBase citationBase = new NonSynchronizedCitationBase();
    private final int PORT = 18080;
    private final static Logger LOGGER = LogManager.getLogger();

    private class ServerRoutine implements Runnable {
        ServerRoutine(Socket socket) {
            clientSocket = socket;
        }

        @Override
        public void run() {
            LOGGER.info(
                "Timestamp=%s. Start handling client in thread with id=%s".formatted(
                    System.nanoTime() / 1_000_000_000,
                    Thread.currentThread().threadId())
            );

            try {
                try(
                    var reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    var writer = new PrintWriter(clientSocket.getOutputStream(), true)
                ) {
                    var response = reader.readLine();
                    writer.println(citationBase.getCitation(response));
                }

                // Long processing imitation (2 seconds)
                Thread.sleep(2_000);

                tasksCount.decrementAndGet();
                clientSocket.close();
            } catch (InterruptedException | IOException e) {
                LOGGER.info(e.getMessage());
            }

            try {
                clientSocket.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        private final Socket clientSocket;
    }
}
