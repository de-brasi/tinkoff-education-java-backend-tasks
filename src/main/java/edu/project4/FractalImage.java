package edu.project4;

import edu.project4.utils.Pixel;

public record FractalImage(Pixel[][] data, int width, int height) {
    public static FractalImage create(int width, int height) {
        Pixel[][] newData = new Pixel[height][width];
        return new FractalImage(newData, width, height);
    }

    boolean contains(int x, int y) {
        int absoluteX = getAbsoluteValueOfCoordinate(x, Axis.HORIZONTAL);
        int absoluteY = getAbsoluteValueOfCoordinate(y, Axis.VERTICAL);
        return (0 <= absoluteY && absoluteY < height) && (0 <= absoluteX && absoluteX < width);
    }

    Pixel pixel(int x, int y) {
        int absoluteX = getAbsoluteValueOfCoordinate(x, Axis.HORIZONTAL);
        int absoluteY = getAbsoluteValueOfCoordinate(y, Axis.VERTICAL);
        return data[absoluteY][absoluteX];
    }

    private int getAbsoluteValueOfCoordinate(int coordinate, Axis value) {
        int axisSize;

        switch (value) {
            case VERTICAL -> {
                axisSize = this.width;
            }
            case HORIZONTAL -> {
                axisSize = this.height;
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
