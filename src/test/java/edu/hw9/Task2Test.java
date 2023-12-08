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
import java.util.function.Predicate;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Task2Test {
    @Test
    @DisplayName("findDirectoriesWithMoreThanFiles test, not empty result")
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

        assertThat(res.contains(tempDir)).isTrue();
        assertThat(res.contains(withFilesDir)).isTrue();
    }

    @Test
    @DisplayName("findDirectoriesWithMoreThanFiles test, empty result")
    public void test2(@TempDir Path tempDir) throws IOException {
        Path withFilesDir = tempDir.resolve("with_files_dir");
        Files.createDirectories(withFilesDir);

        for (int i = 1; i <= 999; i++) {
            Path txtFile = withFilesDir.resolve("file" + i + ".txt");
            Files.createFile(txtFile);
        }


        for (int i = 1; i <= 999; i++) {
            Path txtFile = tempDir.resolve("file" + i + ".txt");
            Files.createFile(txtFile);
        }

        var res = FileSystemSearcher.findDirectoriesWithMoreThanFiles(1000, tempDir);

        assertThat(res.size()).isEqualTo(0);
    }

    @Test
    @DisplayName("findFiles test, not empty result")
    public void test3(@TempDir Path tempDir) throws IOException {
        Path withFilesDir = tempDir.resolve("with_files_dir");
        Files.createDirectories(withFilesDir);

        for (int i = 1; i <= 100; i++) {
            Path txtFile = withFilesDir.resolve("file" + i + ".txt");
            Files.createFile(txtFile);
        }

        for (int i = 1; i <= 10; i++) {
            Path txtFile = tempDir.resolve("file" + i + ".txt");
            Files.createFile(txtFile);
        }

        Path wrappedDir = withFilesDir.resolve("wrapped_dir");
        Files.createDirectories(wrappedDir);
        Path anotherTxt = withFilesDir.resolve("one_more_file.txt");
        Files.createFile(anotherTxt);

        Predicate<Path> predicateIsTxtFile = (Path path) ->
            Files.isRegularFile(path)
                && path.getFileName().toString().toLowerCase().endsWith(".txt");

        var res = FileSystemSearcher.findFiles(predicateIsTxtFile, tempDir);

        assertThat(res.size()).isEqualTo(111);
    }

    @Test
    @DisplayName("findFiles test, empty result")
    public void test4(@TempDir Path tempDir) throws IOException {
        Path withFilesDir = tempDir.resolve("with_files_dir");
        Files.createDirectories(withFilesDir);

        for (int i = 1; i <= 100; i++) {
            Path txtFile = withFilesDir.resolve("file" + i + ".png");
            Files.createFile(txtFile);
        }

        for (int i = 1; i <= 10; i++) {
            Path txtFile = tempDir.resolve("file" + i + ".jpg");
            Files.createFile(txtFile);
        }

        Path wrappedDir = withFilesDir.resolve("wrapped_dir");
        Files.createDirectories(wrappedDir);
        Path anotherTxt = withFilesDir.resolve("one_more_file.docx");
        Files.createFile(anotherTxt);

        Predicate<Path> predicateIsTxtFile = (Path path) ->
            Files.isRegularFile(path)
                && path.getFileName().toString().toLowerCase().endsWith(".txt");

        var res = FileSystemSearcher.findFiles(predicateIsTxtFile, tempDir);

        assertThat(res.size()).isEqualTo(0);
    }

    private final static Logger LOGGER = LogManager.getLogger();
}
