package edu.project4.variationgenerators;

import edu.project4.utils.Point;

public class NonLinearTransformationsGenerator {
    // TODO: сделать вариант с рандомными коэффициентами

    private NonLinearTransformationsGenerator() {}

    public static Transformation getSinusoidalTransformation() {
        return getSinusoidalTransformation(1, 1);
    }

    public static Transformation getSinusoidalTransformation(double xScale, double yScale) {
        Transformation result = (Point source) -> {
            return new Point(
                xScale * Math.sin(source.x()),
                yScale * Math.sin(source.y())
            );
        };

        return result.nonLinear();
    }

    public static Transformation getSphericalTransformation() {
        return getSphericalTransformation(1, 1);
    }

    public static Transformation getSphericalTransformation(double xScale, double yScale) {
        Transformation result = (Point source) -> {
            double newX = source.x() / (Math.pow(source.x(), 2) + Math.pow(source.y(), 2));
            double newY = source.y() / (Math.pow(source.x(), 2) + Math.pow(source.y(), 2));
            return new Point(
                xScale * newX,
                yScale * newY
            );
        };

        return result.nonLinear();
    }

    public static Transformation getPolarTransformation() {
        return getPolarTransformation(1, 1);
    }

    public static Transformation getPolarTransformation(double xScale, double yScale) {
        Transformation result = (Point source) -> {
            double newX = Math.atan(source.y() / source.x()) / Math.PI;
            double newY = Math.sqrt(Math.pow(source.x(), 2) + Math.pow(source.y(), 2)) - 1;
            return new Point(
                xScale * newX,
                yScale * newY
            );
        };

        return result.nonLinear();
    }

    public static Transformation getHeartTransformation() {
        return getHeartTransformation(1, 1);
    }

    public static Transformation getHeartTransformation(double xScale, double yScale) {
        Transformation result = (Point source) -> {
            double newX =
                Math.sqrt(Math.pow(source.x(), 2) + Math.pow(source.y(), 2))
                    * Math.sin(
                    Math.sqrt(Math.pow(source.x(), 2) + Math.pow(source.y(), 2))
                        * Math.atan(source.y()) / source.x()
                );
            double newY =
                -1 * Math.sqrt(Math.pow(source.x(), 2) + Math.pow(source.y(), 2))
                    * Math.cos(
                    Math.sqrt(Math.pow(source.x(), 2) + Math.pow(source.y(), 2))
                        * Math.atan(source.y()) / source.x()
                );
            return new Point(
                xScale * newX,
                yScale * newY
            );
        };

        return result.nonLinear();
    }

    public static Transformation getDiskTransformation() {
        return getDiskTransformation(1, 1);
    }

    public static Transformation getDiskTransformation(double xScale, double yScale) {
        Transformation result = (Point source) -> {
            double newX = (1 / Math.PI)
                * Math.atan(source.y() / source.x())
                * Math.sin(Math.PI * Math.sqrt(Math.pow(source.x(), 2) + Math.pow(source.y(), 2)));
            double newY = (1 / Math.PI)
                * Math.atan(source.y() / source.x())
                * Math.cos(Math.PI * Math.sqrt(Math.pow(source.x(), 2) + Math.pow(source.y(), 2)));
            return new Point(
                xScale * newX,
                yScale * newY
            );
        };

        return result.nonLinear();
    }
}
