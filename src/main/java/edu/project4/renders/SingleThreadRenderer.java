package edu.project4.renders;

import edu.project4.FractalImage;
import edu.project4.utils.Domain;
import edu.project4.utils.RendererRunningConfig;
import edu.project4.variationgenerators.Transformation;
import edu.project4.utils.Pixel;
import edu.project4.utils.Point;
import edu.project4.variationgenerators.TransformationsManipulator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.List;
import java.util.Random;

public class SingleThreadRenderer implements Renderer {
    @Override
    public FractalImage render(
        FractalImage canvas,
        List<Transformation> variations,
        Domain domain,
        RendererRunningConfig config,
        long seed
    )
    {
        var transformationsManipulator = new TransformationsManipulator(variations);
        final Random random = new Random(seed);

        Point newPoint;
        Pixel curPixel;

        int xCoordinate;
        int yCoordinate;

        for (int i = 0; i < config.samplesCount(); i++) {
            logProcessDebugOnly(i, config.samplesCount());
            newPoint = Point.of(
                random.nextDouble(domain.xMin(), domain.xMax()),
                random.nextDouble(domain.yMin(), domain.yMax())
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

                // TODO: понять в чем ошибка с симметрией
                double theta2Degree = 0.0;
                int symmetry = config.symmetry();
                final Point origin = new Point(canvas.width(), canvas.height());
                Point symmetryPoint;

                for (int s = 0; s <= symmetry; theta2Degree += (360D / symmetry), ++s) {
                    symmetryPoint = rotate(newPoint, origin, theta2Degree);

                    xCoordinate = canvas.width() - (int) Math.round(
                        (domain.xMax() - symmetryPoint.x())
                            / (domain.xMax() - domain.xMin())
                            * canvas.width()
                    );
                    yCoordinate = canvas.height() - (int) Math.round(
                        (domain.yMax() - symmetryPoint.y())
                            / (domain.yMax() - domain.yMin())
                            * canvas.height()
                    );

                    if (canvas.containsCoordinate(xCoordinate, yCoordinate)) {
                        curPixel = canvas.coordinate(xCoordinate, yCoordinate);

                        if (curPixel.getHitCount() == 0) {
                            curPixel.hit().setColor(transformation.getColor());
                        } else {
                            curPixel.hit().getColor().mixColorWith(transformation.getColor());
                        }

                    }
                }
            }
        }

        return canvas;
    }

    private Point rotate(Point source, Point origin, double thetaDegree) {
        double theta2Rad = (thetaDegree * Math.PI) / 180;
        double newX = (source.x() - origin.x()) * Math.cos(thetaDegree)
            - (source.y() - origin.y()) * Math.sin(thetaDegree)
            + origin.x();
        double newY = (source.x() - origin.x()) * Math.sin(thetaDegree)
            + (source.y() - origin.y()) * Math.cos(thetaDegree)
            + origin.y();
        return new Point(newX, newY);
    }

    private void logProcessDebugOnly(long i, long max) {
        long step = max / 100;
        if (i >= (prevPivotDebugOnly + 1) * (step + 1)) {
            ++prevPivotDebugOnly;
            LOGGER.info("Render status: " + prevPivotDebugOnly + "%");
        }
    }

    private long prevPivotDebugOnly = 0;

    private final static Logger LOGGER = LogManager.getLogger();
}
