package edu.project4;

import edu.project4.postprocessors.SingleThreadLogarithmicGammaCorrector;
import edu.project4.renders.MultiThreadRenderer;
import edu.project4.renders.SingleThreadRenderer;
import edu.project4.utils.Color;
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
    private Main() {}

    @SuppressWarnings("MagicNumber")
    public static void main(String[] args) {
        FractalImage canvas =
            FractalImage.createWithBaseColor(3840, 2160, Color.of(14, 14, 78));

        List<Transformation> variations = List.of(
            NonLinearTransformationsBuilder
                .getHeartTransformation()
                .withWeight(12),
            NonLinearTransformationsBuilder
                .getSphericalTransformation()
                .withWeight(1),
            NonLinearTransformationsBuilder
                .getSinusoidalTransformation(1.8, 1.8)
                .withWeight(3),
            NonLinearTransformationsBuilder
                .getPolarTransformation()
                .withWeight(2),
            LinearTransformationsBuilder
                .getRandomCompressiveTransformation()
                .withColor(ColorShortcuts.DARK_RED)
                .withWeight(16),
            LinearTransformationsBuilder
                .getRandomCompressiveTransformation()
                .withColor(ColorShortcuts.DEEP_SKY_BLUE)
                .withWeight(2),
            LinearTransformationsBuilder
                .getRandomCompressiveTransformation()
                .withColor(ColorShortcuts.RED)
                .withWeight(2),
            LinearTransformationsBuilder
                .getRandomCompressiveTransformation()
                .withColor(ColorShortcuts.MAROON)
                .withWeight(2),
            LinearTransformationsBuilder
                .getRandomCompressiveTransformation()
                .withColor(ColorShortcuts.FUCHSIA)
                .withWeight(6)
        );


        MultiThreadRenderer renderer = new MultiThreadRenderer();
        long seed = 100;
        Domain domain = new Domain(-1.7, 1.7, -1, 1);
        RendererRunningConfig config = new RendererRunningConfig(
            5_000_000, (short) 50, (short) 200, 1
        );
        canvas = renderer.render(canvas, variations, domain, config, seed);
        new SingleThreadLogarithmicGammaCorrector().process(canvas, 2);

        Path output = Paths.get("").toAbsolutePath().getParent().resolve("example_image");
        ImageUtils.save(canvas, output, ImageFormat.PNG);
    }
}
