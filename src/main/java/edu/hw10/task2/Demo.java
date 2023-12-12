package edu.hw10.task2;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Demo {
    private Demo() {}

    @SuppressWarnings("UncommentedMain")
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

    private final static Logger LOGGER = LogManager.getLogger();

    public interface CustomInterface {
        @Cache(persist = true)
        void customMethod();
    }

    public interface AnotherCustomInterface {
        void anotherCustomMethod();
    }

    static class CustomObject implements CustomInterface {
        @Override
        public void customMethod() {
            LOGGER.info("Custom object method called.");
        }
    }

    static class AnotherCustomObj implements AnotherCustomInterface {

        @Override
        public void anotherCustomMethod() {
            LOGGER.info("Another custom object method called.");
        }
    }
}
