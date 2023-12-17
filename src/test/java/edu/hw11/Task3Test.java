package edu.hw11;

import edu.hw11.util.FibExample;
import org.junit.jupiter.api.Test;
import java.lang.reflect.InvocationTargetException;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Task3Test {
    @Test
    public void test()
        throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        var fibGetterClass = FibClassGenerator.doClassWithFibMethod();
        var instance = fibGetterClass.newInstance();

        for (int i = 1; i < 20; i++) {
            var expectedResult = FibExample.fib(i);
            var actualResult = instance.getClass().getMethod("fib", int.class).invoke(instance, i);

            assertThat(expectedResult).isEqualTo(actualResult);
        }
    }
}
