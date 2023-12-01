package edu.project4;

import edu.project4.utils.Point;
import java.util.function.Function;

@FunctionalInterface
public interface Transformation extends Function<Point, Point> {}
