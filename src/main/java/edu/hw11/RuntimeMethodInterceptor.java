package edu.hw11;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.agent.ByteBuddyAgent;
import net.bytebuddy.dynamic.loading.ClassReloadingStrategy;
import static net.bytebuddy.implementation.MethodDelegation.to;
import static net.bytebuddy.matcher.ElementMatchers.named;

public class RuntimeMethodInterceptor {
    private RuntimeMethodInterceptor() {}

    public static void interceptMethod(Class<?> target, Class<?> donor, String methodName) {
        ByteBuddyAgent.install();
        new ByteBuddy()
            .redefine(target)
            .method(named(methodName))
            .intercept(to(donor))
            .make()
            .load(
                target.getClassLoader(),
                ClassReloadingStrategy.fromInstalledAgent()
            );
    }
}
