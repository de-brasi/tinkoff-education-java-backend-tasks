// Task 1

package edu.hw2;

public sealed interface Expr {
    double evaluate();

    record Constant(double value) implements Expr {
        @Override
        public double evaluate() {
            return value;
        }
    }

    record Negate(Expr val) implements Expr {
        @Override
        public double evaluate() {
            return -1 * val.evaluate();
        }
    }

    record Exponent(Expr base, int power) implements Expr {
        @Override
        public double evaluate() {
            return Math.pow(base.evaluate(), power);
        }
    }

    record Addition(Expr lhs, Expr rhs) implements Expr {
        @Override
        public double evaluate() {
            return lhs().evaluate() + rhs().evaluate();
        }
    }

    record Multiplication(Expr lhs, Expr rhs) implements Expr {
        @Override
        public double evaluate() {
            return lhs().evaluate() * rhs().evaluate();
        }
    }
}
