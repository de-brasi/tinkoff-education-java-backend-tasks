package edu.hw2;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Task4Test {
    @Test
    void test1() {
        var res = Task4.whoCallTheFunction();
        assertThat(res.className()).isEqualTo("edu.hw2.Task4Test");
        assertThat(res.methodName()).isEqualTo("test1");
    }

    @Test
    void test2() {
        var res = Task4.whoCallTheFunction();
        assertThat(res.className()).isEqualTo("edu.hw2.Task4Test");
        assertThat(res.methodName()).isEqualTo("test2");
    }

    @Test
    void methodWithVeryLongMethodName() {
        var res = Task4.whoCallTheFunction();
        assertThat(res.className()).isEqualTo("edu.hw2.Task4Test");
        assertThat(res.methodName()).isEqualTo("methodWithVeryLongMethodName");
    }

    @Test
    void proxy() {
        var res = ProxyClass.proxyCall();
        assertThat(res.className()).isEqualTo("edu.hw2.Task4Test$ProxyClass");
        assertThat(res.methodName()).isEqualTo("proxyCall");
    }

    private static class ProxyClass {
        private ProxyClass() {}

        public static CallingInfo proxyCall() {
            return Task4.whoCallTheFunction();
        }
    }
}
