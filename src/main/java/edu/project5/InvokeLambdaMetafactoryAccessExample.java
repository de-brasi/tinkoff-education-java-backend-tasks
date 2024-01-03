package edu.project5;

import edu.project5.targets.Student;
import java.lang.invoke.LambdaMetafactory;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class InvokeLambdaMetafactoryAccessExample {
    private InvokeLambdaMetafactoryAccessExample() {}

    @SuppressWarnings("UncommentedMain")
    public static void main(String[] args) throws Throwable {
        final String sourceMethodName = "get";

        // Get name
        MethodHandles.Lookup caller = MethodHandles.lookup();
        MethodType getterNameType = MethodType.methodType(StudentNameInfoGetter.class);
        MethodType methodGetterType = MethodType.methodType(String.class, Student.class);
        MethodHandle originalCalledGenNameMethod = caller.findVirtual(
            Student.class, "name", MethodType.methodType(String.class)
        );
        MethodType instantiatedMethodType = MethodType.methodType(String.class, Student.class);

        StudentNameInfoGetter nameGetter = (StudentNameInfoGetter) LambdaMetafactory.metafactory(
            caller,
                sourceMethodName,
            getterNameType,
            methodGetterType,
            originalCalledGenNameMethod,
            instantiatedMethodType
        )
            .getTarget()
            .invokeExact();

        LOGGER.info(nameGetter.get(TARGET));


        // Get surname
        MethodType getterSurnameType = MethodType.methodType(StudentSurnameInfoGetter.class);
        MethodHandle originalCalledGenSurnameMethod = caller.findVirtual(
            Student.class, "surname", MethodType.methodType(String.class)
        );
        StudentSurnameInfoGetter surnameGetter = (StudentSurnameInfoGetter) LambdaMetafactory.metafactory(
                caller,
                sourceMethodName,
                getterSurnameType,
                methodGetterType,
                originalCalledGenSurnameMethod,
                instantiatedMethodType
            )
            .getTarget()
            .invokeExact();
        LOGGER.info(surnameGetter.get(TARGET));
    }

    private final static Student TARGET = new Student("Some", "Student");
    private final static Logger LOGGER = LogManager.getLogger();

    public interface StudentNameInfoGetter {
        String get(Student target);
    }

    public interface StudentSurnameInfoGetter {
        String get(Student target);
    }
}
