package edu.project5;

import java.lang.invoke.LambdaMetafactory;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

@State(Scope.Thread)
public class ReflectionBenchmark {
    @SuppressWarnings({"UncommentedMain", "MagicNumber"})
    public static void main(String[] args) throws RunnerException {
        Options options = new OptionsBuilder()
            .include(ReflectionBenchmark.class.getSimpleName())
            .shouldFailOnError(true)
            .shouldDoGC(true)
            .mode(Mode.AverageTime)
            .timeUnit(TimeUnit.NANOSECONDS)
            .forks(1)
            .warmupForks(1)
            .warmupIterations(1)
            .warmupTime(TimeValue.seconds(5))
            .measurementIterations(1)
            .measurementTime(TimeValue.seconds(180))
            .build();

        new Runner(options).run();
    }

    private Student student;
    private Method method;
    private MethodHandle methodHandle;
    StudentNameInfoGetter nameGetter;

    @Setup
    public void setup() throws Throwable {
        final String sourceMethodName = "name";

        student = new Student("SomeName", "SomeSurname");
        method = student.getClass().getMethod(sourceMethodName);

        // using java.lang.invoke.MethodHandles
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        MethodType methodType = MethodType.methodType(String.class);

        methodHandle = lookup.findVirtual(Student.class, sourceMethodName, methodType);

        // using java.lang.invoke.LambdaMetafactory
        MethodHandles.Lookup caller = MethodHandles.lookup();
        MethodType getterNameType = MethodType.methodType(StudentNameInfoGetter.class);
        MethodType methodGetterType = MethodType.methodType(String.class, Student.class);
        MethodHandle originalCalledGenNameMethod = caller.findVirtual(
            Student.class, sourceMethodName, MethodType.methodType(String.class)
        );
        MethodType instantiatedMethodType = MethodType.methodType(String.class, Student.class);

        nameGetter = (StudentNameInfoGetter) LambdaMetafactory.metafactory(
                caller,
                "get",
                getterNameType,
                methodGetterType,
                originalCalledGenNameMethod,
                instantiatedMethodType
            )
            .getTarget()
            .invokeExact();
    }

    @Benchmark
    public void directAccess(Blackhole bh) {
        String name = student.name();
        bh.consume(name);
    }


    @Benchmark
    public void reflection(Blackhole bh) throws InvocationTargetException, IllegalAccessException {
        Object name = method.invoke(student);
        bh.consume(name);
    }


    @Benchmark
    public void invokeMethodHandlers(Blackhole bh) throws Throwable {
        var name = methodHandle.invoke(student);
        bh.consume(name);
    }


    @Benchmark
    public void invokeLambdaMetafactory(Blackhole bh) {
        var name = nameGetter.get(student);
        bh.consume(name);
    }


    private interface StudentNameInfoGetter {
        String get(Student target);
    }

    record Student(String name, String surname) {}
}
