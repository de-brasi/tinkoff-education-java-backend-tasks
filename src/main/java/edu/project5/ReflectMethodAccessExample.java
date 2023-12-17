package edu.project5;

import edu.project5.targets.Student;
import java.lang.reflect.InvocationTargetException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ReflectMethodAccessExample {
    private ReflectMethodAccessExample() {}

    @SuppressWarnings("UncommentedMain")
    public static void main(String[] args)
        throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        LOGGER.info(
            TARGET.getClass().getMethod("name").invoke(TARGET)
        );
        LOGGER.info(
            TARGET.getClass().getMethod("surname").invoke(TARGET)
        );
    }

    private final static Student TARGET = new Student("Some", "Student");
    private final static Logger LOGGER = LogManager.getLogger();
}
