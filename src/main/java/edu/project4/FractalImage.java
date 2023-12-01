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
                newData[i][j] = new Pixel(Color.of(base), 0);
            }
        }
        return new FractalImage(newData, width, height);
    }

    public boolean contains(int x, int y) {
        int absoluteX = getAbsoluteValueOfCoordinate(x, Axis.HORIZONTAL);
        int absoluteY = getAbsoluteValueOfCoordinate(y, Axis.VERTICAL);
        return (0 <= absoluteY && absoluteY < height)
            && (0 <= absoluteX && absoluteX < width);
    }

    public Pixel pixel(int x, int y) {
        int absoluteX = getAbsoluteValueOfCoordinate(x, Axis.HORIZONTAL);
        int absoluteY = getAbsoluteValueOfCoordinate(y, Axis.VERTICAL);
        return data[absoluteY][absoluteX];
    }

    // TODO: пахнет кринжем такая работа!
    public void changePixelTo(int x, int y, Pixel changeTo) {
        int absoluteX = getAbsoluteValueOfCoordinate(x, Axis.HORIZONTAL);
        int absoluteY = getAbsoluteValueOfCoordinate(y, Axis.VERTICAL);
        data[absoluteY][absoluteX] = changeTo;
    }

    private int getAbsoluteValueOfCoordinate(int coordinate, Axis value) {
        int axisSize;

        switch (value) {
            case VERTICAL -> {
                axisSize = this.height;
            }
            case HORIZONTAL -> {
                axisSize = this.width;
            }

            default -> {
                throw new IllegalArgumentException();
            }
        }

        int zeroCoordinate = (axisSize % 2 == 0) ? (axisSize / 2) : (axisSize / 2 + 1);
        int zeroCoordinateIndex = zeroCoordinate - 1;

        return zeroCoordinateIndex + coordinate;
    }

    private enum Axis {
        VERTICAL, HORIZONTAL
    }
}
