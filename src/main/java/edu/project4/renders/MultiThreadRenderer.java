package edu.project4.renders;

import edu.project4.FractalImage;
import edu.project4.utils.Domain;
import edu.project4.utils.Pixel;
import edu.project4.utils.Point;
import edu.project4.utils.RendererRunningConfig;
import edu.project4.variationgenerators.Transformation;
import edu.project4.variationgenerators.TransformationsManipulator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadLocalRandom;

public class MultiThreadRenderer implements Renderer {
    public MultiThreadRenderer withCountThreads(int threadsCount) {
        this.threadsCount = threadsCount;
        return this;
    }

    @Override
    public FractalImage render(
        FractalImage canvas,
        List<Transformation> variations,
        Domain domain,
        RendererRunningConfig config,
        long seed
    )
    {
        try (var threadPool = Executors.newFixedThreadPool(threadsCount)) {
            List<Future<?>> futures = new ArrayList<>();

            for (int i = 0; i < threadsCount; i++) {
                // TODO: сделать код элегантнее с помощью submitAll
                var threadLocalConfig = new RendererRunningConfig(
                    config.samplesCount() / threadsCount,
                    config.missedIterationsCount(),
                    config.mainIterationsCount(),
                    config.symmetry()
                );
                var newFuture = threadPool.submit(
                    new ThreadRenderRoutine(canvas, variations, domain, threadLocalConfig)
                );

                futures.add(newFuture);
            }

            for (var future: futures) {
                try {
                    future.get();
                } catch (InterruptedException | ExecutionException exception) {
                    LOGGER.info(exception);
                    throw new RuntimeException(exception);
                }
            }
        }
        return canvas;
    }

    private int threadsCount = Runtime.getRuntime().availableProcessors();
    private final static Logger LOGGER = LogManager.getLogger();

    private static class ThreadRenderRoutine implements Runnable {
        public ThreadRenderRoutine(
            FractalImage canvas,
            List<Transformation> variations,
            Domain domain,
            RendererRunningConfig config
        ) {
            this.canvas = canvas;
            transformationsManipulator = new TransformationsManipulator(variations);
            this.domain = domain;
            this.config = config;
        }

        @Override
        public void run() {
            Point newPoint;
            Pixel curPixel;

            int xCoordinate;
            int yCoordinate;

            for (int i = 0; i < config.samplesCount(); i++) {
                logProcessDebugOnly(i, config.samplesCount());
                newPoint = Point.of(
                    ThreadLocalRandom.current().nextDouble(domain.xMin(), domain.xMax()),
                    ThreadLocalRandom.current().nextDouble(domain.yMin(), domain.yMax())
                );

                for (int j = 0; j < config.missedIterationsCount(); j++) {
                    newPoint = transformationsManipulator.getRandomLinear().apply(newPoint);
                    newPoint = transformationsManipulator.getRandomNonLinear().apply(newPoint);
                }

                for (int j = 0; j < config.mainIterationsCount(); j++) {
                    var transformation = transformationsManipulator.getRandomLinear();
                    newPoint = transformation.apply(newPoint);
                    newPoint = transformationsManipulator.getRandomNonLinear().apply(newPoint);

                    if (
                        !(domain.xMin() <= newPoint.x() && newPoint.x() <= domain.xMax())
                            || !(domain.yMin() <= newPoint.y() && newPoint.y() <= domain.yMax())
                    ) {
                        continue;
                    }

                    double theta2Degree = 0.0;
                    int symmetry = config.symmetry();
                    final Point origin = new Point(canvas.width() / 2D, canvas.height() / 2D);
                    Point symmetryPoint;

                    xCoordinate = canvas.width() - (int) Math.round(
                        (domain.xMax() - newPoint.x())
                            / (domain.xMax() - domain.xMin())
                            * canvas.width()
                    );
                    yCoordinate = canvas.height() - (int) Math.round(
                        (domain.yMax() - newPoint.y())
                            / (domain.yMax() - domain.yMin())
                            * canvas.height()
                    );

                    int symmetryX;
                    int symmetryY;

                    for (int s = 0; s <= symmetry; theta2Degree += (360D / symmetry), ++s) {
                        symmetryPoint = rotate(new Point(xCoordinate, yCoordinate), origin, theta2Degree);

                        symmetryX = (int) Math.round(symmetryPoint.x());
                        symmetryY = (int) Math.round(symmetryPoint.y());

                        if (canvas.containsCoordinate(symmetryX, symmetryY)) {
                            curPixel = canvas.coordinate(symmetryX, symmetryY);

                            synchronized (curPixel) {
                                if (curPixel.getHitCount() == 0) {
                                    curPixel.hit().setColor(transformation.getColor());
                                } else {
                                    curPixel.hit().getColor().mixColorWith(transformation.getColor());
                                }
                            }

                        }
                    }
                }
            }
        }

        private Point rotate(Point source, Point origin, double thetaDegree) {
            double theta2Rad = (thetaDegree * Math.PI) / 180;
            double newX = (source.x() - origin.x()) * Math.cos(theta2Rad)
                - (source.y() - origin.y()) * Math.sin(theta2Rad)
                + origin.x();
            double newY = (source.x() - origin.x()) * Math.sin(theta2Rad)
                + (source.y() - origin.y()) * Math.cos(theta2Rad)
                + origin.y();
            return new Point(newX, newY);
        }

        private void logProcessDebugOnly(long i, long max) {
            long step = max / 100;
            if (i >= (prevPivotDebugOnly + 1) * (step + 1)) {
                ++prevPivotDebugOnly;
                LOGGER.info("Thread " + Thread.currentThread().threadId()
                    + "; Render status: " + prevPivotDebugOnly + "%");
            }
        }

        private long prevPivotDebugOnly = 0;

        private final FractalImage canvas;
        private final TransformationsManipulator transformationsManipulator;
        private final Domain domain;
        private final RendererRunningConfig config;
    }
}
