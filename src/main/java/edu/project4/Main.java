package edu.project4;

import edu.project4.utils.ImageFormat;
import edu.project4.utils.ImageUtils;
import edu.project4.utils.Pixel;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        // TODO:
        //  1) --done-- отрисовать что-то и превратить в png;
        //  2) научиться получать коэффициенты для аффинных преобразований;
        //  3) реализация алгоритма с habr'а;
        //  4) разнести по функциям;
        //  5) полученный алгоритм - в SingleThreadRender

        Path output = Paths.get("").toAbsolutePath().getParent().resolve("example_image");

        Pixel[][] source = new Pixel[128][512];
        for (int i = 0; i < source.length; i++) {
            for (int j = 0; j < source[i].length; j++) {
                source[i][j] = new Pixel(j % 256, i % 256, (i + j) % 256, 0);
            }
        }

        FractalImage image = new FractalImage(source, source[0].length, source.length);
        ImageUtils.save(image, output, ImageFormat.BMP);
    }
}
