// Tests for Task 1

package edu.hw6;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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
    public void test2() {
        var diskMap = new DiskMap();
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
