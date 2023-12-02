package edu.project4.renders;

import edu.project4.FractalImage;
import edu.project4.variationgenerators.Transformation;
import edu.project4.utils.Pixel;
import edu.project4.utils.Point;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.List;
import java.util.Random;

public class SingleThreadRenderer implements Renderer {
    @Override
    public FractalImage render(
        FractalImage canvas,
        List<Transformation> variations,
        int samples, short iterPerSample,
        long seed)
    {
        final Random random = new Random(seed);

        // TODO: конфигурировать это
        final double xMin = -100;
        final double xMax = 100;
        final double yMin = -100;
        final double yMax = 100;

        Point newPoint;
        Pixel curPixel;

        int xCoordinate;
        int yCoordinate;

        // TODO: симметрия
        for (int i = 0; i < samples; i++) {
            logProcessDebugOnly(i, samples);
            newPoint = Point.of(
                random.nextDouble(xMin, xMax),
                random.nextDouble(yMin, yMax)
            );

            // TODO: конфигурировать сколько действий на "раздувание" координаты
            //  и какие действия - линейные или нелинейные преобразования!
            // TODO: проверить, а что если не скипать!
            for (int j = 0; j < 100; j++) {
                // TODO: конфигурировать, какие преобразования применять, а какие нет и в каком порядке
                newPoint = variations.get(random.nextInt(0, variations.size())).apply(newPoint);
            }

            for (int j = 0; j < iterPerSample; j++) {
                // TODO: конфигурировать, какие преобразования применять, а какие нет и в каком порядке
                var transformation = variations.get(random.nextInt(0, variations.size()));
                newPoint = transformation.apply(newPoint);

                // TODO: применить НЕ линейное преобразование, или как?;

                xCoordinate = (int) Math.round(newPoint.x());
                yCoordinate = (int) Math.round(newPoint.y());

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

        return canvas;
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
