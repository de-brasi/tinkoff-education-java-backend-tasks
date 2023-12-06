package edu.project4.variationgenerators;

import java.util.Random;
import java.util.concurrent.TimeoutException;

public class LinearTransformationsBuilder {
    private LinearTransformationsBuilder() {}

    public static Transformation getTransformation(double a, double b, double c, double d, double e, double f) {
        AffineTransformationCoefficients coefficients = new AffineTransformationCoefficients(a, b, c, d, e, f);

        Transformation result = point ->
            point.withCoefficients(
                coefficients.a(), coefficients.b(), coefficients.c(),
                coefficients.d(), coefficients.e(), coefficients.f()
            );

        return result.withType(Transformation.Type.LINEAR);
    }

    public static Transformation getRandomTransformation() {
        return (random.nextInt() % 2 == 0)
            ? getRandomCompressiveTransformation()
            : getRandomNonCompressiveTransformation();
    }

    public static Transformation getRandomCompressiveTransformation() {
        CoefficientsGeneratorPredicate predicate =
            (a, b, d, e) -> (
                Math.pow(a, 2) + Math.pow(d, 2) < 1
                    && Math.pow(b, 2) + Math.pow(e, 2) < 1
                    && Math.pow(a, 2) + Math.pow(b, 2) + Math.pow(d, 2) + Math.pow(e, 2)
                    < 1 + Math.pow(a*e - b*d, 2)
            );

        try {
            AffineTransformationCoefficients coefficients = generateRandomCoefficients(predicate, TIMEOUT_MILLISECONDS);

            Transformation result = point -> point.withCoefficients(
                coefficients.a(), coefficients.b(), coefficients.c(),
                coefficients.d(), coefficients.e(), coefficients.f()
            );

            return result.withType(Transformation.Type.LINEAR);
        } catch (TimeoutException timeoutException) {
            throw new RuntimeException(timeoutException);
        }
    }

    public static Transformation getRandomNonCompressiveTransformation() {
        CoefficientsGeneratorPredicate predicate =
            (a, b, d, e) -> (
                Math.pow(a, 2) + Math.pow(d, 2) >= 1
                    || Math.pow(b, 2) + Math.pow(e, 2) >= 1
                    || Math.pow(a, 2) + Math.pow(b, 2) + Math.pow(d, 2) + Math.pow(e, 2)
                    >= 1 + Math.pow(a*e - b*d, 2)
            );

        try {
            AffineTransformationCoefficients coefficients = generateRandomCoefficients(predicate, TIMEOUT_MILLISECONDS);
            Transformation result = point -> point.withCoefficients(
                coefficients.a(), coefficients.b(), coefficients.c(),
                coefficients.d(), coefficients.e(), coefficients.f()
            );

            return result.withType(Transformation.Type.LINEAR);
        } catch (TimeoutException timeoutException) {
            throw new RuntimeException(timeoutException);
        }
    }

    private static AffineTransformationCoefficients generateRandomCoefficients
        (CoefficientsGeneratorPredicate predicate, long timeout) throws TimeoutException {
        double a;
        double b;
        double c;
        double d;
        double e;
        double f;

        var startTime = System.nanoTime();
        var curTime = startTime;

        do {
            curTime = System.nanoTime();

            a = random.nextDouble(-1, 1);
            b = random.nextDouble(-1, 1);
            d = random.nextDouble(-1, 1);
            e = random.nextDouble(-1, 1);

            f = random.nextDouble(-2, 2);
            c = random.nextDouble(-2, 2);
        } while (
            !predicate.validArguments(a, b, d, e)
                && (curTime - startTime) <= timeout * NANOSECONDS_PER_MILLISECONDS
        );

        if ((curTime - startTime) > timeout * NANOSECONDS_PER_MILLISECONDS) {
            throw new TimeoutException("TimeoutException when generate coefficients for linear transformation");
        }

        return new AffineTransformationCoefficients(a, b, c, d, e, f);
    }


    @FunctionalInterface
    private interface CoefficientsGeneratorPredicate {
        boolean validArguments(double a, double b, double d, double e);
    }


    private record AffineTransformationCoefficients(double a, double b, double c, double d, double e, double f) {}


    private final static Random random = new Random();
    private final static int TIMEOUT_MILLISECONDS = 1_000;
    private final static int NANOSECONDS_PER_MILLISECONDS = 1_000_000;
}
