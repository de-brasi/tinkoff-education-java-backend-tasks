package edu.project4.renders;

import edu.project4.FractalImage;
import edu.project4.utils.Domain;
import edu.project4.variationgenerators.Transformation;
import java.util.List;

@FunctionalInterface
public interface Renderer {
    FractalImage render(
        FractalImage canvas,
        List<Transformation> variations,
        Domain domain,
        int samples, short iterPerSample, long seed
    );
}
