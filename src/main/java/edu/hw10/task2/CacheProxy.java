package edu.hw10.task2;

import java.lang.reflect.Proxy;
import java.nio.file.Path;

public class CacheProxy {
    private CacheProxy() {}

    public static Object create(Object obj, Class<?> interfaceToProxy) {
        return Proxy.newProxyInstance(
            CacheProxy.class.getClassLoader(),
            new Class<?>[] {interfaceToProxy},
            new CustomInvocationHandler(obj)
        );
    }

    public static Object create(Object obj, Class<?> interfaceToProxy, Path pathFileForSave) {
        return Proxy.newProxyInstance(
            CacheProxy.class.getClassLoader(),
            new Class<?>[] {interfaceToProxy},
            new CustomInvocationHandler(obj, pathFileForSave)
        );
    }
}
