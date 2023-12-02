package edu.project4.postprocessors;

import edu.project4.FractalImage;

@FunctionalInterface
public interface ImageProcessor {
    void process(FractalImage image);
}
