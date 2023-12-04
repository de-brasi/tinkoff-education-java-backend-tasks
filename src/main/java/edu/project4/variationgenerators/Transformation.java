package edu.project4.variationgenerators;

import edu.project4.utils.Color;
import edu.project4.utils.Point;
import java.util.function.Function;

@FunctionalInterface
public interface Transformation extends Function<Point, Point> {
    default Transformation withColor(Color toSet) {
        var sourceTransformation = this;
        return new Transformation() {
            @Override
            public Point apply(Point point) {
                return sourceTransformation.apply(point);
            }

            @Override
            public Color getColor() {
                return toSet;
            }
        };
    }

    default Transformation linear() {
        var sourceTransformation = this;
        return new Transformation() {
            @Override
            public Point apply(Point point) {
                return sourceTransformation.apply(point);
            }

            @Override
            public Color getColor() {
                return sourceTransformation.getColor();
            }

            @Override
            public Transformation withColor(Color toSet) {
                return sourceTransformation.withColor(toSet);
            }

            @Override
            public Type getType() {
                return Type.LINEAR;
            }
        };
    }

    default Transformation nonLinear() {
        var sourceTransformation = this;
        return new Transformation() {
            @Override
            public Point apply(Point point) {
                return sourceTransformation.apply(point);
            }

            @Override
            public Color getColor() {
                return sourceTransformation.getColor();
            }

            @Override
            public Transformation withColor(Color toSet) {
                return sourceTransformation.withColor(toSet);
            }

            @Override
            public Type getType() {
                return Type.NON_LINEAR;
            }
        };
    }

    default Color getColor() {
        return Color.of(0, 0, 0);
    }

    default Transformation.Type getType() {
        return Type.ANY;
    }

    enum Type {
        LINEAR, NON_LINEAR, ANY
    }
}
