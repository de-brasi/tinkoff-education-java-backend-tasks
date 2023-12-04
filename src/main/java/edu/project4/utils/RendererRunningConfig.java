package edu.project4.utils;

import edu.project4.variationgenerators.Transformation;

public record RendererRunningConfig(
    short missedIterationsCount, Transformation.Type missedIterationsType,
    short mainIterationsCount, Transformation.Type mainIterationsType,
    int symmetry
) {}
