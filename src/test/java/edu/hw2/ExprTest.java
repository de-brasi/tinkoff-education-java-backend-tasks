// Test Task 1

package edu.hw2;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ExprTest {
    @Test
    @DisplayName("Тест из условия")
    void test1() {
        var two = new Expr.Constant(2);
        var four = new Expr.Constant(4);
        var negOne = new Expr.Negate(new Expr.Constant(1));
        var sumTwoFour = new Expr.Addition(two, four);
        var mult = new Expr.Multiplication(sumTwoFour, negOne);
        var exp = new Expr.Exponent(mult, 2);
        var res = new Expr.Addition(exp, new Expr.Constant(1));

        LOGGER.info(res + " = " + res.evaluate());
    }

    @Test
    @DisplayName("Тест Constant")
    void test2() {
        var two = new Expr.Constant(2);
        assertThat(two.evaluate()).isEqualTo(2);
    }

    @Test
    @DisplayName("Тест Negate")
    void test3() {
        var negativeTwo = new Expr.Negate(new Expr.Constant(2));
        assertThat(negativeTwo.evaluate()).isEqualTo(-2);
    }

    @Test
    @DisplayName("Тест Exponent")
    void test4() {
        var exp = new Expr.Exponent(new Expr.Constant(2), 3);
        assertThat(exp.evaluate()).isEqualTo(8);
    }

    @Test
    @DisplayName("Тест Addition")
    void test5() {
        var lhs = new Expr.Constant(2);
        var rhs = new Expr.Constant(3);
        var addition = new Expr.Addition(lhs, rhs);
        assertThat(addition.evaluate()).isEqualTo(5);
    }

    @Test
    @DisplayName("Тест Multiplication")
    void test6() {
        var lhs = new Expr.Constant(2);
        var rhs = new Expr.Constant(3);
        var multiplication = new Expr.Multiplication(lhs, rhs);
        assertThat(multiplication.evaluate()).isEqualTo(6);
    }

    @Test
    @DisplayName("Тест long expression")
    void test7() {
        // -((1 + 2)^3 * 2) = -54
        var res = new Expr.Negate(
            new Expr.Multiplication(
                new Expr.Exponent(
                    new Expr.Addition(new Expr.Constant(1), new Expr.Constant(2)),
                    3
                ),
                new Expr.Constant(2)
            )
        );
        assertThat(res.evaluate()).isEqualTo(-54);
    }

    private final static Logger LOGGER = LogManager.getLogger();
}
