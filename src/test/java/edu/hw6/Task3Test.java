package edu.hw6;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.EnumSet;
import java.util.Set;
import static edu.hw6.Task3.globMatches;
import static edu.hw6.Task3.sizeEqual;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Task3Test {
    @Test
    @DisplayName("Тестирование isDirectory")
    public void test1(@TempDir Path tempDir) throws IOException {
        Path tempFolder = tempDir.resolve("tempFolder");
        Files.createDirectory(tempFolder);
        assertThat(Task3.IS_DIRECTORY.accept(tempFolder)).isTrue();
    }

    @Test
    @DisplayName("Тестирование isRegularFile")
    public void test2(@TempDir Path tempDir) throws IOException {
        Path tempFile = tempDir.resolve("tempFile.png");
        Files.createFile(tempFile);
        assertThat(Task3.IS_REGULAR_FILE.accept(tempFile)).isTrue();
    }

    @Test
    @DisplayName("Тестирование isHidden")
    public void test3(@TempDir Path tempDir) throws IOException {
        Path tempFile = tempDir.resolve(".hiddenFile");
        Files.createFile(tempFile);
        assertThat(Task3.IS_HIDDEN.accept(tempFile)).isTrue();
    }

    @Test
    @DisplayName("Тестирование isReadable - True")
    public void test4(@TempDir Path tempDir) throws IOException {
        Path tempFile = tempDir.resolve("tempReadableFile.txt");
        Files.createFile(tempFile);
        assertThat(Task3.IS_READABLE.accept(tempFile)).isTrue();
    }

    @Test
    @DisplayName("Тестирование isReadable - False")
    public void test5(@TempDir Path tempDir) throws IOException {
        Path tempFile = tempDir.resolve("nonReadableFile.txt");

        Set<PosixFilePermission> nonReadablePermissions = EnumSet.noneOf(PosixFilePermission.class);
        Files.createFile(tempFile, PosixFilePermissions.asFileAttribute(nonReadablePermissions));

        assertThat(Task3.IS_READABLE.accept(tempFile)).isFalse();
    }

    @Test
    @DisplayName("Тестирование sizeLessThan, sizeEqual")
    public void test6(@TempDir Path tempDir) throws IOException {
        Path tempFile = tempDir.resolve("tempFile");
        Files.createFile(tempFile);

        int fileSizeInBytes = 100;
        byte[] data = new byte[fileSizeInBytes];

        Files.write(tempFile, data, StandardOpenOption.WRITE);

        assertThat(Task3.sizeLessThan(fileSizeInBytes).accept(tempFile)).isFalse();
        assertThat(Task3.sizeLessThan(fileSizeInBytes - 1).accept(tempFile)).isFalse();
        assertThat(Task3.sizeLessThan(fileSizeInBytes + 1).accept(tempFile)).isTrue();
        assertThat(Task3.sizeEqual(fileSizeInBytes).accept(tempFile)).isTrue();
    }

    @Test
    @DisplayName("Тестирование globMatches")
    public void test7(@TempDir Path tempDir) throws IOException {
        Path tempFile = tempDir.resolve("tempFile.txt");
        Files.createFile(tempFile);
        Files.write(tempFile, "Hello, World!".getBytes());
        assertThat(globMatches("*.txt").accept(tempFile)).isTrue();
    }

    @Test
    @DisplayName("Тестирование regexContains")
    public void test8(@TempDir Path tempDir) throws IOException {
        Path tempFile = tempDir.resolve("tempFile.txt");
        Files.createFile(tempFile);
        assertThat(Task3.regexContains("^.*temp.*$").accept(tempFile)).isTrue();
    }

    @Test
    @DisplayName("Тестирование magicNumber")
    public void test9(@TempDir Path tempDir) throws IOException {
        Path tempFile = tempDir.resolve("tempFile");
        Files.createFile(tempFile);

        byte[] signature = {(byte) 0x89, 'P', 'N', 'G'};
        Files.write(tempFile, signature, StandardOpenOption.WRITE);

        assertThat(Task3.magicNumber(0x89, 'P', 'N', 'G').accept(tempFile)).isTrue();
    }

    @Test
    @DisplayName("Тестирование некоторых фильтров в цепочке")
    public void test10(@TempDir Path tempDir) throws IOException {
        Path tempFile = tempDir.resolve("tempFile.txt");
        Files.createFile(tempFile);

        byte[] signature = {(byte) 0x89, 'P', 'N', 'G'};
        Files.write(tempFile, signature, StandardOpenOption.WRITE);

        assertThat(
            Task3
                .magicNumber(0x89, 'P', 'N', 'G')
                .and(Task3.IS_READABLE)
                .and(Task3.globMatches("*.txt"))
                .and(Task3.regexContains("^(.*)mpFi(.*)$"))
                .and(sizeEqual(signature.length))
                .accept(tempFile)
        ).isTrue();
    }
}
