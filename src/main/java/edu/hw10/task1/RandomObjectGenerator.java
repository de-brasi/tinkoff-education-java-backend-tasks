package edu.hw10.task1;

import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;
import java.util.UUID;

public class RandomObjectGenerator {
    public Object nextObject(Class<?> classObj, String methodName) {
        Method[] generatingPublicMethodsSortedByParamsCountDescending = Arrays.stream(classObj.getMethods())
            .filter(method -> method.getName().equals(methodName))
            .filter(method -> Modifier.isPublic(method.getModifiers()))
            .sorted(Comparator.comparingInt(Method::getParameterCount).reversed())
            .toArray(Method[]::new);

        if (generatingPublicMethodsSortedByParamsCountDescending.length == 0) {
            throw new RuntimeException("No such method found");
        }

        Method mostParameterizableGeneratingMethod = generatingPublicMethodsSortedByParamsCountDescending[0];
        var args = generateRandomArguments(mostParameterizableGeneratingMethod);
        try {
            return mostParameterizableGeneratingMethod.invoke(classObj, args);
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public Object nextObject(Class<?> classObj) {
        if (isBaseType(classObj)) {
            return createRandomBaseTypeObj(classObj);
        }

        // TODO: выяснить почему не могу отсортировать в обратном порядке
        // TODO: выяснить чем отличается обычный конструктор от конструктора record

        Constructor<?>[] constructors = Arrays.stream(classObj.getConstructors())
            .sorted(Comparator.comparingInt(Constructor::getParameterCount)).toArray(Constructor<?>[]::new);

        if (constructors.length == 0) {
            throw new RuntimeException("No public constructors available");
        }

        Constructor<?> mostParameterizableConstructor = constructors[constructors.length - 1];
        var args = generateRandomArguments(mostParameterizableConstructor);
        try {
            return mostParameterizableConstructor.newInstance(args);
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private Object[] generateRandomArguments(Executable executable) {
        Class<?>[] arguments = executable.getParameterTypes();
        // TODO
        Object[] argValues = new Object[arguments.length];
        for (int i = 0; i < arguments.length; i++) {
            argValues[i] = nextObject(arguments[i]);
        }

        return argValues;
    }

    private Object createRandomBaseTypeObj(Class<?> classObj) {
        return switch (classObj.getName()) {
            case "java.lang.String" -> UUID.randomUUID().toString();
            case "java.lang.Integer" -> {
                int origin = 0;
                int maxPossible = 1_000_000;
                yield random.nextInt(origin, maxPossible + 1);
            }
            case null, default -> throw new RuntimeException("Unknown base type '" + classObj.getName() + "'");
        };
    }

    private boolean isBaseType(Class<?> toCheck) {
        if (toCheck.isPrimitive()) {
            return true;
        }

        String className = toCheck.getName();
        return className.equals("java.lang.String") || className.equals("java.lang.Integer");
    }

    private final Random random = new Random();
}
