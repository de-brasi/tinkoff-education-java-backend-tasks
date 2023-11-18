package edu.hw6;

import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.util.regex.Pattern;

@SuppressWarnings("ConstantName")
public class Task3 {
    private Task3() {
        // not allowed
    }

    public static final AbstractFilter isDirectory = Files::isDirectory;
    public static final AbstractFilter isRegularFile = Files::isRegularFile;
    public static final AbstractFilter isHidden = Files::isHidden;
    public static final AbstractFilter isReadable = Files::isReadable;
    public static final AbstractFilter isWritable = Files::isWritable;

    public static AbstractFilter sizeLessThan(int size) {
        return path -> {
            try {
                return Files.isRegularFile(path) && Files.size(path) < size;
            } catch (IOException e) {
                return false;
            }
        };
    }

    public static AbstractFilter sizeEqual(int size) {
        return path -> {
            try {
                return Files.isRegularFile(path) && Files.size(path) == size;
            } catch (IOException e) {
                return false;
            }
        };
    }

    public static AbstractFilter globMatches(String glob) {
        return path -> {
            PathMatcher pathMatcher = FileSystems.getDefault().getPathMatcher("glob:" + glob);
            return pathMatcher.matches(
                Path.of(
                    path.getFileName().toString()
                )
            );
        };
    }

    public static AbstractFilter regexContains(String regex) {
        var namePattern = Pattern.compile(regex);
        return path -> {
            return namePattern.matcher(path.getFileName().toString()).find();
        };
    }

    public static AbstractFilter magicNumber(int... magic) {
        return path -> {
            try (FileChannel channel = FileChannel.open(path)) {
                byte[] actualBytes = Files.readAllBytes(path);

                if (actualBytes.length < magic.length) {
                    return false;
                }

                for (int i = 0; i < magic.length; i++) {
                    byte expectedByte = (byte) magic[i];
                    byte actualByte = actualBytes[i];

                    if (expectedByte != actualByte) {
                        return false;
                    }
                }
            } catch (IOException e) {
                return false;
            }

            return true;
        };
    }
}
