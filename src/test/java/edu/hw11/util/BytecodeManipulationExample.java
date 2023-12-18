package edu.hw11.util;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.agent.ByteBuddyAgent;
import net.bytebuddy.dynamic.loading.ClassReloadingStrategy;
import static net.bytebuddy.implementation.MethodDelegation.to;
import static net.bytebuddy.matcher.ElementMatchers.named;

public class BytecodeManipulationExample {

    public static class ArithmeticUtils {
        public static int sum(int a, int b) {
            return a + b;
        }
    }

    public static void main(String[] args) {
        int result = ArithmeticUtils.sum(2, 3);
        System.out.println("Modified sum result: " + result);

        ByteBuddyAgent.install();
        new ByteBuddy()
            .redefine(ArithmeticUtils.class)
            .method(named("sum"))
            .intercept(to(MyInterceptor.class))
            .make()
            .load(
                ArithmeticUtils.class.getClassLoader(),
                ClassReloadingStrategy.fromInstalledAgent()
            );

        result = ArithmeticUtils.sum(2, 3);
        System.out.println("Modified sum result: " + result);
    }

    public static class MyInterceptor {
        public static int mul(int a, int b) {
            return a * b;
        }
    }
}
