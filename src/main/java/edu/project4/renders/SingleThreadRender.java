package edu.project4.renders;

import edu.project4.FractalImage;
import edu.project4.Transformation;
import edu.project4.utils.Color;
import edu.project4.utils.Pixel;
import edu.project4.utils.Point;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.List;
import java.util.Random;

public class SingleThreadRender implements Renderer {
    @Override
    public FractalImage render(FractalImage canvas,
        // TODO: как то ассоциировать цвет с вариацией(преобразованием)
        List<Transformation> variations,
        int samples, short iterPerSample,
        long seed)
    {
        // TODO: разобраться на что влияет сид, и сюда ли надо передавать seed
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

        int variationIndex = 0;

        // TODO: симметрия
        for (int i = 0; i < samples; i++) {
            newPoint = Point.of(random.nextDouble(xMin, xMax), random.nextDouble(yMin, yMax));

            // TODO: конфигурировать сколько действий на "раздувание" координаты
            //  и какие действия - линейные или нелинейные преобразования!
            // TODO: проверить, а что если не скипать!
            for (int j = 0; j < 100; j++) {
                // TODO: конфигурировать, какие преобразования применять, а какие нет и в каком порядке
                newPoint = Point.of(random.nextDouble(xMin, xMax), random.nextDouble(yMin, yMax));
            }

            for (int j = 0; j < iterPerSample; j++) {
                // TODO: конфигурировать, какие преобразования применять, а какие нет и в каком порядке
                newPoint = variations.get(random.nextInt(0, variationIndex)).apply(newPoint);

                // TODO: применить НЕ линейное преобразование, или как?;

                xCoordinate = (int) Math.round(newPoint.x());
                yCoordinate = (int) Math.round(newPoint.y());

                if (canvas.contains(xCoordinate, yCoordinate)) {
                    curPixel = canvas.pixel(xCoordinate, yCoordinate);

                    // TODO: нужен пересчет попаданий нормальный - не дело каждый раз новый Pixel создавать
                    if (curPixel.hitCount() == 0) {
                        // TODO: нужно продумать как подмешивать цвет, ассоциированный с новым преобразованием
                        canvas.changePixelTo(xCoordinate, yCoordinate, new Pixel(Color.of(12, 123, 100), 1));
                    } else {
                        // TODO: нужно продумать как подмешивать цвет, ассоциированный с новым преобразованием
                        // TODO: разобраться, почему не работает смешивание
//                        curPixel.color().mixColorWith(affineColors[affineTransformIndex]);
                        canvas.changePixelTo(xCoordinate, yCoordinate, new Pixel(Color.of(12, 123, 100), 1));
                    }

                }
            }
        }

        // TODO: потом в вызывающем коде сохранять файл по какому-то пути
        return canvas;
    }

    private final static Logger LOGGER = LogManager.getLogger();
}
