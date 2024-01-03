package edu.project5;

import edu.project5.targets.Student;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DirectAccessExample {
    private DirectAccessExample() {}

    @SuppressWarnings("UncommentedMain")
    public static void main(String[] args) {
        LOGGER.info(TARGET.name());
        LOGGER.info(TARGET.surname());
    }

    private final static Student TARGET = new Student("Some", "Student");
    private final static Logger LOGGER = LogManager.getLogger();
}
