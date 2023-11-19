package edu.hw6;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.zip.Adler32;
import java.util.zip.CheckedOutputStream;

public class Task4 {
    private Task4() {
        // not allowed
    }

    public static void doStreamComposition(Path filePath, String text) {
        if (!Files.isWritable(filePath)) {
            throw new IllegalArgumentException("Unexpected type of file");
        }

        try (OutputStream fileOutputStream = Files.newOutputStream(filePath);
             CheckedOutputStream checkedOutputStream = new CheckedOutputStream(fileOutputStream, new Adler32());
             BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(checkedOutputStream);
             OutputStreamWriter outputStreamWriter = new OutputStreamWriter(bufferedOutputStream, StandardCharsets.UTF_8);
             PrintWriter fileWriter = new PrintWriter(outputStreamWriter)) {

            fileWriter.println(text);

        } catch (IOException e) {
            LOGGER.info(e.getStackTrace());
        }
    }

    private final static Logger LOGGER = LogManager.getLogger();
}
