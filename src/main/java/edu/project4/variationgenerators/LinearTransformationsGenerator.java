package edu.project4.variationgenerators;

import edu.project4.utils.Point;
import java.util.Random;

public class LinearTransformationsGenerator {
    private LinearTransformationsGenerator() {}

    public static Transformation getTransformation(double a, double b, double c, double d, double e, double f) {
        AffineTransformationCoefficients coefficients = new AffineTransformationCoefficients(a, b, c, d, e, f);

        return point -> new Point(
            point.x() * coefficients.a() + point.y() * coefficients.b() + coefficients.c(),
            point.x() * coefficients.d() + point.y() * coefficients.e() + coefficients.f()
        );
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

        AffineTransformationCoefficients coefficients = generateRandomCoefficients(predicate);

        // TODO: узнать, что если я верну объект типа Transformation
        //  в виде point -> applyCoefficients(point, coefficients).
        //  То есть будет использоваться статическая функция.
        //  В таком случае вызов объекта Transformation в разных потоках
        //  должен будет синхронизироваться между вызовами над тем же объектом в параллельных потоках?

        // TODO: WARNING!!!
        // TODO: выяснить вообще как происходит вызов статической функции класса из параллельных потоков.
        return point -> new Point(
            point.x() * coefficients.a() + point.y() * coefficients.b() + coefficients.c(),
            point.x() * coefficients.d() + point.y() * coefficients.e() + coefficients.f()
        );
    }

    public static Transformation getRandomNonCompressiveTransformation() {
        CoefficientsGeneratorPredicate predicate =
            (a, b, d, e) -> (
                Math.pow(a, 2) + Math.pow(d, 2) >= 1
                    || Math.pow(b, 2) + Math.pow(e, 2) >= 1
                    || Math.pow(a, 2) + Math.pow(b, 2) + Math.pow(d, 2) + Math.pow(e, 2)
                    >= 1 + Math.pow(a*e - b*d, 2)
            );

        AffineTransformationCoefficients coefficients = generateRandomCoefficients(predicate);
        return point -> new Point(
            point.x() * coefficients.a() + point.y() * coefficients.b() + coefficients.c(),
            point.x() * coefficients.d() + point.y() * coefficients.e() + coefficients.f()
        );
    }

    private static AffineTransformationCoefficients generateRandomCoefficients(CoefficientsGeneratorPredicate predicate) {
        // TODO: настроить ограничения на рандом коэффициентов и сдвига -
        //      возможно стоит настроить это через конструктор или сеттер,
        //      а при вызове ссылаться на поля класса

        // TODO: настроить поддержку таймаута -
        //  возможно принять его в конструкторе (сколько время ждать), или через сеттер

        double a;
        double b;
        double c;
        double d;
        double e;
        double f;

        do {
            a = random.nextDouble(-1, 1);
            b = random.nextDouble(-1, 1);
            d = random.nextDouble(-1, 1);
            e = random.nextDouble(-1, 1);

            f = random.nextDouble(-500, 500.0);
            c = random.nextDouble(-550, 500.0);
        } while (!predicate.validArguments(a, b, d, e));

        return new AffineTransformationCoefficients(a, b, c, d, e, f);
    }


    @FunctionalInterface
    private interface CoefficientsGeneratorPredicate {
        boolean validArguments(double a, double b, double d, double e);
    }


    private record AffineTransformationCoefficients(double a, double b, double c, double d, double e, double f) {}


    private final static Random random = new Random();
}
