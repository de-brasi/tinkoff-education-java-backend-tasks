package edu.hw8.task2;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Stream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FixedThreadPool implements ThreadPool {
    public static FixedThreadPool create(int nThreads) {
        return new FixedThreadPool(nThreads);
    }

    public FixedThreadPool(int nThreads) {
        workers = Stream
            .generate(
                () -> new Thread(
                    new WorkerRoutine()
                )
            )
            .limit(nThreads)
            .toArray(Thread[]::new);
    }

    @Override
    public void start() {
        for (Thread worker : workers) {
            worker.start();
        }
    }

    @Override
    public void execute(Runnable runnable) {
        lock.lock();
        try {
            scheduledTasks.add(runnable);
            scheduledTasksExists.signal();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void close() {
        lock.lock();

        try {
            for (Thread worker : workers) {
                worker.interrupt();
            }
            scheduledTasks.clear();
            scheduledTasksExists.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public void joinScheduled() {
        lock.lock();
        try {
            while (
                !scheduledTasks.isEmpty()
                    || !Arrays.stream(workers).allMatch(thread -> thread.getState() == Thread.State.WAITING)) {
                allWorkersWaiting.await();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

    private final Thread[] workers;
    private final Deque<Runnable> scheduledTasks = new ArrayDeque<>();
    private final Lock lock = new ReentrantLock();
    private final Condition scheduledTasksExists = lock.newCondition();
    private final Condition allWorkersWaiting = lock.newCondition();

    private final static Logger LOGGER = LogManager.getLogger();

    private class WorkerRoutine implements Runnable {
        @Override
        public void run() {
            // TODO:
            //  Я не уверен, что правильно обрабатываю ошибку прерывания после блока try
            //      с получением задачи из коллекции.
            //  Как я понял InterruptedException при вызове await может произойти, когда другой поток прерывает спящего
            //  и будит его с поднятием исключения.
            //  Это так?
            //  То есть игнорирование этого исключения не нарушит гарантии ThreadPool
            //  на выполнение всех задач и не разрушит синхронизацию?

            Runnable newTask = null;

            while (!Thread.currentThread().isInterrupted()) {
                lock.lock();
                try {
                    while (!Thread.currentThread().isInterrupted() && scheduledTasks.isEmpty()) {
                        allWorkersWaiting.signal();
                        LOGGER.info("Worker in thread %s sleep".formatted(Thread.currentThread().threadId()));
                        scheduledTasksExists.await();
                    }
                    newTask = scheduledTasks.pollLast();
                } catch (InterruptedException ignore) {
                } finally {
                    lock.unlock();
                }

                if (newTask != null) {
                    newTask.run();
                    LOGGER.info("WorkerRoutine in thread %s finished".formatted(Thread.currentThread().threadId()));
                } else if (!Thread.currentThread().isInterrupted()) {
                    throw new RuntimeException("Error when task receipt synchronized");
                }
            }

        }
    }
}
