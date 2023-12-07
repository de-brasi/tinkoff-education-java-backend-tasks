package edu.hw9;

import edu.hw9.task1.StatisticsCollection;
import edu.hw9.task1.StatisticsCollector;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.fail;

public class Task1Test {
    @Test
    @DisplayName("Single-thread producer")
    public void test1() {
        var metricsName = "some numbers";
        var numbers = new double[] {0, 1, 2, 3, 4};
        var collector = new StatisticsCollector();

        Map<String, StatisticsCollection> correctAnswer = Map.of(
            metricsName,
            new StatisticsCollection(
                Arrays.stream(numbers).sum(),
                Arrays.stream(numbers).min().getAsDouble(),
                Arrays.stream(numbers).max().getAsDouble(),
                Arrays.stream(numbers).sum() / numbers.length,
                numbers.length
            )
        );

        collector.push(metricsName, numbers);

        for (var metrics: collector.stats()) {
            assertThat(metrics.getValue()).isEqualTo(correctAnswer.get(metrics.getKey()));
        }
    }

    @Test
    @DisplayName("Single-thread producer, two data-batch for one metrics")
    public void test2() {
        var metricsName = "some numbers";
        var numbers1 = new double[] {0, 1, 2, 3, 4};
        var numbers2 = new double[] {5, 6, 7, 8, 9};
        var collector = new StatisticsCollector();

        Map<String, StatisticsCollection> correctAnswer = Map.of(
            metricsName,
            new StatisticsCollection(
                IntStream.range(0, 10).mapToDouble(i -> (double) i).sum(),
                IntStream.range(0, 10).mapToDouble(i -> (double) i).min().getAsDouble(),
                IntStream.range(0, 10).mapToDouble(i -> (double) i).max().getAsDouble(),
                IntStream.range(0, 10).mapToDouble(i -> (double) i).sum() / IntStream.range(0, 10).count(),
                (int) IntStream.range(0, 10).count()
            )
        );

        collector.push(metricsName, numbers1);
        collector.push(metricsName, numbers2);

        for (var metrics: collector.stats()) {
            assertThat(metrics.getValue()).isEqualTo(correctAnswer.get(metrics.getKey()));
        }
    }

    @Test
    @DisplayName("Single-thread producer, one data-batch per metrics")
    public void test3() {
        var metricsName1 = "some numbers 1";
        var metricsName2 = "some numbers 2";
        var numbers1 = new double[] {0, 1, 2, 3, 4};
        var numbers2 = new double[] {5, 6, 7, 8, 9};
        var collector = new StatisticsCollector();

        Map<String, StatisticsCollection> correctAnswer = Map.of(
            metricsName1,
            new StatisticsCollection(
                Arrays.stream(numbers1).sum(),
                Arrays.stream(numbers1).min().getAsDouble(),
                Arrays.stream(numbers1).max().getAsDouble(),
                Arrays.stream(numbers1).sum() / Arrays.stream(numbers1).count(),
                (int) Arrays.stream(numbers1).count()
            ),
            metricsName2,
            new StatisticsCollection(
                Arrays.stream(numbers2).sum(),
                Arrays.stream(numbers2).min().getAsDouble(),
                Arrays.stream(numbers2).max().getAsDouble(),
                Arrays.stream(numbers2).sum() / Arrays.stream(numbers2).count(),
                (int) Arrays.stream(numbers2).count()
            )
        );

        collector.push(metricsName1, numbers1);
        collector.push(metricsName2, numbers2);

        for (var metrics: collector.stats()) {
            assertThat(metrics.getValue()).isEqualTo(correctAnswer.get(metrics.getKey()));
        }
    }

    @Test
    @DisplayName("Multi-thread producer, two (short) data-batch for one metrics")
    public void test4() {
        var metricsName = "some numbers";
        var numbers1 = new double[] {0, 1, 2, 3, 4};
        var numbers2 = new double[] {5, 6, 7, 8, 9};
        var collector = new StatisticsCollector();

        Map<String, StatisticsCollection> correctAnswer = Map.of(
            metricsName,
            new StatisticsCollection(
                IntStream.range(0, 10).mapToDouble(i -> (double) i).sum(),
                IntStream.range(0, 10).mapToDouble(i -> (double) i).min().getAsDouble(),
                IntStream.range(0, 10).mapToDouble(i -> (double) i).max().getAsDouble(),
                IntStream.range(0, 10).mapToDouble(i -> (double) i).sum() / IntStream.range(0, 10).count(),
                (int) IntStream.range(0, 10).count()
            )
        );

        var producer1 = new Thread(() -> collector.push(metricsName, numbers1));
        var producer2 = new Thread(() -> collector.push(metricsName, numbers2));

        producer1.start();
        producer2.start();

        try {
            producer1.join();
            producer2.join();
        } catch (InterruptedException e) {
            LOGGER.info(e);
            fail("Unexpected error");
        }

        for (var metrics: collector.stats()) {
            assertThat(metrics.getValue()).isEqualTo(correctAnswer.get(metrics.getKey()));
        }
    }

    @Test
    @DisplayName("Multi-thread producer, two (short) data-batch for different metrics")
    public void test5() {
        var metricsName1 = "some numbers 1";
        var metricsName2 = "some numbers 2";
        var numbers1 = new double[] {0, 1, 2, 3, 4};
        var numbers2 = new double[] {5, 6, 7, 8, 9};
        var collector = new StatisticsCollector();

        Map<String, StatisticsCollection> correctAnswer = Map.of(
            metricsName1,
            new StatisticsCollection(
                Arrays.stream(numbers1).sum(),
                Arrays.stream(numbers1).min().getAsDouble(),
                Arrays.stream(numbers1).max().getAsDouble(),
                Arrays.stream(numbers1).sum() / Arrays.stream(numbers1).count(),
                (int) Arrays.stream(numbers1).count()
            ),
            metricsName2,
            new StatisticsCollection(
                Arrays.stream(numbers2).sum(),
                Arrays.stream(numbers2).min().getAsDouble(),
                Arrays.stream(numbers2).max().getAsDouble(),
                Arrays.stream(numbers2).sum() / Arrays.stream(numbers2).count(),
                (int) Arrays.stream(numbers2).count()
            )
        );

        var producer1 = new Thread(() -> collector.push(metricsName1, numbers1));
        var producer2 = new Thread(() -> collector.push(metricsName2, numbers2));

        producer1.start();
        producer2.start();

        try {
            producer1.join();
            producer2.join();
        } catch (InterruptedException e) {
            LOGGER.info(e);
            fail("Unexpected error");
        }

        for (var metrics: collector.stats()) {
            assertThat(metrics.getValue()).isEqualTo(correctAnswer.get(metrics.getKey()));
        }
    }

    @Test
    @DisplayName("Multi-thread producer, load test")
    public void test6() {
        var metricsNamePrefix = "some numbers ";
        var metricsNameCount = 100;
        var producersCount = Runtime.getRuntime().availableProcessors();
        var operationsPerProducer = 100;
        var sequenceMaxValue= 100_000;
        var numbersInSequence = IntStream.range(0, sequenceMaxValue + 1).mapToDouble(item -> (double) item).toArray();
        var collector = new StatisticsCollector();

        Map<String, StatisticsCollection> correctAnswer = new HashMap<>();
        for (int i = 0; i < metricsNameCount; i++) {
            var currentMetricsName = metricsNamePrefix + i;

            var sum = Arrays.stream(numbersInSequence).sum() * producersCount * operationsPerProducer;
            var min = Arrays.stream(numbersInSequence).min().getAsDouble();
            var max = Arrays.stream(numbersInSequence).max().getAsDouble();
            int count = (int) Arrays.stream(numbersInSequence).count() * producersCount * operationsPerProducer;
            var avg = sum / count;

            var currentMetricsExpectedStatistics = new StatisticsCollection(
                sum, min, max, avg, count
            );

            correctAnswer.put(currentMetricsName, currentMetricsExpectedStatistics);
        }

        var producers = new ArrayList<Thread>();

        for (int i = 0; i < producersCount; i++) {
            var newProducer = new Thread(
                () -> {
                    for (int j = 0; j < operationsPerProducer; j++) {
                        for (int metricsNumber = 0; metricsNumber < metricsNameCount; metricsNumber++) {
                            var currentName = metricsNamePrefix + metricsNumber;
                            var data = Arrays.copyOf(numbersInSequence, numbersInSequence.length);
                            collector.push(currentName, data);
                        }
                    }
                }
            );
            producers.add(newProducer);
        }

        for (var producer: producers) {
            producer.start();
        }

        for (var producer: producers) {
            try {
                producer.join();
            } catch (InterruptedException e) {
                LOGGER.info(e);
                fail("Unexpected error");
            }
        }

        for (var metrics: collector.stats()) {
            assertThat(metrics.getValue()).isEqualTo(correctAnswer.get(metrics.getKey()));
        }
    }

    private final static Logger LOGGER = LogManager.getLogger();
}
