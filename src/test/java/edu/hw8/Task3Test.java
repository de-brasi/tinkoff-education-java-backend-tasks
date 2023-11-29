package edu.hw8;

import edu.hw8.task3.SingleThreadPasswordCracker;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Map;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Task3Test {
    @Test
    @DisplayName("Single thread passwords")
    public void test1() {
        var source = List.of(
            "Vasya 0cc175b9c0f1b6a831c399e269772661",
            "Masha 92eb5ffee6ae2fec3ad71c777531578f",
            "Petya 4a8a08f09d37b73795649038408b5f33"
        );
        final var expected = Map.of(
            "Vasya", "a",
            "Masha", "b",
            "Petya", "c"
        );

        var cracker = new SingleThreadPasswordCracker();
        cracker.loadRecords(source);
        final var res = cracker.getCrackedRecords();

        assertThat(expected).isEqualTo(res);
    }
}
