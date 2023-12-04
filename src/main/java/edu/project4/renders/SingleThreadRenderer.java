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
        int samples, short iterPerSample,
        long seed)
    {
        var transformationsManipulator = new TransformationsManipulator(variations);
        final Random random = new Random(seed);

        Point newPoint;
        Pixel curPixel;

        int xCoordinate;
        int yCoordinate;

        // TODO: симметрия
        for (int i = 0; i < samples; i++) {
            logProcessDebugOnly(i, samples);
            newPoint = Point.of(
                random.nextDouble(domain.xMin(), domain.xMax()),
                random.nextDouble(domain.yMin(), domain.yMax())
            );

            // TODO: конфигурировать сколько действий на "раздувание" координаты
            //  и какие действия - линейные или нелинейные преобразования!
            // TODO: проверить, а что если не скипать!
            for (int j = 0; j < 20; j++) {

                // TODO: конфигурировать, какие преобразования применять, а какие нет и в каком порядке
                newPoint = transformationsManipulator.getRandom().apply(newPoint);

            }

            for (int j = 0; j < iterPerSample; j++) {

                // TODO: конфигурировать, какие преобразования применять, а какие нет и в каком порядке
                var transformation = transformationsManipulator.getRandom();
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
