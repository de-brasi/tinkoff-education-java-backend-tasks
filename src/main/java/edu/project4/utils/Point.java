package edu.project4.utils;

public record Point(double x, double y) {
    public static Point of(double x, double y) {
        return new Point(x, y);
    }

    public Point withCoefficients(double a, double b, double c, double d, double e, double f) {
        return new Point(
            x * a + y * b + c,
            x * d + y * e + f
        );
    }
}
