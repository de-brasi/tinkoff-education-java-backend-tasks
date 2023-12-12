package edu.hw10.task2;

import java.lang.reflect.Proxy;

public class CacheProxy {
    private CacheProxy() {}

    public static Object create(Object obj, Class<?> interfaceToProxy) {
        return Proxy.newProxyInstance(
            CacheProxy.class.getClassLoader(),
            new Class<?>[] {interfaceToProxy},
            new CustomInvocationHandler(obj)
        );
    }
}
