package edu.project4.variationgenerators;

import edu.project4.utils.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TransformationsManipulator {
    public TransformationsManipulator(List<Transformation> variations) {
        ArrayList<Transformation> linearTransformations = new ArrayList<>();
        ArrayList<Transformation> nonLinearTransformations = new ArrayList<>();

        for (var variation: variations) {
            if (variation.getType() == Transformation.Type.LINEAR) {
                linearTransformations.add(variation);
            } else if (variation.getType() == Transformation.Type.NON_LINEAR) {
                nonLinearTransformations.add(variation);
            }
        }

        linearGetter = new RandomWeightedTransformationsGetter(linearTransformations);
        nonLinearGetter = new RandomWeightedTransformationsGetter(nonLinearTransformations);
        anyGetter = new RandomWeightedTransformationsGetter(variations);
    }

    public Transformation getRandom(Transformation.Type type) {
        return switch (type) {
            case ANY -> getRandom();
            case LINEAR -> getRandomLinear();
            case NON_LINEAR -> getRandomNonLinear();
            case null, default -> throw new RuntimeException("Unexpected argument!");
        };
    }

    public Transformation getRandom() {
        return anyGetter.getRandom();
    }

    public Transformation getRandomLinear() {
        return linearGetter.getRandom();
    }

    public Transformation getRandomNonLinear() {
        return nonLinearGetter.getRandom();
    }

    private final Random random = new Random();
    private final RandomWeightedTransformationsGetter linearGetter;
    private final RandomWeightedTransformationsGetter nonLinearGetter;
    private final RandomWeightedTransformationsGetter anyGetter;

    private static class RandomWeightedTransformationsGetter {
        RandomWeightedTransformationsGetter(List<Transformation> transformations) {
            totalWeight = transformations.stream().mapToInt(Transformation::getWeight).sum();
            this.transformations = new ArrayList<>();

            for (var transformation: transformations) {
                for (int i = 0; i < transformation.getWeight(); i++) {
                    this.transformations.add(transformation);
                }
            }
        }

        public Transformation getRandom() {
            if (transformations.isEmpty()) {
                return idempotentOperation;
            }

            return transformations.get(
                random.nextInt(0, totalWeight)
            );
        }

        private final int totalWeight;
        private final List<Transformation> transformations;
        private final Random random = new Random();
        private final Transformation idempotentOperation = (Point point) -> {return point;};
    }
}
