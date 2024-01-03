package edu.hw8.task1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Server {
    public Server() {}

    @SuppressWarnings("MagicNumber")
    public void run(int threadCount) {
        // TODO:
        //  Как лучше всего завершать работу серверного потока, по таймауту?
        //  Насколько приемлема текущая реализация?
        try (
            ServerSocket server = new ServerSocket(port);
            ExecutorService threadPool = Executors.newFixedThreadPool(threadCount)
        ) {
            server.setSoTimeout(1_000);
            Socket socket;

            while (true) {

                try {
                    lock.lock();
                    while (tasksCount.get() >= threadCount) {
                        freeExecutorsExists.await();
                    }

                    socket = server.accept();
                    LOGGER.info("Server find free executor!");
                    threadPool.submit(new ServerRoutine(socket));
                    tasksCount.incrementAndGet();
                } finally {
                    lock.unlock();
                }
            }
        } catch (IOException ioException) {
            LOGGER.info("Error: " + ioException.getMessage());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private final AtomicInteger tasksCount = new AtomicInteger(0);
    Lock lock = new ReentrantLock();
    Condition freeExecutorsExists = lock.newCondition();
    private final NonSynchronizedCitationBase citationBase = new NonSynchronizedCitationBase();
    private final int port = 18080;
    private final static Logger LOGGER = LogManager.getLogger();

    private class ServerRoutine implements Runnable {
        ServerRoutine(Socket socket) {
            clientSocket = socket;
        }

        @Override
        @SuppressWarnings("MagicNumber")
        public void run() {
            // TODO:
            //  Не уверен, что в главном потоке при ожидании готовых к обслуживанию нового клиента потоков
            //      спать на кондваре в ожидании нотификации от потоков-воркеров - хорошая идея.
            //  Я вижу такую проблему - параллельно завершающие работу потоки-воркеры должны ожидать "коллег"
            //      при синхронизации на lock, то есть будет простой на потоках в тред-пуле
            //      (причем наверное существенный, если в тред-пуле много воркеров).
            //  Однако как по-другому реализовать ожидание свободных потоков в тред-пуле,
            //      минуя эту проблему и не прибегая к busy-waiting в главном потоке сервера - не знаю :(
            //  Буду благодарен за обратную связь по текущей реализации
            //      и за рекомендацию как разрешить эту проблему оптимально!

            LOGGER.info(
                "Timestamp=%s. Start handling client in thread with id=%s".formatted(
                    System.nanoTime() / 1_000_000_000,
                    Thread.currentThread().threadId())
            );

            try {
                try (
                    var reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    var writer = new PrintWriter(clientSocket.getOutputStream(), true)
                ) {
                    var response = reader.readLine();
                    writer.println(citationBase.getCitation(response));
                }

                // Long processing imitation (2 seconds)
                Thread.sleep(2_000);

                tasksCount.decrementAndGet();

                try {
                    lock.lock();
                    freeExecutorsExists.signal();
                } finally {
                    lock.unlock();
                }
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
