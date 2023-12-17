package edu.project5;

import edu.project5.targets.Student;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.lang.reflect.InvocationTargetException;

public class ReflectMethodAccessExample {
    private ReflectMethodAccessExample() {}

    public static void main(String[] args)
        throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        LOGGER.info(
            target.getClass().getMethod("name").invoke(target)
        );
        LOGGER.info(
            target.getClass().getMethod("surname").invoke(target)
        );
    }

    private final static Student target = new Student("Some", "Student");
    private final static Logger LOGGER = LogManager.getLogger();
}
