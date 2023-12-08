package edu.hw9;

import edu.hw9.task2.FileSystemSearcher;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Task2Test {
    @Test
    @DisplayName("Single-thread producer")
    public void test1(@TempDir Path tempDir) throws IOException {
        Path withFilesDir = tempDir.resolve("with_files_dir");
        Files.createDirectories(withFilesDir);

        for (int i = 1; i <= 1000; i++) {
            Path txtFile = withFilesDir.resolve("file" + i + ".txt");
            Files.createFile(txtFile);
        }


        for (int i = 1; i <= 1000; i++) {
            Path txtFile = tempDir.resolve("file" + i + ".txt");
            Files.createFile(txtFile);
        }

        var res = FileSystemSearcher.findDirectoriesWithMoreThanFiles(1000, tempDir);
        LOGGER.info(res);

        assertThat(res.contains(tempDir)).isTrue();
        assertThat(res.contains(withFilesDir)).isTrue();
    }

    private final static Logger LOGGER = LogManager.getLogger();
}
