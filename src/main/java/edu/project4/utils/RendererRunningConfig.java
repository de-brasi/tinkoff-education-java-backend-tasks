package edu.project4.utils;

import edu.project4.variationgenerators.Transformation;

public record RendererRunningConfig(
    int samplesCount,
    short missedIterationsCount,
    short mainIterationsCount,
    int symmetry
) {}
