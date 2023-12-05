package edu.project4.variationgenerators;

import edu.project4.utils.Color;
import edu.project4.utils.Point;
import java.util.function.Function;

@FunctionalInterface
public interface Transformation extends Function<Point, Point> {
    default Transformation withColor(Color toSet) {
        var sourceTransformation = this;
        var type = this.getType();
        var weight = this.getWeight();

        return new Transformation() {
            @Override
            public Point apply(Point point) {
                return sourceTransformation.apply(point);
            }

            @Override
            public Color getColor() {
                return toSet;
            }

            @Override
            public Type getType() {
                return type;
            }

            @Override
            public int getWeight() {
                return weight;
            }
        };
    }

    default Transformation linear() {
        var sourceTransformation = this;
        var color = this.getColor();
        var weight = this.getWeight();

        return new Transformation() {
            @Override
            public Point apply(Point point) {
                return sourceTransformation.apply(point);
            }

            @Override
            public Color getColor() {
                return color;
            }

            @Override
            public Type getType() {
                return Type.LINEAR;
            }

            @Override
            public int getWeight() {
                return weight;
            }
        };
    }

    default Transformation nonLinear() {
        var sourceTransformation = this;
        var color = this.getColor();
        var weight = this.getWeight();
        return new Transformation() {
            @Override
            public Point apply(Point point) {
                return sourceTransformation.apply(point);
            }

            @Override
            public Color getColor() {
                return color;
            }

            @Override
            public Type getType() {
                return Type.NON_LINEAR;
            }

            @Override
            public int getWeight() {
                return weight;
            }
        };
    }

    default Transformation withWeight(int weight) {
        var sourceTransformation = this;
        var color = this.getColor();
        var type = this.getType();

        return new Transformation() {
            @Override
            public Point apply(Point point) {
                return sourceTransformation.apply(point);
            }

            @Override
            public Color getColor() {
                return color;
            }

            @Override
            public Type getType() {
                return type;
            }


            @Override
            public int getWeight() {
                return weight;
            }
        };
    }

    default Color getColor() {
        return Color.of(0, 0, 0);
    }

    default Transformation.Type getType() {
        return Type.ANY;
    }

    default int getWeight() {
        return 1;
    }

    enum Type {
        LINEAR, NON_LINEAR, ANY
    }
}
