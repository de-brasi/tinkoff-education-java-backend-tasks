package edu.project4.utils;

import edu.project4.FractalImage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public final class ImageUtils {
    private ImageUtils() {}

    public static void save(FractalImage image, Path filename, ImageFormat format) {
        BufferedImage bufferedImage = createBufferImageAndSetColors(image);
        tryToSaveImageInFormat(bufferedImage, filename, format);
    }

    private static BufferedImage createBufferImageAndSetColors(FractalImage source) {
        int width = source.width();
        int height = source.height();

        BufferedImage res = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Pixel curPixel = source.data()[y][x];
                int rgb = (curPixel.r() << 16) | (curPixel.g() << 8) | curPixel.b();
                res.setRGB(x, y, rgb);
            }
        }

        return res;
    }

    private static void tryToSaveImageInFormat(BufferedImage bufferedImage, Path filename, ImageFormat format) {
        try {
            File output = filename.toFile();
            ImageIO.write(bufferedImage, format.getStringRepresentation(), output);
            System.out.println("Изображение успешно сохранено в " + output.getAbsolutePath());
        } catch (IOException e) {
            LOGGER.info(e);
        }
    }

    private final static Logger LOGGER = LogManager.getLogger();
}
