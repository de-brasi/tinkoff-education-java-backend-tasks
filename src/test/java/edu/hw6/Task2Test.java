package edu.hw6;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class Task2Test {
    @Test
    @DisplayName("Копирование файла, одна копия, проверка создания новой копии (не учитывая контент)")
    public void test1() {
        Path currentPath = Paths.get("").toAbsolutePath();
        String newFileName = "example.txt";
        String expectedCopyFileName = "example - копия.txt";
        Path newFilePath = currentPath.resolve(newFileName).toAbsolutePath();

        try {
            createFileWithContentUtilFunction(newFilePath, "");
            Task2.cloneFile(newFilePath);

            Stream<Path> siblingsPathStream = getSiblingFiles(newFilePath);
            ArrayList<String> siblingsNames = siblingsPathStream
                .map(Path::getFileName)
                .map(Path::toString)
                .collect(Collectors.toCollection(ArrayList::new));

            assertThat(siblingsNames.indexOf(expectedCopyFileName)).isNotEqualTo(-1);

            // Delete created files
            Files.delete(newFilePath);
            Files.delete(currentPath.resolve(expectedCopyFileName).toAbsolutePath());
        } catch (IOException ioException) {
            fail("Не ожидалось исключение IOException: " + ioException.getMessage());
            LOGGER.info(ioException.getLocalizedMessage());
        }
    }

    @Test
    @DisplayName("Копирование файла, одна копия, проверка создания новой копии (с учетом контента)")
    public void test2() {
        Path currentPath = Paths.get("").toAbsolutePath();
        String newFileName = "example.txt";
        String expectedCopyFileName = "example - копия.txt";
        String exampleContent = "Какой-то текст для тестирования";
        Path newFilePath = currentPath.resolve(newFileName).toAbsolutePath();
        Path expectedCopyPath = currentPath.resolve(expectedCopyFileName).toAbsolutePath();

        try {
            createFileWithContentUtilFunction(newFilePath, exampleContent);
            Task2.cloneFile(newFilePath);

            Stream<Path> siblingsPathStream = getSiblingFiles(newFilePath);
            ArrayList<String> siblingsNames = siblingsPathStream
                .map(Path::getFileName)
                .map(Path::toString)
                .collect(Collectors.toCollection(ArrayList::new));

            assertThat(siblingsNames.indexOf(expectedCopyFileName)).isNotEqualTo(-1);
            assertThat(getFileContent(expectedCopyPath)).isEqualTo(exampleContent);

            // Delete created files
            Files.delete(newFilePath);
            Files.delete(expectedCopyPath);
        } catch (IOException ioException) {
            fail("Не ожидалось исключение IOException: " + ioException.getMessage());
            LOGGER.info(ioException.getLocalizedMessage());
        }
    }

    @Test
    @DisplayName("Копирование файла, 3 копии, проверка создания новых копий (не учитывая контент)")
    public void test3() {
        Path currentPath = Paths.get("").toAbsolutePath();
        String newFileName = "example.txt";
        String expectedCopyFileName1 = "example - копия.txt";
        String expectedCopyFileName2 = "example - копия (2).txt";
        String expectedCopyFileName3 = "example - копия (3).txt";
        Path newFilePath = currentPath.resolve(newFileName).toAbsolutePath();
        Path expectedCopyPath1 = currentPath.resolve(expectedCopyFileName1).toAbsolutePath();
        Path expectedCopyPath2 = currentPath.resolve(expectedCopyFileName2).toAbsolutePath();
        Path expectedCopyPath3 = currentPath.resolve(expectedCopyFileName3).toAbsolutePath();

        try {
            createFileWithContentUtilFunction(newFilePath, "");

            // Do copies
            Task2.cloneFile(newFilePath);
            Task2.cloneFile(newFilePath);
            Task2.cloneFile(newFilePath);

            Stream<Path> siblingsPathStream = getSiblingFiles(newFilePath);
            ArrayList<String> siblingsNames = siblingsPathStream
                .map(Path::getFileName)
                .map(Path::toString)
                .collect(Collectors.toCollection(ArrayList::new));

            assertThat(siblingsNames.indexOf(expectedCopyFileName1)).isNotEqualTo(-1);
            assertThat(siblingsNames.indexOf(expectedCopyFileName2)).isNotEqualTo(-1);
            assertThat(siblingsNames.indexOf(expectedCopyFileName3)).isNotEqualTo(-1);

            // Delete created files
            Files.delete(newFilePath);
            Files.delete(expectedCopyPath1.toAbsolutePath());
            Files.delete(expectedCopyPath2.toAbsolutePath());
            Files.delete(expectedCopyPath3.toAbsolutePath());
        } catch (IOException ioException) {
            fail("Не ожидалось исключение IOException: " + ioException.getMessage());
            LOGGER.info(ioException.getLocalizedMessage());
        }
    }

    @Test
    @DisplayName("Копирование файла, 3 копии, проверка создания новых копий (с учетом контента)")
    public void test4() {
        Path currentPath = Paths.get("").toAbsolutePath();
        String newFileName = "example.txt";
        String expectedCopyFileName1 = "example - копия.txt";
        String expectedCopyFileName2 = "example - копия (2).txt";
        String expectedCopyFileName3 = "example - копия (3).txt";
        Path newFilePath = currentPath.resolve(newFileName).toAbsolutePath();
        Path expectedCopyPath1 = currentPath.resolve(expectedCopyFileName1).toAbsolutePath();
        Path expectedCopyPath2 = currentPath.resolve(expectedCopyFileName2).toAbsolutePath();
        Path expectedCopyPath3 = currentPath.resolve(expectedCopyFileName3).toAbsolutePath();

        String exampleContent = "Какой-то текст для тестирования";

        try {
            createFileWithContentUtilFunction(newFilePath, exampleContent);

            // Do copies
            Task2.cloneFile(newFilePath);
            Task2.cloneFile(newFilePath);
            Task2.cloneFile(newFilePath);

            Stream<Path> siblingsPathStream = getSiblingFiles(newFilePath);
            ArrayList<String> siblingsNames = siblingsPathStream
                .map(Path::getFileName)
                .map(Path::toString)
                .collect(Collectors.toCollection(ArrayList::new));

            assertThat(siblingsNames.indexOf(expectedCopyFileName1)).isNotEqualTo(-1);
            assertThat(siblingsNames.indexOf(expectedCopyFileName2)).isNotEqualTo(-1);
            assertThat(siblingsNames.indexOf(expectedCopyFileName3)).isNotEqualTo(-1);

            assertThat(getFileContent(expectedCopyPath1)).isEqualTo(exampleContent);
            assertThat(getFileContent(expectedCopyPath2)).isEqualTo(exampleContent);
            assertThat(getFileContent(expectedCopyPath3)).isEqualTo(exampleContent);

            // Delete created files
            Files.delete(newFilePath);
            Files.delete(expectedCopyPath1.toAbsolutePath());
            Files.delete(expectedCopyPath2.toAbsolutePath());
            Files.delete(expectedCopyPath3.toAbsolutePath());
        } catch (IOException ioException) {
            fail("Не ожидалось исключение IOException: " + ioException.getMessage());
            LOGGER.info(ioException.getLocalizedMessage());
        }
    }

    @Test
    @DisplayName("Копирование директории, проверка получения исключения")
    public void test5() {
        Path currentPath = Paths.get("").toAbsolutePath();
        Path parentDirPath = currentPath.getParent();

        try {
            // Do copy
            Task2.cloneFile(parentDirPath);
            fail("Не ожидалось успешное копирование");
        } catch (IllegalArgumentException ignored) {}
    }

    private static void createFileWithContentUtilFunction(Path pathToCreate, String content)
        throws IOException {
        File newFile = Files.createFile(pathToCreate).toFile();

        FileOutputStream fileOutputStream = new FileOutputStream(newFile);
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
        BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);

        bufferedWriter.write(content);

        bufferedWriter.close();
        outputStreamWriter.close();
        fileOutputStream.close();
    }

    private static String getFileContent(Path path) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(path.toFile());
        InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        StringBuilder builder = new StringBuilder();

        String line;
        while ((line = bufferedReader.readLine()) != null) {
            builder.append(line);
        }

        bufferedReader.close();
        fileInputStream.close();
        inputStreamReader.close();

        return builder.toString();
    }

    private static Stream<Path> getSiblingFiles(Path path) throws IOException {
        Path directoryPath = path.getParent();
        DirectoryStream<Path> directoryStream = Files.newDirectoryStream(directoryPath);

        directoryStream.close();
        return Files.list(directoryPath);
    }

    private final static Logger LOGGER = LogManager.getLogger();
}
