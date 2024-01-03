package edu.hw9.task1;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class StatisticsCollector {
    public StatisticsCollector() {
        int workersCount = Runtime.getRuntime().availableProcessors();
        threadPool = Executors.newFixedThreadPool(workersCount);
    }

    public StatisticsCollector(int workersCount) {
        threadPool = Executors.newFixedThreadPool(workersCount);
    }

    public void push(String metricName, double[] values) {
        var newTask = new StatisticsCollectionRoutine(metricName, values);
        var future = threadPool.submit(newTask);
        taskFutures.add(future);
    }

    public Set<Map.Entry<String, StatisticsCollection>> stats() {
        taskFutures.forEach(future -> {
            try {
                future.get();
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        });

        return dataStorage.entrySet();
    }

    private final ExecutorService threadPool;
    private final ConcurrentHashMap<String, StatisticsCollection> dataStorage = new ConcurrentHashMap<>();
    private final ConcurrentLinkedQueue<Future<?>> taskFutures = new ConcurrentLinkedQueue<>();
    private final static Logger LOGGER = LogManager.getLogger();

    private class StatisticsCollectionRoutine implements Runnable {
        StatisticsCollectionRoutine(String metrics, double[] values) {
            this.metrics = metrics;
            this.values = values;
        }

        @Override
        public void run() {
            if (values.length == 0) {
                return;
            }

            var maxVal = Arrays.stream(values).max().getAsDouble();
            var minVal = Arrays.stream(values).min().getAsDouble();
            var sum = Arrays.stream(values).sum();
            var count = values.length;

            dataStorage.compute(
                metrics,
                (key, value) -> {
                    if (value != null) {
                        return new StatisticsCollection(
                            sum + value.sum(),
                            Math.min(minVal, value.min()),
                            Math.max(maxVal, value.max()),
                            (sum + value.sum()) / (count + value.count()),
                            count + value.count()
                        );
                    } else {
                        return new StatisticsCollection(sum, minVal, maxVal, sum / count, count);
                    }
                }
            );
        }

        private final String metrics;
        private final double[] values;
        }
}
