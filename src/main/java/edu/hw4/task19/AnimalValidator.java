package edu.hw4.task19;

import edu.hw4.Animal;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class AnimalValidator {
    private AnimalValidator() {}

    public static boolean validate(Animal target) {
        for (ValidationFunc validationFunc: validators) {
            if (validationFunc.validate(target).isPresent()) {
                return false;
            }
        }
        return true;
    }

    public static Set<ValidationError> getErrors(Animal target) {
        var errors = new HashSet<ValidationError>();

        for (ValidationFunc validationFunc: validators) {
            var res = validationFunc.validate(target);
            res.ifPresent(errors::add);
        }

        return errors;
    }

    private static final Set<ValidationFunc> validators = Set.of(
        animal -> {
            return !animal.bites()
                ? Optional.empty()
                : Optional.of(ValidationError.CAN_BITE);
            },
        animal -> {
            return (animal.height() < 100)
                ? Optional.empty()
                : Optional.of(ValidationError.TO_BIG);
            },
        animal -> {
            return (animal.weight() < 50)
                ? Optional.empty()
                : Optional.of(ValidationError.WEIGHT_INVALID_VALUE);
        }
    );

    @FunctionalInterface
    private static interface ValidationFunc {
        public abstract Optional<ValidationError> validate(Animal animal);
    }
}
