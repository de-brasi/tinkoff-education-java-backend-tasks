// Tests for Task 1

package edu.hw6;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class DiskMapTest {
    @Test
    @DisplayName("Функционал Map")
    public void test1() {
        var diskMap = new DiskMap();
        diskMap.put("a", "1");
        diskMap.put("b", "2");
        diskMap.put("c", "3");
        diskMap.remove("b");

        assertThat(diskMap.containsKey("a")).isTrue();
        assertThat(diskMap.containsKey("b")).isFalse();
        assertThat(diskMap.containsKey("c")).isTrue();
    }

    @Test
    @DisplayName("Проверка загрузки на диск и чтения данных без указания пути и чтения")
    public void test2(@TempDir Path tempDir) throws IOException {
        Path tempFilePath = Files.createTempFile(tempDir, "test", ".txt");

        var diskMap = new DiskMap(tempFilePath.toString());
        diskMap.put("a", "1");
        diskMap.put("b", "2");
        diskMap.put("c", "3");

        assertThat(diskMap.loadToDisk()).isTrue();

        diskMap.clear();
        assertThat(diskMap.uploadFromDisk()).isTrue();

        assertThat(diskMap.containsKey("a")).isTrue();
        assertThat(diskMap.containsKey("b")).isTrue();
        assertThat(diskMap.containsKey("c")).isTrue();
    }
}
