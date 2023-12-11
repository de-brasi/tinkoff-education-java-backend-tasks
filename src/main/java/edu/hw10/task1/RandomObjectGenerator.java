package edu.hw10.task1;

import edu.hw10.task1.annotations.Max;
import edu.hw10.task1.annotations.Min;
import edu.hw10.task1.annotations.NotNull;
import java.lang.annotation.Annotation;
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
            return createRandomBaseTypeObj(classObj, AnnotationsRestrictions.getDefault());
        }

        // TODO: выяснить почему не могу отсортировать в обратном порядке

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
        var parameterAnnotations = executable.getParameterAnnotations();
        Class<?>[] arguments = executable.getParameterTypes();
        Object[] argValues = new Object[arguments.length];
        for (int i = 0; i < arguments.length; i++) {
            var curParam = arguments[i];
            if (isBaseType(curParam)) {
                var restrictions = parseAnnotations(parameterAnnotations[i]);
                argValues[i] = createRandomBaseTypeObj(curParam, restrictions);
            } else {
                argValues[i] = nextObject(curParam);
            }
        }

        return argValues;
    }

    private AnnotationsRestrictions parseAnnotations(Annotation[] annotations) {
        var res = AnnotationsRestrictions.getDefault();

        for (var annotation: annotations) {
            if (annotation instanceof Min) {
                res.setMinValue(((Min) annotation).value());
            } else if (annotation instanceof Max) {
                res.setMaxValue(((Max) annotation).value());
            } else if (annotation instanceof NotNull) {
                res.setNotNull(true);
            } else {
                throw new RuntimeException("Unexpected annotation");
            }
        }

        return res;
    }

    private Object createRandomBaseTypeObj(Class<?> classObj, AnnotationsRestrictions restrictions) {
        return switch (classObj.getName()) {
            case "java.lang.String" -> {
                var res = UUID.randomUUID().toString();
                assert !restrictions.notNull || res != null;
                yield res;
            }
            case "java.lang.Integer" -> {
                int origin = restrictions.getMinValue();
                int maxPossible = restrictions.getMaxValue();
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

    private static class AnnotationsRestrictions {
        AnnotationsRestrictions() {
            minValue = 0;
            maxValue = 1_000_000;
            notNull = false;
        }

        public static AnnotationsRestrictions getDefault() {
            return new AnnotationsRestrictions();
        }

        public void setMaxValue(Integer maxValue) {
            this.maxValue = maxValue;
        }

        public void setMinValue(Integer minValue) {
            this.minValue = minValue;
        }

        public void setNotNull(boolean notNull) {
            this.notNull = notNull;
        }

        public Integer getMinValue() {
            return minValue;
        }

        public Integer getMaxValue() {
            return maxValue;
        }

        private Integer minValue;
        private Integer maxValue;
        private boolean notNull;
    }
}
