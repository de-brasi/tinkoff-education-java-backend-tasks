package edu.project4.postprocessors;

import edu.project4.FractalImage;
import edu.project4.utils.Color;

final public class SingleThreadLogarithmicGammaCorrector implements ImageProcessor {
    @Override
    public void process(FractalImage image, double gamma) {
        double max = 0.0;

        for (int row = 0; row < image.height(); row++) {
            for (int col = 0; col < image.width(); col++) {
                var currentPixel = image.pixel(col, row);
                if (currentPixel.getHitCount() != 0) {
                    currentPixel.normal = Math.log10(currentPixel.getHitCount());
                    max = Math.max(max, currentPixel.normal);
                }
            }
        }

        for (int row = 0; row < image.height(); row++) {
            for (int col = 0; col < image.width(); col++) {
                var currentPixel = image.pixel(col, row);

                if (currentPixel.getHitCount() == 0) {
                    continue;
                }

                currentPixel.normal /= max;
                double correctionCoefficient = Math.pow(currentPixel.normal, (1.0/gamma));

                int newRed = (int) Math.round(
                    currentPixel.getColor().r() * correctionCoefficient
                );
                int newGreen = (int) Math.round(
                    currentPixel.getColor().g() * correctionCoefficient
                );
                int newBlue = (int) Math.round(
                    currentPixel.getColor().b() * correctionCoefficient
                );

                currentPixel.setColor(Color.of(newRed, newGreen, newBlue));
            }
        }
    }
}
