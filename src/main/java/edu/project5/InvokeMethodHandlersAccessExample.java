package edu.project5;

import edu.project5.targets.Student;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class InvokeMethodHandlersAccessExample {
    private InvokeMethodHandlersAccessExample() {}

    @SuppressWarnings("UncommentedMain")
    public static void main(String[] args) throws Throwable {
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        MethodType methodType = MethodType.methodType(String.class);

        MethodHandle instanceMethodNameHandle = lookup.findVirtual(Student.class, "name", methodType);
        MethodHandle instanceMethodSurnameHandle = lookup.findVirtual(Student.class, "surname", methodType);

        LOGGER.info(instanceMethodNameHandle.invoke(TARGET));
        LOGGER.info(instanceMethodSurnameHandle.invoke(TARGET));
    }

    private final static Student TARGET = new Student("Some", "Student");
    private final static Logger LOGGER = LogManager.getLogger();
}
