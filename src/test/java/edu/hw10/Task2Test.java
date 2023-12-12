package edu.hw10;

import edu.hw10.task2.CacheProxy;
import edu.hw10.testinginterfaces.UselessInterface;
import edu.hw10.testinginterfaces.LongProcessOperationInterface;
import edu.hw10.testinginterfaces.UsefulInterface;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.nio.file.Path;
import java.util.concurrent.ThreadLocalRandom;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.fail;

public class Task2Test {
    @Test
    @DisplayName("Test caching")
    public void test1() throws InterruptedException {
        LongProcessOperationInterface interfaceInstance = new LongProcessOperationInterface() {
            @Override
            public int longProcess() throws InterruptedException {
                Thread.sleep(1000);
                return 1;
            }
        };
        LongProcessOperationInterface proxy = (LongProcessOperationInterface)
            CacheProxy.create(interfaceInstance, LongProcessOperationInterface.class);

        var start = System.nanoTime();
        var resBefore = proxy.longProcess();
        var firstMethodCallDuration = System.nanoTime() - start;

        start = System.nanoTime();
        var resAfter = proxy.longProcess();
        var secondMethodCallDuration = System.nanoTime() - start;

        assertThat(secondMethodCallDuration).isLessThan(firstMethodCallDuration);
        assertThat(resAfter).isEqualTo(resBefore);
    }

    @Test
    @DisplayName("Test that works with different interfaces")
    public void test2() {
        UselessInterface firstInterfaceInstance = new UselessInterface() {
            @Override
            public void uselessMethod() {
                LOGGER.info(
                    "Hi, my name is (what?)\n" +
                    "My name is (who?)..."
                );
            }
        };

        UsefulInterface secondInterfaceInstance = new UsefulInterface() {
            @Override
            public void doLifeBetter() {
                this.makePeopleHappy();
            }

            @Override
            public void makePeopleHappy() {
                LOGGER.info("Set afternoon sleep for all people in the world!");
            }

            @Override
            public long collectAllWorldsMoney() {
                return takeAwayAllMassonMoneyInSwissFranks();
            }

            private static long takeAwayAllMassonMoneyInSwissFranks() {
                return ThreadLocalRandom.current().nextLong(0, 1_000_000_000);
            }
        };

        UselessInterface firstInterfaceProxy = (UselessInterface)
            CacheProxy.create(firstInterfaceInstance, UselessInterface.class);

        UsefulInterface secondInterfaceProxy = (UsefulInterface)
            CacheProxy.create(secondInterfaceInstance, UsefulInterface.class);


        try {
            firstInterfaceProxy.uselessMethod();

            secondInterfaceProxy.makePeopleHappy();
            secondInterfaceProxy.doLifeBetter();
            LOGGER.info(secondInterfaceProxy.collectAllWorldsMoney() + " CHF");
        } catch (Exception exception) {
            fail("Not expected exception " + exception.getMessage());
        }
    }

    @Test
    @DisplayName("Test saving to file")
    public void test3(@TempDir Path tempDir) throws InterruptedException {
        Path tempFile = tempDir.resolve("tmp.json");

        LongProcessOperationInterface interfaceInstance = new LongProcessOperationInterface() {
            @Override
            public int longProcess() throws InterruptedException {
                Thread.sleep(1000);
                return 1;
            }
        };
        LongProcessOperationInterface proxy = (LongProcessOperationInterface)
            CacheProxy.create(interfaceInstance, LongProcessOperationInterface.class, tempFile);

        var start = System.nanoTime();
        var resBefore = proxy.longProcess();
        var firstMethodCallDuration = System.nanoTime() - start;

        start = System.nanoTime();
        var resAfter = proxy.longProcess();
        var secondMethodCallDuration = System.nanoTime() - start;

        assertThat(tempFile.toFile().getTotalSpace()).isNotEqualTo(0);
    }

    private final static Logger LOGGER = LogManager.getLogger();
}
