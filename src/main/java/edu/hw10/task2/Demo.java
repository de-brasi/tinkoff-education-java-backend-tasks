package edu.hw10.task2;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class Demo {
    public interface CustomInterface {
        void customMethod();
    }

    public interface AnotherCustomInterface {
        void anotherCustomMethod();
    }

    static class CustomObject implements CustomInterface {
        @Override
        public void customMethod() {
            System.out.println("Custom object method called.");
        }
    }

    static class AnotherCustomObj implements AnotherCustomInterface {

        @Override
        public void anotherCustomMethod() {
            System.out.println("Another custom object method called.");
        }
    }

    public static void main(String[] args) {

        CustomInterface realObject = new CustomObject();
        CustomInterface customProxy = (CustomInterface) CacheProxy.create(
            realObject,
            CustomInterface.class
        );

        AnotherCustomInterface anotherRealObject = new AnotherCustomObj();
        AnotherCustomInterface anotherCustomProxy = (AnotherCustomInterface) CacheProxy.create(
            anotherRealObject,
            AnotherCustomInterface.class
        );

        customProxy.customMethod();
        anotherCustomProxy.anotherCustomMethod();
    }
}
