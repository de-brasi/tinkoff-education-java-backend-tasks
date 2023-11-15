// Task 1

package edu.hw6;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DiskMap extends HashMap<String, String> {
    private final String separator = ":";
    private final String FILENAME_PREFIX = "DiskMap_file_";
    private final String FILE_EXTENSION = ".txt";
    private final String fileName;
    private final File file;

    public DiskMap() {
        fileName = FILENAME_PREFIX + String.valueOf(UUID.randomUUID()) + FILE_EXTENSION;
        file = new File(fileName);
    }

    public DiskMap(Map<String, String> map) {
        super(map);
        fileName = FILENAME_PREFIX + String.valueOf(UUID.randomUUID()) + FILE_EXTENSION;
        file = new File(fileName);
    }

    public DiskMap(String pathToFile) {
        fileName = FILENAME_PREFIX + String.valueOf(UUID.fromString(pathToFile)) + FILE_EXTENSION;
        file = new File(fileName);
        validateFile(file);
    }

    public DiskMap(Map<String, String> map, String pathToFile) {
        super(map);
        fileName = FILENAME_PREFIX + String.valueOf(UUID.fromString(pathToFile)) + FILE_EXTENSION;
        file = new File(fileName);
        validateFile(file);
    }

    public boolean loadToDisk() {
        try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
            BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);

            for (var entry : this.entrySet()) {
                bufferedWriter.write(entry.getKey() + separator + entry.getValue());
                bufferedWriter.newLine();
            }

            bufferedWriter.close();
            fileOutputStream.close();
            outputStreamWriter.close();
            return true;
        } catch (IOException ioException) {
            return false;
        }
    }

    public boolean uploadFromDisk() {
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String line;
            String[] keyAndValue;
            int value = 1;
            int key = 0;
            while ((line = bufferedReader.readLine()) != null) {
                keyAndValue = line.split(separator);
                this.put(keyAndValue[key], keyAndValue[value]);
            }

            bufferedReader.close();
            fileInputStream.close();
            inputStreamReader.close();
            return true;
        } catch (IOException ioException) {
            return false;
        }
    }

    private void validateFile(File file) {
        if (!file.isFile()) {
            throw new IllegalArgumentException("Invalid file name");
        }
    }
}
