package edu.project4;

import edu.project4.postprocessors.SingleThreadLogarithmicGammaCorrector;
import edu.project4.renders.SingleThreadRenderer;
import edu.project4.utils.ColorShortcuts;
import edu.project4.utils.Domain;
import edu.project4.utils.ImageFormat;
import edu.project4.utils.ImageUtils;
import edu.project4.utils.RendererRunningConfig;
import edu.project4.variationgenerators.LinearTransformationsGenerator;
import edu.project4.variationgenerators.NonLinearTransformationsGenerator;
import edu.project4.variationgenerators.Transformation;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        FractalImage canvas =
            FractalImage.createWithBaseColor(2048, 1080, ColorShortcuts.MIDNIGHT_BLUE);

        // TODO: с вариациями - как то добавить поддержку вероятности вытащить ту или иную вариацию
        List<Transformation> variations = List.of(
            NonLinearTransformationsGenerator
                .getHeartTransformation()
                .withColor(ColorShortcuts.HOT_PINK),
            NonLinearTransformationsGenerator
                .getSphericalTransformation()
                .withColor(ColorShortcuts.DARK_RED),
            NonLinearTransformationsGenerator
                .getSphericalTransformation(200, 200)
                .withColor(ColorShortcuts.YELLOW),
            LinearTransformationsGenerator
                .getRandomNonCompressiveTransformation()
                .withColor(ColorShortcuts.SILVER),
            LinearTransformationsGenerator
                .getRandomNonCompressiveTransformation()
                .withColor(ColorShortcuts.SILVER)
        );

        SingleThreadRenderer renderer = new SingleThreadRenderer();
        long seed = 200;
        Domain domain = new Domain(-100, 100, -100, 100);
        RendererRunningConfig config = new RendererRunningConfig(
            (short) 20, Transformation.Type.LINEAR,
            (short) 100, Transformation.Type.NON_LINEAR,
            0
        );
        canvas = renderer.render(canvas, variations, domain, config, 1_000_000, (short) 100, seed);
        new SingleThreadLogarithmicGammaCorrector().process(canvas, 2);

        Path output = Paths.get("").toAbsolutePath().getParent().resolve("example_image");
        ImageUtils.save(canvas, output, ImageFormat.PNG);
    }
}
