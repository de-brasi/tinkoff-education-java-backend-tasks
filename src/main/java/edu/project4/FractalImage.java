package edu.project4;

import edu.project4.utils.Color;
import edu.project4.utils.Pixel;

public record FractalImage(Pixel[][] data, int width, int height) {
    public static FractalImage create(int width, int height) {
        Color whiteColor = Color.of(255, 255, 255);
        return createWithBaseColor(width, height, whiteColor);
    }

    public static FractalImage createWithBaseColor(int width, int height, Color base) {
        Pixel[][] newData = new Pixel[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                newData[i][j] = new Pixel(Color.of(base));
            }
        }
        return new FractalImage(newData, width, height);
    }

    public boolean containsCoordinate(int x, int y) {
        return (0 <= y && y < height)
            && (0 <= x && x < width);
    }

    public Pixel coordinate(int x, int y) {
        return data[y][x];
    }

    public Pixel pixel(int x, int y) {
        return data[y][x];
    }

    private enum Axis {
        VERTICAL, HORIZONTAL
    }
}
