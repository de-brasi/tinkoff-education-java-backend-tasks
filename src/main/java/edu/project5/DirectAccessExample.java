package edu.project5;

import edu.project5.targets.Student;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DirectAccessExample {
    private DirectAccessExample() {}

    public static void main(String[] args) {
        LOGGER.info(target.name());
        LOGGER.info(target.surname());
    }

    private final static Student target = new Student("Some", "Student");
    private final static Logger LOGGER = LogManager.getLogger();
}
