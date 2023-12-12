package edu.hw10.task2;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class CustomInvocationHandler implements InvocationHandler {
    private final Object target;

    public CustomInvocationHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.isAnnotationPresent(Cache.class) && method.getAnnotation(Cache.class).persist()) {
            System.out.println("Cached method: " + method.getName());
        }
        System.out.println("Before invoking independent custom method: " + method.getName());
        // You can perform additional tasks here.
        Object result = method.invoke(target, args); // Delegate to the real object.
        // You can perform additional tasks here.
        System.out.println("After invoking independent custom method: " + method.getName());
        return result;
    }
}
