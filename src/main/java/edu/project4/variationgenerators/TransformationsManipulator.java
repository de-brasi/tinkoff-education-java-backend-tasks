package edu.project4.variationgenerators;

import java.util.List;
import java.util.Random;

public class TransformationsManipulator {
    public TransformationsManipulator(List<Transformation> variations) {
        transformations = variations;
    }

    public Transformation getRandom() {
        return transformations.get(random.nextInt(0, transformations.size()));
    }

    private final List<Transformation> transformations;
    private final Random random = new Random();
}
