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

    default Color getColor() {
        return Color.of(0, 0, 0);
    }
}
