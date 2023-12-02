package edu.project4;

import edu.project4.postprocessors.SingleThreadLogarithmicGammaCorrector;
import edu.project4.renders.SingleThreadRenderer;
import edu.project4.utils.Color;
import edu.project4.utils.ImageFormat;
import edu.project4.utils.ImageUtils;
import edu.project4.variationgenerators.LinearTransformationsGenerator;
import edu.project4.variationgenerators.NonLinearTransformationsGenerator;
import edu.project4.variationgenerators.Transformation;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        FractalImage canvas = FractalImage.createWithBaseColor(1920, 1080, Color.of(0, 0, 128));

        // TODO: с вариациями - как то добавить поддержку вероятности вытащить ту или иную вариацию
        List<Transformation> variations = List.of(
            NonLinearTransformationsGenerator
                .getSinusoidalTransformation(500, 1000)
                .withColor(
                    Color.of(0, 191, 255)
                ),
            NonLinearTransformationsGenerator
                .getSphericalTransformation(120, 200)
                .withColor(
                    Color.of(255, 215, 0)
                ),
            NonLinearTransformationsGenerator
                .getPolarTransformation(300, 300)
                .withColor(
                    Color.of(238, 130, 238)
                ),
            NonLinearTransformationsGenerator
               .getHeartTransformation(200, 200)
               .withColor(
                   Color.of(255, 69, 0)
               ),
            NonLinearTransformationsGenerator
                .getDiskTransformation()
                .withColor(
                    Color.of(0, 100, 0)
                ),

            LinearTransformationsGenerator
                .getRandomCompressiveTransformation()
                .withColor(
                    Color.of(128, 0, 128)
                ),
            LinearTransformationsGenerator
                .getRandomCompressiveTransformation()
                .withColor(
                    Color.of(128, 0, 128)
                ),
            LinearTransformationsGenerator
                .getRandomCompressiveTransformation()
                .withColor(
                    Color.of(0, 128, 128)
                ),
            LinearTransformationsGenerator
                .getRandomCompressiveTransformation()
                .withColor(
                    Color.of(255, 255, 0)
                ),
            LinearTransformationsGenerator
                .getRandomCompressiveTransformation()
                .withColor(
                    Color.of(128, 0, 128)
                ),
            LinearTransformationsGenerator
                .getRandomCompressiveTransformation()
                .withColor(
                    Color.of(128, 0, 128)
                ),
            LinearTransformationsGenerator
                .getRandomCompressiveTransformation()
                .withColor(
                    Color.of(128, 0, 128)
                ),
            LinearTransformationsGenerator
                .getRandomCompressiveTransformation()
                .withColor(
                    Color.of(0, 128, 128)
                ),
            LinearTransformationsGenerator
                .getRandomCompressiveTransformation()
                .withColor(
                    Color.of(255, 255, 0)
                ),
            LinearTransformationsGenerator
                .getRandomCompressiveTransformation()
                .withColor(
                    Color.of(128, 0, 128)
                ),
            LinearTransformationsGenerator
                .getRandomCompressiveTransformation()
                .withColor(
                    Color.of(128, 0, 128)
                ),
            LinearTransformationsGenerator
                .getRandomCompressiveTransformation()
                .withColor(
                    Color.of(128, 0, 128)
                ),
            LinearTransformationsGenerator
                .getRandomCompressiveTransformation()
                .withColor(
                    Color.of(0, 128, 128)
                ),
            LinearTransformationsGenerator
                .getRandomCompressiveTransformation()
                .withColor(
                    Color.of(255, 255, 0)
                ),
            LinearTransformationsGenerator
                .getRandomCompressiveTransformation()
                .withColor(
                    Color.of(128, 0, 128)
                )
        );

        SingleThreadRenderer renderer = new SingleThreadRenderer();
        long seed = 100;
        canvas = renderer.render(canvas, variations, 50_000, (short) 1000, seed);
        new SingleThreadLogarithmicGammaCorrector().process(canvas, 2);

        Path output = Paths.get("").toAbsolutePath().getParent().resolve("example_image");
        ImageUtils.save(canvas, output, ImageFormat.PNG);
    }
}
