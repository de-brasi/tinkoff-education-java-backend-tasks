package edu.project5;

import edu.project5.targets.Student;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

public class InvokeMethodHandlersAccessExample {
    private InvokeMethodHandlersAccessExample() {}

    public static void main(String[] args) throws Throwable {
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        MethodType methodType = MethodType.methodType(String.class);

        MethodHandle instanceMethodNameHandle = lookup.findVirtual(Student.class, "name", methodType);
        MethodHandle instanceMethodSurnameHandle = lookup.findVirtual(Student.class, "surname", methodType);

        LOGGER.info(instanceMethodNameHandle.invoke(target));
        LOGGER.info(instanceMethodSurnameHandle.invoke(target));
    }

    private final static Student target = new Student("Some", "Student");
    private final static Logger LOGGER = LogManager.getLogger();
}
