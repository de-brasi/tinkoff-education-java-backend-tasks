package edu.hw4;

import edu.hw4.task19.AnimalValidator;
import edu.hw4.task19.ValidationError;
import java.util.AbstractMap;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Task {
    private Task() {}

    public static List<Animal> sortByHeightAscendingOrder(List<Animal> source) {
        // Task-1
        return source
            .stream()
            .sorted(Comparator.comparingInt(Animal::height))
            .toList();
    }

    public static List<Animal> sortByWeightDescendingOrder(List<Animal> source, int count) {
        // Task-2
        return source
            .stream()
            .sorted(Comparator.comparingInt(Animal::weight).reversed())
            .limit(count)
            .toList();
    }

    public static Map<Animal.Type, Integer> countAnimalsByType(List<Animal> source) {
        // Task-3
        return source
            .stream()
            .collect(
                Collectors.groupingBy(
                    Animal::type, Collectors.summingInt(i -> 1)
                )
            );
    }

    public static Animal getWithLongestName(List<Animal> source) {
        // Task-4
        return source
            .stream()
            .sorted(Comparator.comparing(Animal::name))
            .toList()
            .stream()
            .findFirst()
            .orElse(null);
    }

    public static Animal.Sex getMostFrequencyGender(List<Animal> source) {
        // Task-5
        return source
            .stream()
            .collect(Collectors.groupingBy(Animal::sex, Collectors.summingInt(i -> 1)))
            .entrySet()
            .stream()
            .max(Map.Entry.comparingByValue())
            .map(Map.Entry::getKey)
            .orElse(null);
    }

    public static Map<Animal.Type, Animal> getMostHeavyAnimalOfEveryType(List<Animal> source) {
        // Task-6
        return source
            .stream()
            .collect(
                Collectors.groupingBy(Animal::type, Collectors.toList())
            )
            .entrySet()
            .stream()
            .map(
                e -> new AbstractMap.SimpleEntry<>(
                        e.getKey(),
                        e.getValue().stream().max(Comparator.comparingInt(Animal::weight)).orElse(null)
                )
            )
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public static Animal getOlderAnimal(List<Animal> source) {
        // Task-7
        return source
            .stream()
            .sorted(Comparator.comparingInt(Animal::age).reversed())
            .toList()
            .stream()
            .findFirst()
            .orElse(null);
    }

    public static Optional<Animal> getHeaviestAnimalShorterThan(List<Animal> source, int height) {
        // Task-8
        return source
            .stream()
            .filter(animal -> animal.height() < height)
            .max(Comparator.comparingInt(Animal::weight));
    }

    public static Integer getCountOfPaws(List<Animal> source) {
        // Task-9
        return source
            .stream()
            .map(Animal::paws)
            .reduce(0, Integer::sum);
    }

    public static List<Animal> getAnimalsWithAgeNotEqualPawsCount(List<Animal> source) {
        // Task-10
        return source
            .stream()
            .filter(animal -> animal.paws() != animal.age())
            .collect(Collectors.toList());
    }

    @SuppressWarnings("MagicNumber")
    public static List<Animal> getAnimalsThatCanBiteAndHeightMoreThen100(List<Animal> source) {
        // Task-11
        return source
            .stream()
            .filter(Animal::bites)
            .filter(animal -> animal.height() > 100)
            .collect(Collectors.toList());
    }

    public static Integer getCountOfAnimalsWithWeightMoreThanHeight(List<Animal> source) {
        // Task-12
        return Math.toIntExact(source.stream().filter(animal -> animal.weight() > animal.height()).count());
    }

    public static List<Animal> getAnimalsWithTwoWordName(List<Animal> source) {
        // Task-13
        return source.stream().filter(animal -> animal.name().split(" ").length == 2).toList();
    }

    public static boolean checkExistsDogHigherThan(List<Animal> source, int heightToCmp) {
        // Task-14
        return source
            .stream()
            .filter(animal -> animal.type() == Animal.Type.DOG)
            .anyMatch(animal -> animal.height() > heightToCmp);
    }

    public static Map<Animal.Type, Integer> getSummaryWeightOfEveryAnimalTypeWithAge(
        List<Animal> source, int origin, int bound) {
        // Task-15
        return source
            .stream()
            .filter(animal -> origin <= animal.age() && animal.age() <= bound)
            .collect(
                Collectors.groupingBy(
                    Animal::type, Collectors.summingInt(Animal::weight)
                )
            );
    }

    public static List<Animal> getAnimalsSortedByTypeAndGenderAndName(List<Animal> source) {
        // Task-16
        return source
            .stream()
            .sorted(
                Comparator
                    .comparing(Animal::type)
                    .thenComparing(Animal::sex)
                    .thenComparing(Animal::name)
            )
            .toList();
    }

    public static boolean checkSpidersBytesFrequentThanDogs(List<Animal> source) {
        // Task-17
        return source
            .stream()
            .filter(Animal::bites)
            .filter(animal -> animal.type() == Animal.Type.SPIDER)
            .count()
            > source
                .stream()
                .filter(Animal::bites)
                .filter(animal -> animal.type() == Animal.Type.DOG)
                .count();
    }

    @SafeVarargs public static Animal getHeaviestFish(List<Animal>... sources) {
        // Task-18
        return Stream
                .of(sources)
                .flatMap(List::stream)
                .filter(animal -> animal.type() == Animal.Type.FISH)
            .max(Comparator.comparingInt(Animal::weight))
            .orElse(null);
    }

    public static Map<String, Set<ValidationError>> getEntityWithErrors(List<Animal> source) {
        // Task-19
        return source
            .stream()
            .filter(animal -> !AnimalValidator.validate(animal))
            .collect(Collectors.toMap(Animal::name, AnimalValidator::getErrors));
    }

    public static Map<String, String> getEntityWithErrorsString(List<Animal> source) {
        // Task-20
        return source
            .stream()
            .filter(animal -> !AnimalValidator.validate(animal))
            .collect(
                Collectors.toMap(
                    Animal::name,
                    animal -> AnimalValidator.getErrors(animal)
                        .stream()
                        .map(ValidationError::getName)
                        .sorted()
                        .collect(Collectors.joining(", "))
                )
            );
    }
}
