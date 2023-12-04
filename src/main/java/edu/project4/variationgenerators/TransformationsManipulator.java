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

    public Transformation getRandom(Transformation.Type type) {
        return switch (type) {
            case ANY -> getRandom();
            case LINEAR -> getRandomLinear();
            case NON_LINEAR -> getRandomNonLinear();
            case null, default -> throw new RuntimeException("Unexpected argument!");
        };
    }

    public Transformation getRandomLinear() {
        // TODO
        return transformations.get(random.nextInt(0, transformations.size()));
    }

    public Transformation getRandomNonLinear() {
        // TODO
        return transformations.get(random.nextInt(0, transformations.size()));
    }

    private final List<Transformation> transformations;
    private final Random random = new Random();
}
