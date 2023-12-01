package edu.project4.renders;

import edu.project4.FractalImage;
import edu.project4.utils.Rect;
import edu.project4.Transformation;
import java.util.List;

@FunctionalInterface
public interface Renderer {
    FractalImage render(
        FractalImage canvas,
        Rect world, List<Transformation> variations,
        int samples, short iterPerSample, long seed
    );
}
