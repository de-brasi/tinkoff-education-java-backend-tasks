package edu.hw2;

import edu.hw2.task3.ConnectionException;
import edu.hw2.task3.ConnectionManagerType;
import edu.hw2.task3.PopularCommandExecutor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.fail;

public class Task3Test {
    @Test
    @DisplayName("(almost) Stable connection")
    void test1() {
        var executor = new PopularCommandExecutor(1000, ConnectionManagerType.DEFAULT);
        try {
            executor.updatePackages();
        } catch (ConnectionException ignored) {
            fail("Too little chance for that!");
        }
    }

    @Test
    @DisplayName("Faulty connection")
    void test2() {
        var executor = new PopularCommandExecutor(1000, ConnectionManagerType.FAULTY);
        try {
            executor.updatePackages();
        } catch (ConnectionException ignored) {
            fail("Too little chance for that!");
        }
    }
}
