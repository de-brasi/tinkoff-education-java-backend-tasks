package edu.hw10.task2;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class CustomInvocationHandler implements InvocationHandler {
    private final Object target;

    public CustomInvocationHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.isAnnotationPresent(Cache.class) && method.getAnnotation(Cache.class).persist()) {
            System.out.println("Save to file result of method: " + method.getName());
        }

        System.out.println("Before invoking independent custom method: " + method.getName());

        var newCallInfo = new MethodCallInfo(method, args);
        Object result;
        if (methodsCallCache.containsKey(newCallInfo)) {
            result = methodsCallCache.get(newCallInfo);
        } else {
            result = method.invoke(target, args);
            methodsCallCache.put(newCallInfo, result);

            // TODO: если надо кэшировать, то записать в JSON файл
        }

        System.out.println("After invoking independent custom method: " + method.getName());

        return result;
    }

    private final Map<MethodCallInfo, Object> methodsCallCache = new HashMap<>();

    private static record MethodCallInfo(Method method, Object[] args) {}
}
