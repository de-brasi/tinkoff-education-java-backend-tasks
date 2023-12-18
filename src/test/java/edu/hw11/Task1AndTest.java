package edu.hw11;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.implementation.FixedValue;
import org.junit.jupiter.api.Test;
import java.lang.reflect.InvocationTargetException;
import static net.bytebuddy.matcher.ElementMatchers.named;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Task1AndTest {
    @Test
    public void test()
        throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        final String expectedString = "Hello, ByteBuddy!";

        Class<?> exampleType = new ByteBuddy()
            .subclass(Object.class)
            .method(named("toString")).intercept(FixedValue.value(expectedString))
            .make()
            .load(getClass().getClassLoader(), ClassLoadingStrategy.Default.WRAPPER)
            .getLoaded();
        assertThat(exampleType.getDeclaredConstructor().newInstance().toString()).isEqualTo(expectedString);
    }
}
