package edu.project4;

import edu.project4.renders.SingleThreadRenderer;
import edu.project4.utils.Color;
import edu.project4.utils.ImageFormat;
import edu.project4.utils.ImageUtils;
import edu.project4.utils.Pixel;
import edu.project4.utils.Point;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) {
        FractalImage canvas = FractalImage.create(1920, 1080);

        // TODO: с вариациями - как то добавить поддержку вероятности вытащить ту или иную вариацию
        // TODO: добавить афинные преобразования
        List<Transformation> variations = List.of(
            // TODO: почему то не работает
            // Sinusoidal
            (Point source) -> {
                return new Point(500 * Math.sin(source.x()), 500 * Math.sin(source.y()));
            },

            // TODO: почему то не работает
            // Spherical
            (Point source) -> {
                double newX = source.x() / (Math.pow(source.x(), 2) + Math.pow(source.y(), 2));
                double newY = source.y() / (Math.pow(source.x(), 2) + Math.pow(source.y(), 2));
                return new Point(120 * newX, 120 * newY);
            },

            // TODO: почему то не работает
            // Polar
            (Point source) -> {
                double newX = Math.atan(source.y() / source.x()) / Math.PI;
                double newY = Math.sqrt(Math.pow(source.x(), 2) + Math.pow(source.y(), 2)) - 1;
                return new Point(200 * newX, 200 * newY);
            },

            // Heart
            (Point source) -> {
                double newX =
                    Math.sqrt(Math.pow(source.x(), 2) + Math.pow(source.y(), 2))
                        * Math.sin(
                        Math.sqrt(Math.pow(source.x(), 2) + Math.pow(source.y(), 2))
                            * Math.atan(source.y()) / source.x()
                    );
                double newY =
                    -1 * Math.sqrt(Math.pow(source.x(), 2) + Math.pow(source.y(), 2))
                        * Math.cos(
                        Math.sqrt(Math.pow(source.x(), 2) + Math.pow(source.y(), 2))
                            * Math.atan(source.y()) / source.x()
                    );
                return new Point(10 * newX, 10 * newY);
            }
        );

        SingleThreadRenderer renderer = new SingleThreadRenderer();
        long seed = 100;
        canvas = renderer.render(canvas, variations, 10_000, (short) 100, seed);

        Path output = Paths.get("").toAbsolutePath().getParent().resolve("example_image");
        ImageUtils.save(canvas, output, ImageFormat.PNG);
    }

    public static void draftPrinting() {
        // TODO:
        //  1) --done-- отрисовать что-то и превратить в png;
        //  2) --done-- научиться получать коэффициенты для аффинных преобразований;
        //  3) --done-- реализация алгоритма с habr'а;
        //  4) разобраться с нерабочими НЕ линейными преобразованиями; 5)
        //  4) разнести по функциям;
        //  5) полученный алгоритм - в SingleThreadRender


        // ---------------------------------------------
        // -------------- Создание холста --------------
        // ---------------------------------------------
        int canvasWidth = 1920;
        int canvasHeight = 1080;

        final Pixel[][] canvasData = new Pixel[canvasHeight][canvasWidth];
        for (int i = 0; i < canvasHeight; i++) {
            for (int j = 0; j < canvasData[i].length; j++) {
                canvasData[i][j] = new Pixel(new Color(245, 222, 179), 0);
            }
        }

        FractalImage canvas = new FractalImage(canvasData, canvasWidth, canvasHeight);


        // ----------------------------------------------------------------------------------------------
        //  -------------- Реализация алгоритма с Хабра; пока весь холст - рабочая область --------------
        // ----------------------------------------------------------------------------------------------
        // TODO: а надо ли конфигурировать что то кроме размера холста?

        //   -------------- Создание набора аффинных преобразований --------------
        final int transformationsCount = 10;
        AffineTransformationCoefficients[] affineCoefficients =
            IntStream
                .range(0, transformationsCount)
                .mapToObj(i -> generateAffineTransformationCoefficients())
                .toArray(AffineTransformationCoefficients[]::new);

        // TODO: генерить преобразования вместе с цветами?
        Color[] affineColors = {
            Color.of(0, 191, 255),
            Color.of(255, 215, 0),
            Color.of(238, 130, 238),
            Color.of(255, 69, 0),
            Color.of(0, 100, 0),

            Color.of(128, 0, 128),
            Color.of(0, 128, 128),
            Color.of(255, 255, 0),
            Color.of(189, 183, 107),
            Color.of(255, 0, 0)
        };

        //   -------------- Создание набора нелинейных преобразований --------------
        Transformation[] nonLinearTransformations = {
            // TODO: почему то не работает
            // Sinusoidal
            (Point source) -> {
                return new Point(Math.sin(source.x()), Math.sin(source.y()));
            },

            // TODO: почему то не работает
            // Spherical
            (Point source) -> {
                double newX = source.x() / (Math.pow(source.x(), 2) + Math.pow(source.y(), 2));
                double newY = source.y() / (Math.pow(source.x(), 2) + Math.pow(source.y(), 2));
                return new Point(newX, newY);
            },

            // TODO: почему то не работает
            // Polar
            (Point source) -> {
                double newX = Math.atan(source.y() / source.x()) / Math.PI;
                double newY = Math.sqrt(Math.pow(source.x(), 2) + Math.pow(source.y(), 2)) - 1;
                return new Point(newX, newY);
            },

            // Heart
            (Point source) -> {
                double newX =
                    Math.sqrt(Math.pow(source.x(), 2) + Math.pow(source.y(), 2))
                        * Math.sin(
                            Math.sqrt(Math.pow(source.x(), 2) + Math.pow(source.y(), 2))
                                * Math.atan(source.y()) / source.x()
                    );
                double newY =
                    -1 * Math.sqrt(Math.pow(source.x(), 2) + Math.pow(source.y(), 2))
                        * Math.cos(
                        Math.sqrt(Math.pow(source.x(), 2) + Math.pow(source.y(), 2))
                            * Math.atan(source.y()) / source.x()
                    );
                return new Point(newX, newY);
            }
        };

        //   -------------- Основной код --------------
        final long samples = 100_000;
        final int iterPerSample = 100;

        final double xMin = -100;
        final double xMax = 100;
        final double yMin = -100;
        final double yMax = 100;

        Point newPoint;

        int xCoordinate;
        int yCoordinate;

        Pixel curPixel;

        int affineTransformIndex = 0;

        int totalHitCount = 0;

        // TODO: симметрия
        for (int i = 0; i < samples; i++) {
            logProcessDebugOnly(i, samples);

            newPoint = Point.of(random.nextDouble(xMin, xMax), random.nextDouble(yMin, yMax));

            // TODO: проверить, а что если не скипать!
            for (int j = 0; j < 1000; j++) {
                newPoint = doAffineTransformation(
                    newPoint,
                    affineCoefficients[random.nextInt(0, affineCoefficients.length)]
                );

                // TODO: применить НЕ линейное преобразование;
//                newPoint = nonLinearTransformations[1].apply(newPoint);
            }

            for (int j = 0; j < iterPerSample; j++) {
                affineTransformIndex = random.nextInt(0, affineCoefficients.length);

                newPoint = doAffineTransformation(
                    newPoint, affineCoefficients[affineTransformIndex]
                );

                // TODO: применить НЕ линейное преобразование;
                newPoint = nonLinearTransformations[3].apply(newPoint);

                xCoordinate = (int) Math.round(newPoint.x());
                yCoordinate = (int) Math.round(newPoint.y());

                if (canvas.contains(xCoordinate, yCoordinate)) {
                    curPixel = canvas.pixel(xCoordinate, yCoordinate);
                    ++totalHitCount;

                    // TODO: нужен пересчет попаданий нормальный - не дело каждый раз новый Pixel создавать
                    if (curPixel.hitCount() == 0) {
                        canvas.changePixelTo(xCoordinate, yCoordinate, new Pixel(affineColors[affineTransformIndex], 1));
                    } else {
                        // TODO: почнму то не работает смешивание
//                        curPixel.color().mixColorWith(affineColors[affineTransformIndex]);
                        canvas.changePixelTo(xCoordinate, yCoordinate, new Pixel(affineColors[affineTransformIndex], 1));
                    }

                }
            }
        }

        Path output = Paths.get("").toAbsolutePath().getParent().resolve("example_image");
        ImageUtils.save(canvas, output, ImageFormat.PNG);

        LOGGER.info(
            "DONE! Total hit count: " + totalHitCount
                + "; hit rate: " + (float) totalHitCount / (samples * (iterPerSample - 20))
        );
    }

    private static void printAxes(FractalImage src) {
        for (int x = 0; x < src.width() / 2 - 1; x++) {
            src.changePixelTo(x, 0, new Pixel(Color.of(0, 0, 0), 0));
        }

        for (int y = 0; y < src.height() / 2 - 1; y++) {
            src.changePixelTo(0, y, new Pixel(Color.of(0, 0, 0), 0));
        }
    }

    private static void dummyExample() {
        Path output = Paths.get("").toAbsolutePath().getParent().resolve("example_image");

        Pixel[][] source = new Pixel[128][512];
        for (int i = 0; i < source.length; i++) {
            for (int j = 0; j < source[i].length; j++) {
                source[i][j] = new Pixel(
                    new Color(j % 256, i % 256, (i + j) % 256),
                    0
                );
            }
        }

        FractalImage image = new FractalImage(source, source[0].length, source.length);
        ImageUtils.save(image, output, ImageFormat.BMP);
    }

    private static AffineTransformationCoefficients generateAffineTransformationCoefficients() {
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
        } while (!(
            Math.pow(a, 2) + Math.pow(d, 2) < 1
                && Math.pow(b, 2) + Math.pow(e, 2) < 1
                && Math.pow(a, 2) + Math.pow(b, 2) + Math.pow(d, 2) + Math.pow(e, 2) < 1 + Math.pow(a*e - b*d, 2)
        ));

        return new AffineTransformationCoefficients(a, b, c, d, e, f);
    }

    private static Point doAffineTransformation(Point source, AffineTransformationCoefficients coefficients) {
        return new Point(
            source.x() * coefficients.a() + source.y() * coefficients.b() + coefficients.c(),
            source.x() * coefficients.d() + source.y() * coefficients.e() + coefficients.f()
        );
    }

    private static void logProcessDebugOnly(long i, long max) {
        long step = max / 100;
        if (i >= (prevPivotDebugOnly + 1) * (step + 1)) {
            ++prevPivotDebugOnly;
            LOGGER.info("Render status: " + prevPivotDebugOnly + "%");
        }
    }

    private static long prevPivotDebugOnly = 0;

    // TODO: можно добавить seed
    private final static Random random = new Random();
    private final static Logger LOGGER = LogManager.getLogger();

    private record AffineTransformationCoefficients(double a, double b, double c, double d, double e, double f) {}
}
