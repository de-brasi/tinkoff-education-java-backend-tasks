package edu.project4.utils;

public record RendererRunningConfig(
    int samplesCount,
    short missedIterationsCount,
    short mainIterationsCount,
    int symmetry
) {}
