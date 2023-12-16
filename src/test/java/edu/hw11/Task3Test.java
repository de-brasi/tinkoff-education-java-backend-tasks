package edu.hw11;

import org.junit.jupiter.api.Test;

public class Task3Test {
    @Test
    public void test() throws InstantiationException, IllegalAccessException {
        var someObj = FibClassGenerator.doClassWithFibMethod().newInstance();
        System.out.println(someObj.toString());
    }
}
