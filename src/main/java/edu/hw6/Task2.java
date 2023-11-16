package edu.hw6;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Task2 {
    private Task2() {
        // not allowed
    }

    public static void cloneFile(Path path) {
        if (path.getParent() != null && !Files.isDirectory(path)) {
            try {
                doCopyOfFile(path);
            } catch (IOException ioException) {
                LOGGER.info(ioException.getLocalizedMessage());
                LOGGER.info(ioException.getStackTrace());
            }
        } else {
            throw new IllegalArgumentException("Got path to directory instead of path to file!");
        }
    }

    private static void doCopyOfFile(Path originalFilePath) throws IOException {
        Path directoryPath = originalFilePath.getParent();

        String curFileExtension = getFileExtension(originalFilePath);
        String curFileName = getFileNameWithoutExtension(originalFilePath);

        ArrayList<String> copyOfCurFile = getCopies(directoryPath, curFileName, curFileExtension);
        int maxCopyIndex = getCopyCount(copyOfCurFile);
        String copyName = getCopyName(curFileName, maxCopyIndex) + "." + curFileExtension;

        Path newCopyPath = directoryPath.resolve(copyName).toAbsolutePath();
        Files.copy(originalFilePath, newCopyPath);
    }

    private static ArrayList<String> getCopies(Path parentPath, String fileName, String fileExtension)
        throws IOException {
        DirectoryStream<Path> directoryStream = Files.newDirectoryStream(parentPath);
        ArrayList<String> result = new ArrayList<>();
        for (Path entry : directoryStream) {
            String entryName = entry.getFileName().toString();
            if (entryName.startsWith(fileName) && !entryName.equals(fileName + "." + fileExtension)) {
                result.add(fileName);
            }
        }
        directoryStream.close();
        return result;
    }

    private static int getCopyCount(List<String> toSearch) {
        return toSearch.size();
    }

    private static String getCopyName(String fileName, int copyCount) {
        if (copyCount == 0) {
            return fileName + COPY_POSTFIX;
        } else {
            return fileName + COPY_POSTFIX + " (" + Integer.toString(copyCount + 1) + ")";
        }
    }

    private static String getFileExtension(Path path) {
        String fileName = path.getFileName().toString();
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex == -1) ? "" : fileName.substring(dotIndex + 1);
    }

    private static String getFileNameWithoutExtension(Path path) {
        String fileName = path.getFileName().toString();
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex == -1) ? "" : fileName.substring(0, dotIndex);
    }

    private static final String COPY_POSTFIX = " - копия";
    private final static Logger LOGGER = LogManager.getLogger();
}
