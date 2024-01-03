package edu.project4;

import edu.project4.postprocessors.SingleThreadLogarithmicGammaCorrector;
import edu.project4.renders.MultiThreadRenderer;
import edu.project4.utils.Color;
import edu.project4.utils.ColorShortcuts;
import edu.project4.utils.Domain;
import edu.project4.utils.ImageFormat;
import edu.project4.utils.ImageUtils;
import edu.project4.utils.RendererRunningConfig;
import edu.project4.variationgenerators.LinearTransformationsBuilder;
import edu.project4.variationgenerators.NonLinearTransformationsBuilder;
import edu.project4.variationgenerators.Transformation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.nio.file.Path;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FileGenerationTest {
    @Test
    @DisplayName("Test that file actually created")
    public void test(@TempDir Path tempDir) {
        FractalImage canvas =
            FractalImage.createWithBaseColor(100, 100, Color.of(14, 14, 78));

        List<Transformation> variations = List.of(
            NonLinearTransformationsBuilder
                .getHeartTransformation()
                .withWeight(12),
            LinearTransformationsBuilder
                .getRandomCompressiveTransformation()
                .withColor(ColorShortcuts.DARK_RED)
                .withWeight(16)
        );


        MultiThreadRenderer renderer = new MultiThreadRenderer();
        long seed = 100;
        Domain domain = new Domain(-1.7, 1.7, -1, 1);
        RendererRunningConfig config = new RendererRunningConfig(
            50, (short) 5, (short) 2, 1
        );
        canvas = renderer.render(canvas, variations, domain, config, seed);
        new SingleThreadLogarithmicGammaCorrector().process(canvas, 2);

        Path output = tempDir.resolve("example_image");
        ImageUtils.save(canvas, output, ImageFormat.PNG);

        assertTrue(output.toFile().exists());
    }
}
