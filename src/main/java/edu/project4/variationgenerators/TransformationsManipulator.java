package edu.project4.variationgenerators;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TransformationsManipulator {
    public TransformationsManipulator(List<Transformation> variations) {
        transformations = new ArrayList<>(variations);

        linearTransformations = new ArrayList<>();
        nonLinearTransformations = new ArrayList<>();

        for (var variation: variations) {
            if (variation.getType() == Transformation.Type.LINEAR) {
                linearTransformations.add(variation);
            } else if (variation.getType() == Transformation.Type.NON_LINEAR) {
                nonLinearTransformations.add(variation);
            }
        }
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
        // TODO: проверять что-то есть, иначе возвращать идемпотентную трансформацию
        return linearTransformations.get(
            random.nextInt(
                0,
                linearTransformations.size()
            )
        );
    }

    public Transformation getRandomNonLinear() {
        // TODO: проверять что-то есть, иначе возвращать идемпотентную трансформацию
        return nonLinearTransformations.get(
            random.nextInt(
                0,
                nonLinearTransformations.size()
            )
        );
    }

    private final ArrayList<Transformation> transformations;
    private final ArrayList<Transformation> linearTransformations;
    private final ArrayList<Transformation> nonLinearTransformations;
    private final Random random = new Random();
}
