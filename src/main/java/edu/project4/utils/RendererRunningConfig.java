package edu.project4.utils;

import edu.project4.variationgenerators.Transformation;

public record RendererRunningConfig(
    int missedIterationsCount, Transformation.Type missedIterationsType,
    int mainIterationsCount, Transformation.Type mainIterationsType,
    int symmetry
) {}
