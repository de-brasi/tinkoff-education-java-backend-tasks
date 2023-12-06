package edu.project4;

import edu.project4.postprocessors.SingleThreadLogarithmicGammaCorrector;
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
    public static void main(String[] args) {
        FractalImage canvas =
            FractalImage.createWithBaseColor(3840, 2160, Color.of(14, 14, 78));

        List<Transformation> variations = List.of(
            NonLinearTransformationsBuilder
                .getHeartTransformation()
                .withWeight(2),
            NonLinearTransformationsBuilder
                .getSinusoidalTransformation(1.8, 1.8)
                .withWeight(6),
//            LinearTransformationsBuilder
//                .getRandomCompressiveTransformation()
//                .withColor(ColorShortcuts.DARK_RED)
//                .withWeight(8),
            LinearTransformationsBuilder
                .getRandomCompressiveTransformation()
                .withColor(ColorShortcuts.MEDIUM_VIOLET_RED)
                .withWeight(6),
            LinearTransformationsBuilder
                .getRandomCompressiveTransformation()
                .withColor(ColorShortcuts.GOLD)
                .withWeight(5),
            LinearTransformationsBuilder
                .getRandomCompressiveTransformation()
                .withColor(ColorShortcuts.STEEL_BLUE)
                .withWeight(4)
        );


        SingleThreadRenderer renderer = new SingleThreadRenderer();
        long seed = 100;
        Domain domain = new Domain(-1.7, 1.7, -1, 1);
        RendererRunningConfig config = new RendererRunningConfig(
            500_000, (short) 20, (short) 100, 4
        );
        canvas = renderer.render(canvas, variations, domain, config, seed);
        new SingleThreadLogarithmicGammaCorrector().process(canvas, 2);

        Path output = Paths.get("").toAbsolutePath().getParent().resolve("example_image");
        ImageUtils.save(canvas, output, ImageFormat.PNG);
    }
}
