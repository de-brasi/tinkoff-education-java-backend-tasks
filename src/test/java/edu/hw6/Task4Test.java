package edu.hw6;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Task4Test {
    @Test
    public void uniqueTest(@TempDir Path tempDir) throws IOException {
        Path tempFile = tempDir.resolve("tempFile.txt");
        Files.createFile(tempFile);

        final String textToWrite = "Programming is learned by writing programs. ― Brian Kernighan";
        Task4.doStreamComposition(tempFile, textToWrite);

        assertThat(Files.readAllLines(tempFile).getFirst()).isEqualTo(textToWrite);
    }
}
