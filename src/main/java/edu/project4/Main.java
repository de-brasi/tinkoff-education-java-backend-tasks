package edu.project4;

import edu.project4.postprocessors.SingleThreadLogarithmicGammaCorrector;
import edu.project4.renders.SingleThreadRenderer;
import edu.project4.utils.ColorShortcuts;
import edu.project4.utils.Domain;
import edu.project4.utils.ImageFormat;
import edu.project4.utils.ImageUtils;
import edu.project4.utils.RendererRunningConfig;
import edu.project4.variationgenerators.LinearTransformationsBuilder;
import edu.project4.variationgenerators.NonLinearTransformationsBuilder;
import edu.project4.variationgenerators.Transformation;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        FractalImage canvas =
            FractalImage.createWithBaseColor(1920, 1080, ColorShortcuts.MIDNIGHT_BLUE);

        // TODO: с вариациями - как то добавить поддержку вероятности вытащить ту или иную вариацию
        List<Transformation> variations = List.of(
            NonLinearTransformationsBuilder
                .getSphericalTransformation(1920, 1080)
                .withColor(ColorShortcuts.HOT_PINK)
                .withWeight(1),
            NonLinearTransformationsBuilder
                .getPolarTransformation()
                .withColor(ColorShortcuts.DARK_RED)
                .withWeight(1),
            NonLinearTransformationsBuilder
                .getDiskTransformation(1920, 1080)
                .withColor(ColorShortcuts.YELLOW)
                .withWeight(1),
            LinearTransformationsBuilder
                .getRandomCompressiveTransformation()
                .withColor(ColorShortcuts.SILVER)
                .withWeight(1),
            LinearTransformationsBuilder
                .getRandomCompressiveTransformation()
                .withColor(ColorShortcuts.SILVER)
                .withWeight(1)
        );


        SingleThreadRenderer renderer = new SingleThreadRenderer();
        long seed = 200;
        Domain domain = new Domain(-100, 100, -100, 100);
        RendererRunningConfig config = new RendererRunningConfig(
            2_000_000,
            (short) 10, Transformation.Type.LINEAR,
            (short) 400, Transformation.Type.NON_LINEAR,
            0
        );
        canvas = renderer.render(canvas, variations, domain, config, seed);
        new SingleThreadLogarithmicGammaCorrector().process(canvas, 2);

        Path output = Paths.get("").toAbsolutePath().getParent().resolve("example_image");
        ImageUtils.save(canvas, output, ImageFormat.PNG);
    }
}
