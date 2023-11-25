package edu.hw4;

import edu.hw4.task19.ValidationError;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.MapAssert.assertThatMap;

public class TaskTest {
    // -- Task 1 --
    @Test
    @DisplayName("Задача 1: непустая не отсортированная коллекция")
    public void test1() {
        Animal testDogBig = new Animal(
            "BigDog",
            Animal.Type.DOG, Animal.Sex.M,
            10, 100, 100,
            true
        );
        Animal testDogMedium = new Animal(
            "MediumDog",
            Animal.Type.DOG, Animal.Sex.M,
            10, 50, 50,
            true
        );
        Animal testDogSmall = new Animal(
            "SmallDog",
            Animal.Type.DOG, Animal.Sex.M,
            10, 10, 10,
            true
        );

        final List<Animal> source = List.of(
            testDogMedium, testDogBig, testDogBig, testDogSmall
        );
        final var sorted = Task.sortByHeightAscendingOrder(source);
        final var expectedOrder = List.of(
            testDogSmall, testDogMedium, testDogBig, testDogBig
        );

        assertThat(expectedOrder).isEqualTo(sorted);
    }

    @Test
    @DisplayName("Задача 1: непустая отсортированная коллекция")
    public void test2() {
        Animal testDogBig = new Animal(
            "BigDog",
            Animal.Type.DOG, Animal.Sex.M,
            10, 100, 100,
            true
        );
        Animal testDogMedium = new Animal(
            "MediumDog",
            Animal.Type.DOG, Animal.Sex.M,
            10, 50, 50,
            true
        );
        Animal testDogSmall = new Animal(
            "SmallDog",
            Animal.Type.DOG, Animal.Sex.M,
            10, 10, 10,
            true
        );

        final List<Animal> source = List.of(
            testDogSmall, testDogMedium, testDogBig, testDogBig
        );
        final var sorted = Task.sortByHeightAscendingOrder(source);
        final var expectedOrder = List.of(
            testDogSmall, testDogMedium, testDogBig, testDogBig
        );

        assertThat(expectedOrder).isEqualTo(sorted);
    }

    @Test
    @DisplayName("Задача 1: непустая отсортированная в обратном порядке коллекция")
    public void test3() {
        Animal testDogBig = new Animal(
            "BigDog",
            Animal.Type.DOG, Animal.Sex.M,
            10, 100, 100,
            true
        );
        Animal testDogMedium = new Animal(
            "MediumDog",
            Animal.Type.DOG, Animal.Sex.M,
            10, 50, 50,
            true
        );
        Animal testDogSmall = new Animal(
            "SmallDog",
            Animal.Type.DOG, Animal.Sex.M,
            10, 10, 10,
            true
        );

        final List<Animal> source = List.of(
            testDogBig, testDogBig, testDogMedium, testDogSmall
        );
        final var sorted = Task.sortByHeightAscendingOrder(source);
        final var expectedOrder = List.of(
            testDogSmall, testDogMedium, testDogBig, testDogBig
        );

        assertThat(expectedOrder).isEqualTo(sorted);
    }

    @Test
    @DisplayName("Задача 1: пустая коллекция")
    public void test4() {
        final List<Animal> source = new ArrayList<>();
        final var sorted = Task.sortByHeightAscendingOrder(source);
        final var expectedOrder = List.of();

        assertThat(expectedOrder).isEqualTo(sorted);
    }

    // -- Task 2 --
    @Test
    @DisplayName("Задача 2: непустая не отсортированная коллекция; выбрать k < n (n - число животных к сортировке)")
    public void test5() {
        Animal testDogBig = new Animal(
            "BigDog",
            Animal.Type.DOG, Animal.Sex.M,
            10, 100, 100,
            true
        );
        Animal testDogMedium = new Animal(
            "MediumDog",
            Animal.Type.DOG, Animal.Sex.M,
            10, 50, 50,
            true
        );
        Animal testDogSmall = new Animal(
            "SmallDog",
            Animal.Type.DOG, Animal.Sex.M,
            10, 10, 10,
            true
        );

        final int k = 3;

        final List<Animal> source = List.of(
            testDogMedium, testDogBig, testDogBig, testDogSmall
        );
        final var sorted = Task.sortByWeightDescendingOrder(source, k);
        final var expectedOrder = List.of(
            testDogBig, testDogBig, testDogMedium
            );

        assertThat(expectedOrder).isEqualTo(sorted);
    }

    @Test
    @DisplayName("Задача 2: непустая не отсортированная коллекция; выбрать k = n (n - число животных к сортировке)")
    public void test6() {
        Animal testDogBig = new Animal(
            "BigDog",
            Animal.Type.DOG, Animal.Sex.M,
            10, 100, 100,
            true
        );
        Animal testDogMedium = new Animal(
            "MediumDog",
            Animal.Type.DOG, Animal.Sex.M,
            10, 50, 50,
            true
        );
        Animal testDogSmall = new Animal(
            "SmallDog",
            Animal.Type.DOG, Animal.Sex.M,
            10, 10, 10,
            true
        );

        final int k = 4;

        final List<Animal> source = List.of(
            testDogMedium, testDogBig, testDogBig, testDogSmall
        );
        final var sorted = Task.sortByWeightDescendingOrder(source, k);
        final var expectedOrder = List.of(
            testDogBig, testDogBig, testDogMedium, testDogSmall
        );

        assertThat(expectedOrder).isEqualTo(sorted);
    }

    @Test
    @DisplayName("Задача 2: непустая не отсортированная коллекция; выбрать k > n (n - число животных к сортировке)")
    public void test7() {
        Animal testDogBig = new Animal(
            "BigDog",
            Animal.Type.DOG, Animal.Sex.M,
            10, 100, 100,
            true
        );
        Animal testDogMedium = new Animal(
            "MediumDog",
            Animal.Type.DOG, Animal.Sex.M,
            10, 50, 50,
            true
        );
        Animal testDogSmall = new Animal(
            "SmallDog",
            Animal.Type.DOG, Animal.Sex.M,
            10, 10, 10,
            true
        );

        final int k = 5;

        final List<Animal> source = List.of(
            testDogMedium, testDogBig, testDogBig, testDogSmall
        );
        final var sorted = Task.sortByWeightDescendingOrder(source, k);
        final var expectedOrder = List.of(
            testDogBig, testDogBig, testDogMedium, testDogSmall
        );

        assertThat(expectedOrder).isEqualTo(sorted);
    }

    @Test
    @DisplayName("Задача 2: непустая отсортированная коллекция; выбрать k < n (n - число животных к сортировке)")
    public void test8() {
        Animal testDogBig = new Animal(
            "BigDog",
            Animal.Type.DOG, Animal.Sex.M,
            10, 100, 100,
            true
        );
        Animal testDogMedium = new Animal(
            "MediumDog",
            Animal.Type.DOG, Animal.Sex.M,
            10, 50, 50,
            true
        );
        Animal testDogSmall = new Animal(
            "SmallDog",
            Animal.Type.DOG, Animal.Sex.M,
            10, 10, 10,
            true
        );

        final int k = 2;

        final List<Animal> source = List.of(
            testDogBig, testDogBig, testDogMedium, testDogSmall
        );
        final var sorted = Task.sortByWeightDescendingOrder(source, k);
        final var expectedOrder = List.of(testDogBig, testDogBig);

        assertThat(expectedOrder).isEqualTo(sorted);
    }

    @Test
    @DisplayName("Задача 2: не пустая коллекция, взять первые 0 элементов")
    public void test9() {
        Animal testDogBig = new Animal(
            "BigDog",
            Animal.Type.DOG, Animal.Sex.M,
            10, 100, 100,
            true
        );
        Animal testDogMedium = new Animal(
            "MediumDog",
            Animal.Type.DOG, Animal.Sex.M,
            10, 50, 50,
            true
        );
        Animal testDogSmall = new Animal(
            "SmallDog",
            Animal.Type.DOG, Animal.Sex.M,
            10, 10, 10,
            true
        );

        final int k = 0;

        final List<Animal> source = List.of(
            testDogMedium, testDogBig, testDogBig, testDogSmall
        );
        final var sorted = Task.sortByWeightDescendingOrder(source, k);
        final var expectedOrder = List.of();

        assertThat(expectedOrder).isEqualTo(sorted);
    }

    @Test
    @DisplayName("Задача 2: пустая коллекция")
    public void test10() {
        final List<Animal> source = new ArrayList<>();
        final var sorted = Task.sortByWeightDescendingOrder(source, 100);
        final var expectedOrder = List.of();

        assertThat(expectedOrder).isEqualTo(sorted);
    }

    // -- Task 3 --
    @Test
    @DisplayName("Задача 3: пустая коллекция")
    public void test11() {
        final List<Animal> source = new ArrayList<>();
        final var grouped = Task.countAnimalsByType(source);
        final var expected = new HashMap<Animal, Integer>();

        assertThat(expected).isEqualTo(grouped);
    }

    @Test
    @DisplayName("Задача 3: не пустая коллекция")
    public void test12() {
        Animal dog1 = new Animal(
            "BigDog",
            Animal.Type.DOG, Animal.Sex.M,
            10, 100, 100,
            true
        );
        Animal dog2 = new Animal(
            "MediumDog",
            Animal.Type.DOG, Animal.Sex.M,
            10, 50, 50,
            true
        );
        Animal dog3 = new Animal(
            "SmallDog",
            Animal.Type.DOG, Animal.Sex.M,
            10, 10, 10,
            true
        );

        Animal bird = new Animal(
            "BigDog",
            Animal.Type.BIRD, Animal.Sex.M,
            10, 100, 100,
            true
        );


        Animal cat1 = new Animal(
            "MediumDog",
            Animal.Type.CAT, Animal.Sex.M,
            10, 50, 50,
            true
        );
        Animal cat2 = new Animal(
            "SmallDog",
            Animal.Type.CAT, Animal.Sex.M,
            10, 10, 10,
            true
        );

        Animal fish = new Animal(
            "SmallDog",
            Animal.Type.FISH, Animal.Sex.M,
            10, 10, 10,
            true
        );

        final List<Animal> source = List.of(
            dog1, dog2, dog3,
            bird,
            cat1, cat2,
            fish
        );
        final var grouped = Task.countAnimalsByType(source);

        final var expected = new HashMap<Animal.Type, Integer>();
        expected.put(Animal.Type.DOG, 3);
        expected.put(Animal.Type.BIRD, 1);
        expected.put(Animal.Type.CAT, 2);
        expected.put(Animal.Type.FISH, 1);

        assertThat(expected).isEqualTo(grouped);
    }

    // -- Task 4 --
    @Test
    @DisplayName("Задача 4: пустая коллекция")
    public void test13() {
        final var longestNameAnimal = Task.getWithLongestName(new ArrayList<>());

        assertThat(longestNameAnimal).isNull();
    }

    @Test
    @DisplayName("Задача 4: не пустая коллекция")
    public void test14() {
        Animal dog1 = new Animal(
            "ShortName",
            Animal.Type.DOG, Animal.Sex.M,
            10, 100, 100,
            true
        );
        Animal dog2 = new Animal(
            "MeeeediumName",
            Animal.Type.DOG, Animal.Sex.M,
            10, 50, 50,
            true
        );
        Animal dog3 = new Animal(
            "LooooooooooongName",
            Animal.Type.DOG, Animal.Sex.M,
            10, 10, 10,
            true
        );

        final List<Animal> source = List.of(dog1, dog2, dog3);
        final Animal longestNameAnimal = Task.getWithLongestName(source);

        assertThat(longestNameAnimal).isEqualTo(dog3);
    }

    // -- Task 5 --
    @Test
    @DisplayName("Задача 5: пустая коллекция")
    public void test15() {
        final var sex = Task.getMostFrequencyGender(new ArrayList<>());
        assertThat(sex).isNull();
    }

    @Test
    @DisplayName("Задача 5: не пустая коллекция")
    public void test16() {
        Animal male1 = new Animal(
            "", Animal.Type.DOG, Animal.Sex.M,
            10, 100, 100, true
        );
        Animal male2 = new Animal(
            "", Animal.Type.DOG, Animal.Sex.M,
            10, 50, 50, true
        );
        Animal male3 = new Animal(
            "", Animal.Type.DOG, Animal.Sex.M,
            10, 10, 10, true
        );

        Animal female1 = new Animal(
            "", Animal.Type.DOG, Animal.Sex.M,
            10, 10, 10, true
        );

        Animal female2 = new Animal(
            "", Animal.Type.DOG, Animal.Sex.M,
            10, 10, 10, true
        );


        final var sex = Task.getMostFrequencyGender(
            List.of(
                male1, male2, male3,
                female1, female2
            )
        );
        assertThat(sex).isEqualTo(Animal.Sex.M);
    }

    // -- Task 6 --
    @Test
    @DisplayName("Задача 6: пустая коллекция")
    public void test17() {
        final var sex = Task.getMostHeavyAnimalOfEveryType(new ArrayList<>());
        assertThat(sex).isEqualTo(new HashMap<>());
    }

    @Test
    @DisplayName("Задача 6: пустая коллекция")
    public void test18() {
        Animal dog1 = new Animal(
            "Heaviest",
            Animal.Type.DOG, Animal.Sex.M,
            10, 100, 100,
            true
        );
        Animal dog2 = new Animal(
            "",
            Animal.Type.DOG, Animal.Sex.M,
            10, 50, 50,
            true
        );
        Animal dog3 = new Animal(
            "",
            Animal.Type.DOG, Animal.Sex.M,
            10, 10, 10,
            true
        );

        Animal bird = new Animal(
            "Heaviest",
            Animal.Type.BIRD, Animal.Sex.M,
            10, 100, 100,
            true
        );


        Animal cat1 = new Animal(
            "Heaviest",
            Animal.Type.CAT, Animal.Sex.M,
            10, 50, 50,
            true
        );
        Animal cat2 = new Animal(
            "",
            Animal.Type.CAT, Animal.Sex.M,
            10, 10, 10,
            true
        );

        Animal fish = new Animal(
            "Heaviest",
            Animal.Type.FISH, Animal.Sex.M,
            10, 10, 10,
            true
        );

        final var result = Task.getMostHeavyAnimalOfEveryType(
            List.of(
                dog1, dog2, dog3, bird, cat1, cat2, fish
            )
        );
        final var expected = Map.of(
            Animal.Type.DOG, dog1,
            Animal.Type.BIRD, bird,
            Animal.Type.CAT, cat1,
            Animal.Type.FISH, fish
        );
        assertThatMap(result).containsAllEntriesOf(expected);
    }

    // -- Task 7 --
    @Test
    @DisplayName("Задача 7: пустая коллекция")
    public void test19() {
        final var oldest = Task.getOlderAnimal(new ArrayList<>());
        assertThat(oldest).isEqualTo(null);
    }

    @Test
    @DisplayName("Задача 7: пустая коллекция")
    public void test20() {
        Animal dog1 = new Animal(
            "Oldest",
            Animal.Type.DOG, Animal.Sex.M,
            10000, 100, 100,
            true
        );
        Animal dog2 = new Animal(
            "",
            Animal.Type.DOG, Animal.Sex.M,
            10, 50, 50,
            true
        );
        Animal dog3 = new Animal(
            "",
            Animal.Type.DOG, Animal.Sex.M,
            10, 10, 10,
            true
        );

        Animal bird = new Animal(
            "",
            Animal.Type.BIRD, Animal.Sex.M,
            10, 100, 100,
            true
        );


        Animal cat1 = new Animal(
            "",
            Animal.Type.CAT, Animal.Sex.M,
            10, 50, 50,
            true
        );
        Animal cat2 = new Animal(
            "",
            Animal.Type.CAT, Animal.Sex.M,
            10, 10, 10,
            true
        );

        Animal fish = new Animal(
            "",
            Animal.Type.FISH, Animal.Sex.M,
            10, 10, 10,
            true
        );

        final var oldest = Task.getOlderAnimal(
            List.of(
                dog1, dog2, dog3, bird, cat1, cat2, fish
            )
        );
        assertThat(oldest).isEqualTo(dog1);
    }

    // -- Task 8 --
    @Test
    @DisplayName("Задача 8: пустая коллекция")
    public void test21() {
        final var res = Task.getHeaviestAnimalShorterThan(new ArrayList<>(), 1);
        assertThat(res).isEqualTo(Optional.empty());
    }

    @Test
    @DisplayName("Задача 8: не пустая коллекция")
    public void test22() {
        Animal dog1 = new Animal(
            "Oldest",
            Animal.Type.DOG, Animal.Sex.M,
            10000, 100, 1000,
            true
        );
        Animal dog2 = new Animal(
            "Oldest",
            Animal.Type.DOG, Animal.Sex.M,
            10000, 100, 500,
            true
        );
        Animal dog3 = new Animal(
            "Oldest",
            Animal.Type.DOG, Animal.Sex.M,
            10000, 5, 100,
            true
        );
        Animal dog4 = new Animal(
            "Oldest",
            Animal.Type.DOG, Animal.Sex.M,
            10000, 3, 101,
            true
        );

        final int height = 10;

        final var res = Task.getHeaviestAnimalShorterThan(
            List.of(dog1, dog2, dog3, dog4),
            height
        );
        assertThat(res).isEqualTo(Optional.of(dog4));
    }

    // -- Task 9 --
    @Test
    @DisplayName("Задача 9: пустая коллекция")
    public void test23() {
        final var res = Task.getCountOfPaws(new ArrayList<>());
        assertThat(res).isEqualTo(0);
    }

    @Test
    @DisplayName("Задача 9: не пустая коллекция")
    public void test24() {
        Animal dog1 = new Animal(
            "Oldest",
            Animal.Type.DOG, Animal.Sex.M,
            10000, 100, 1000,
            true
        );
        Animal dog2 = new Animal(
            "Oldest",
            Animal.Type.DOG, Animal.Sex.M,
            10000, 100, 500,
            true
        );
        Animal dog3 = new Animal(
            "Oldest",
            Animal.Type.DOG, Animal.Sex.M,
            10000, 5, 100,
            true
        );
        Animal dog4 = new Animal(
            "Oldest",
            Animal.Type.DOG, Animal.Sex.M,
            10000, 3, 101,
            true
        );

        final var res = Task.getCountOfPaws(
            List.of(dog1, dog2, dog3, dog4)
        );
        assertThat(res).isEqualTo(16);
    }

    // -- Task 10 --
    @Test
    @DisplayName("Задача 10: пустая коллекция")
    public void test25() {
        final var res = Task.getAnimalsWithAgeNotEqualPawsCount(new ArrayList<>());
        assertThat(res).isEqualTo(List.of());
    }

    @Test
    @DisplayName("Задача 10: не пустая коллекция")
    public void test26() {
        Animal dog1 = new Animal(
            "Oldest",
            Animal.Type.DOG, Animal.Sex.M,
            4, 100, 1000,
            true
        );
        Animal dog2 = new Animal(
            "Oldest",
            Animal.Type.DOG, Animal.Sex.M,
            3, 100, 500,
            true
        );
        Animal dog3 = new Animal(
            "Oldest",
            Animal.Type.DOG, Animal.Sex.M,
            4, 5, 100,
            true
        );
        Animal dog4 = new Animal(
            "Oldest",
            Animal.Type.DOG, Animal.Sex.M,
            1, 3, 101,
            true
        );

        final var res = Task.getAnimalsWithAgeNotEqualPawsCount(
            List.of(dog1, dog2, dog3, dog4)
        );
        assertThat(res).isEqualTo(List.of(dog2, dog4));
    }

    // -- Task 11 --
    @Test
    @DisplayName("Задача 11: пустая коллекция")
    public void test27() {
        final var res = Task.getAnimalsWithAgeNotEqualPawsCount(new ArrayList<>());
        assertThat(res).isEqualTo(List.of());
    }

    @Test
    @DisplayName("Задача 11: не пустая коллекция")
    public void test28() {
        Animal dog1 = new Animal(
            "Oldest",
            Animal.Type.DOG, Animal.Sex.M,
            4, 101, 1000,
            true
        );
        Animal dog2 = new Animal(
            "Oldest",
            Animal.Type.DOG, Animal.Sex.M,
            3, 100, 500,
            false
        );
        Animal dog3 = new Animal(
            "Oldest",
            Animal.Type.DOG, Animal.Sex.M,
            4, 5, 100,
            true
        );
        Animal dog4 = new Animal(
            "Oldest",
            Animal.Type.DOG, Animal.Sex.M,
            1, 3, 101,
            true
        );


        final var res = Task.getAnimalsThatCanBiteAndHeightMoreThen100(
            List.of(dog1, dog2, dog3, dog4)
        );
        assertThat(res).isEqualTo(List.of(dog1));
    }

    // -- Task 12 --
    @Test
    @DisplayName("Задача 12: пустая коллекция")
    public void test29() {
        final var res = Task.getAnimalsWithAgeNotEqualPawsCount(new ArrayList<>());
        assertThat(res).isEqualTo(List.of());
    }

    @Test
    @DisplayName("Задача 12: не пустая коллекция")
    public void test30() {
        Animal dog1 = new Animal(
            "Oldest",
            Animal.Type.DOG, Animal.Sex.M,
            4, 101, 1000,
            true
        );
        Animal dog2 = new Animal(
            "Oldest",
            Animal.Type.DOG, Animal.Sex.M,
            3, 100, 100,
            false
        );
        Animal dog3 = new Animal(
            "Oldest",
            Animal.Type.DOG, Animal.Sex.M,
            4, 5, 3,
            true
        );
        Animal dog4 = new Animal(
            "Oldest",
            Animal.Type.DOG, Animal.Sex.M,
            1, 3, 101,
            true
        );


        final var res = Task.getCountOfAnimalsWithWeightMoreThanHeight(
            List.of(dog1, dog2, dog3, dog4)
        );
        assertThat(res).isEqualTo(2);
    }

    // -- Task 13 --
    @Test
    @DisplayName("Задача 13: пустая коллекция")
    public void test31() {
        final var res = Task.getAnimalsWithTwoWordName(new ArrayList<>());
        assertThat(res).isEqualTo(List.of());
    }

    @Test
    @DisplayName("Задача 13: не пустая коллекция")
    public void test32() {
        Animal dog1 = new Animal(
            "Two Words",
            Animal.Type.DOG, Animal.Sex.M,
            4, 101, 1000,
            true
        );
        Animal dog2 = new Animal(
            "Two Words",
            Animal.Type.DOG, Animal.Sex.M,
            3, 100, 100,
            false
        );
        Animal dog3 = new Animal(
            "OneWord",
            Animal.Type.DOG, Animal.Sex.M,
            4, 5, 3,
            true
        );
        Animal dog4 = new Animal(
            "Three short words",
            Animal.Type.DOG, Animal.Sex.M,
            1, 3, 101,
            true
        );


        final var res = Task.getAnimalsWithTwoWordName(
            List.of(dog1, dog2, dog3, dog4)
        );
        assertThat(res).isEqualTo(List.of(dog1, dog2));
    }

    // -- Task 14 --
    @Test
    @DisplayName("Задача 14: пустая коллекция")
    public void test33() {
        final var res = Task.checkExistsDogHigherThan(new ArrayList<>(), 100);
        assertThat(res).isFalse();
    }

    @Test
    @DisplayName("Задача 14: не пустая коллекция")
    public void test34() {
        Animal dog1 = new Animal(
            "Two Words",
            Animal.Type.DOG, Animal.Sex.M,
            4, 101, 1000,
            true
        );
        Animal dog2 = new Animal(
            "Two Words",
            Animal.Type.DOG, Animal.Sex.M,
            3, 100, 100,
            false
        );
        Animal dog3 = new Animal(
            "OneWord",
            Animal.Type.DOG, Animal.Sex.M,
            4, 5, 3,
            true
        );
        Animal dog4 = new Animal(
            "Three short words",
            Animal.Type.DOG, Animal.Sex.M,
            1, 3, 101,
            true
        );


        final var res = Task.checkExistsDogHigherThan(
            List.of(dog1, dog2, dog3, dog4),
            99
        );
        assertThat(res).isTrue();
    }

    // -- Task 15 --
    @Test
    @DisplayName("Задача 15: пустая коллекция")
    public void test35() {
        final var res = Task.getSummaryWeightOfEveryAnimalTypeWithAge(new ArrayList<>(), 1, 100);
        assertThat(res).isEqualTo(new HashMap<>());
    }

    @Test
    @DisplayName("Задача 15: не пустая коллекция")
    public void test36() {
        Animal dog1 = new Animal(
            "Two Words",
            Animal.Type.DOG, Animal.Sex.M,
            100, 101, 1000,
            true
        );
        Animal dog2 = new Animal(
            "Two Words",
            Animal.Type.DOG, Animal.Sex.M,
            3, 100, 100,
            false
        );
        Animal dog3 = new Animal(
            "OneWord",
            Animal.Type.DOG, Animal.Sex.M,
            4, 5, 3,
            true
        );
        Animal dog4 = new Animal(
            "Three short words",
            Animal.Type.DOG, Animal.Sex.M,
            0, 3, 101,
            true
        );


        final var res = Task.getSummaryWeightOfEveryAnimalTypeWithAge(
            List.of(dog1, dog2, dog3, dog4),
            1, 10
        );
        assertThat(res).isEqualTo(Map.of(Animal.Type.DOG, 103));
    }

    // -- Task 16 --
    @Test
    @DisplayName("Задача 16: пустая коллекция")
    public void test37() {
        final var res = Task.getAnimalsSortedByTypeAndGenderAndName(new ArrayList<>());
        assertThat(res).isEqualTo(List.of());
    }

    @Test
    @DisplayName("Задача 16: не пустая коллекция")
    public void test38() {
        Animal dog1 = new Animal(
            "A",
            Animal.Type.DOG, Animal.Sex.M,
            10, 100, 100,
            true
        );
        Animal dog2 = new Animal(
            "B",
            Animal.Type.DOG, Animal.Sex.M,
            10, 50, 50,
            true
        );
        Animal dog3 = new Animal(
            "C",
            Animal.Type.DOG, Animal.Sex.F,
            10, 10, 10,
            true
        );

        Animal bird = new Animal(
            "A",
            Animal.Type.BIRD, Animal.Sex.F,
            10, 100, 100,
            true
        );


        Animal cat1 = new Animal(
            "F",
            Animal.Type.CAT, Animal.Sex.M,
            10, 50, 50,
            true
        );
        Animal cat2 = new Animal(
            "C",
            Animal.Type.CAT, Animal.Sex.F,
            10, 10, 10,
            true
        );

        Animal fish = new Animal(
            "A",
            Animal.Type.FISH, Animal.Sex.M,
            10, 10, 10,
            true
        );


        final var res = Task.getAnimalsSortedByTypeAndGenderAndName(
            List.of(
                dog1, dog2, dog3,
                bird,
                cat1, cat2,
                fish
            )
        );
        assertThat(res).isEqualTo(
            List.of(
                cat1, cat2,
                dog1, dog2, dog3,
                bird,
                fish
            )
        );
    }

    @Test
    @DisplayName("Задача 16: не пустая коллекция, сортировка внутри одного типа")
    public void test38_1() {
        Animal dog1 = new Animal(
            "C",
            Animal.Type.DOG, Animal.Sex.F,
            10, 100, 100,
            true
        );
        Animal dog2 = new Animal(
            "B",
            Animal.Type.DOG, Animal.Sex.F,
            10, 50, 50,
            true
        );
        Animal dog3 = new Animal(
            "A",
            Animal.Type.DOG, Animal.Sex.F,
            10, 10, 10,
            true
        );

        Animal dog4 = new Animal(
            "C",
            Animal.Type.DOG, Animal.Sex.M,
            10, 10, 10,
            true
        );


        final var res = Task.getAnimalsSortedByTypeAndGenderAndName(
            List.of(
                dog1, dog2, dog3, dog4
            )
        );
        assertThat(res).isEqualTo(
            List.of(
                dog4, dog3, dog2, dog1
            )
        );
    }

    // -- Task 17 --
    @Test
    @DisplayName("Задача 17: пустая коллекция")
    public void test39() {
        final var res = Task.checkSpidersBytesFrequentThanDogs(new ArrayList<>());
        assertThat(res).isFalse();
    }

    @Test
    @DisplayName("Задача 17: не пустая коллекция; собаки кусают чаще")
    public void test40() {
        Animal dog1 = new Animal(
            "A",
            Animal.Type.DOG, Animal.Sex.M,
            10, 100, 100,
            true
        );
        Animal dog2 = new Animal(
            "B",
            Animal.Type.DOG, Animal.Sex.M,
            10, 50, 50,
            true
        );
        Animal dog3 = new Animal(
            "C",
            Animal.Type.DOG, Animal.Sex.F,
            10, 10, 10,
            true
        );


        Animal spyder1 = new Animal(
            "F",
            Animal.Type.CAT, Animal.Sex.M,
            10, 50, 50,
            false
        );
        Animal spyder2 = new Animal(
            "C",
            Animal.Type.CAT, Animal.Sex.F,
            10, 10, 10,
            true
        );

        Animal spyder3 = new Animal(
            "A",
            Animal.Type.FISH, Animal.Sex.M,
            10, 10, 10,
            false
        );

        Animal spyder4 = new Animal(
            "A",
            Animal.Type.FISH, Animal.Sex.M,
            10, 10, 10,
            false
        );


        final var res = Task.checkSpidersBytesFrequentThanDogs(
            List.of(
                dog1, dog2, dog3,
                spyder1, spyder2, spyder3, spyder4
            )
        );
        assertThat(res).isFalse();
    }

    @Test
    @DisplayName("Задача 17: не пустая коллекция; пауки кусают чаще")
    public void test41() {
        Animal dog1 = new Animal(
            "A",
            Animal.Type.DOG, Animal.Sex.M,
            10, 100, 100,
            false
        );
        Animal dog2 = new Animal(
            "B",
            Animal.Type.DOG, Animal.Sex.M,
            10, 50, 50,
            true
        );
        Animal dog3 = new Animal(
            "C",
            Animal.Type.DOG, Animal.Sex.F,
            10, 10, 10,
            true
        );
        Animal dog4 = new Animal(
            "C",
            Animal.Type.DOG, Animal.Sex.F,
            10, 10, 10,
            false
        );


        Animal spyder1 = new Animal(
            "F",
            Animal.Type.SPIDER, Animal.Sex.M,
            10, 50, 50,
            true
        );
        Animal spyder2 = new Animal(
            "C",
            Animal.Type.SPIDER, Animal.Sex.F,
            10, 10, 10,
            true
        );

        Animal spyder3 = new Animal(
            "A",
            Animal.Type.SPIDER, Animal.Sex.M,
            10, 10, 10,
            true
        );

        Animal spyder4 = new Animal(
            "A",
            Animal.Type.SPIDER, Animal.Sex.M,
            10, 10, 10,
            true
        );


        final var res = Task.checkSpidersBytesFrequentThanDogs(
            List.of(
                dog1, dog2, dog3, dog4,
                spyder1, spyder2, spyder3, spyder4
            )
        );
        assertThat(res).isTrue();
    }

    // -- Task 18 --
    @Test
    @DisplayName("Задача 18: пустая коллекция")
    public void test42() {
        final var res = Task.getHeaviestFish();
        assertThat(res).isNull();
    }

    @Test
    @DisplayName("Задача 18: не пустая коллекция; 1 лист, рыбы нет")
    public void test43() {
        Animal dog1 = new Animal(
            "A",
            Animal.Type.DOG, Animal.Sex.M,
            10, 100, 100,
            false
        );
        Animal dog2 = new Animal(
            "B",
            Animal.Type.DOG, Animal.Sex.M,
            10, 50, 50,
            true
        );
        Animal dog3 = new Animal(
            "C",
            Animal.Type.DOG, Animal.Sex.F,
            10, 10, 10,
            true
        );
        Animal dog4 = new Animal(
            "C",
            Animal.Type.DOG, Animal.Sex.F,
            10, 10, 10,
            false
        );


        Animal spyder1 = new Animal(
            "F",
            Animal.Type.SPIDER, Animal.Sex.M,
            10, 50, 50,
            true
        );
        Animal spyder2 = new Animal(
            "C",
            Animal.Type.SPIDER, Animal.Sex.F,
            10, 10, 10,
            true
        );

        Animal spyder3 = new Animal(
            "A",
            Animal.Type.SPIDER, Animal.Sex.M,
            10, 10, 10,
            true
        );

        Animal spyder4 = new Animal(
            "A",
            Animal.Type.SPIDER, Animal.Sex.M,
            10, 10, 10,
            true
        );


        final var res = Task.getHeaviestFish(
            List.of(
                dog1, dog2, dog3, dog4,
                spyder1, spyder2, spyder3, spyder4
            )
        );
        assertThat(res).isNull();
    }

    @Test
    @DisplayName("Задача 18: не пустая коллекция; 1 лист, обычный случай")
    public void test44() {
        Animal fish1 = new Animal(
            "A",
            Animal.Type.FISH, Animal.Sex.M,
            10, 100, 100,
            false
        );
        Animal fish2 = new Animal(
            "B",
            Animal.Type.FISH, Animal.Sex.M,
            10, 50, 50,
            true
        );
        Animal fish3 = new Animal(
            "C",
            Animal.Type.FISH, Animal.Sex.M,
            10, 10, 10,
            true
        );
        Animal fish4 = new Animal(
            "C",
            Animal.Type.FISH, Animal.Sex.M,
            10, 10, 10,
            false
        );
        Animal fish5 = new Animal(
            "F",
            Animal.Type.FISH, Animal.Sex.M,
            10, 50, 50,
            true
        );
        Animal fish6 = new Animal(
            "C",
            Animal.Type.FISH, Animal.Sex.M,
            10, 10, 10,
            true
        );
        Animal fish7 = new Animal(
            "A",
            Animal.Type.FISH, Animal.Sex.M,
            10, 10, 10,
            true
        );
        Animal ideTheFishOfMyDream = new Animal(
            "A",
            Animal.Type.FISH, Animal.Sex.M,
            10, 10, 10000000,
            true
        );


        final var res = Task.getHeaviestFish(
            List.of(
                fish1, fish2, fish3, fish4,
                fish5, fish6, fish7, ideTheFishOfMyDream
            )
        );
        assertThat(res).isEqualTo(ideTheFishOfMyDream);
    }

    @Test
    @DisplayName("Задача 18: не пустая коллекция; 2 листа, обычный случай")
    public void test45() {
        Animal fish1 = new Animal(
            "A",
            Animal.Type.FISH, Animal.Sex.M,
            10, 100, 100,
            false
        );
        Animal fish2 = new Animal(
            "B",
            Animal.Type.FISH, Animal.Sex.M,
            10, 50, 50,
            true
        );
        Animal fish3 = new Animal(
            "C",
            Animal.Type.FISH, Animal.Sex.M,
            10, 10, 10,
            true
        );
        Animal fish4 = new Animal(
            "C",
            Animal.Type.FISH, Animal.Sex.M,
            10, 10, 10,
            false
        );
        Animal fish5 = new Animal(
            "F",
            Animal.Type.FISH, Animal.Sex.M,
            10, 50, 50,
            true
        );
        Animal fish6 = new Animal(
            "C",
            Animal.Type.FISH, Animal.Sex.M,
            10, 10, 10,
            true
        );
        Animal fish7 = new Animal(
            "A",
            Animal.Type.FISH, Animal.Sex.M,
            10, 10, 10,
            true
        );
        Animal ideTheFishOfMyDream = new Animal(
            "A",
            Animal.Type.FISH, Animal.Sex.M,
            10, 10, 10000000,
            true
        );


        final var res = Task.getHeaviestFish(
            List.of(
                fish1, fish2, fish3, fish4
            ),
            List.of(
                fish5, fish6, fish7, ideTheFishOfMyDream
            )
        );
        assertThat(res).isEqualTo(ideTheFishOfMyDream);
    }

    @Test
    @DisplayName("Задача 18: не пустая коллекция; 4 листа, обычный случай")
    public void test46() {
        Animal fish1 = new Animal(
            "A",
            Animal.Type.FISH, Animal.Sex.M,
            10, 100, 100,
            false
        );
        Animal fish2 = new Animal(
            "B",
            Animal.Type.FISH, Animal.Sex.M,
            10, 50, 50,
            true
        );
        Animal fish3 = new Animal(
            "C",
            Animal.Type.FISH, Animal.Sex.M,
            10, 10, 10,
            true
        );
        Animal fish4 = new Animal(
            "C",
            Animal.Type.FISH, Animal.Sex.M,
            10, 10, 10,
            false
        );
        Animal fish5 = new Animal(
            "F",
            Animal.Type.FISH, Animal.Sex.M,
            10, 50, 50,
            true
        );
        Animal fish6 = new Animal(
            "C",
            Animal.Type.FISH, Animal.Sex.M,
            10, 10, 10,
            true
        );
        Animal fish7 = new Animal(
            "A",
            Animal.Type.FISH, Animal.Sex.M,
            10, 10, 10,
            true
        );
        Animal ideTheFishOfMyDream = new Animal(
            "A",
            Animal.Type.FISH, Animal.Sex.M,
            10, 10, 10000000,
            true
        );


        final var res = Task.getHeaviestFish(
            List.of(
                fish1, fish2
            ),
            List.of(
                fish3, fish4
            ),
            List.of(
                fish5, fish6
            ),
            List.of(
                fish7, ideTheFishOfMyDream
            )
        );
        assertThat(res).isEqualTo(ideTheFishOfMyDream);
    }

    // -- Task 19 --
    @Test
    @DisplayName("Задача 19: пустая коллекция")
    public void test47() {
        final var res = Task.getEntityWithErrors(new ArrayList<>());
        assertThat(res).isEqualTo(new HashMap<>());
    }

    @Test
    @DisplayName("Задача 19: не пустая коллекция")
    public void test48() {
        Animal normalAnimal = new Animal(
            "Normal",
            Animal.Type.FISH, Animal.Sex.M,
            1, 1, 1,
            false
        );

        Animal canBiteAnimal = new Animal(
            "Can Bite",
            Animal.Type.FISH, Animal.Sex.M,
            1, 1, 1,
            true
        );

        Animal bigWeightAnimal = new Animal(
            "Big Weight",
            Animal.Type.FISH, Animal.Sex.M,
            1, 1, 51,
            false
        );

        Animal toHighAnimal = new Animal(
            "To High",
            Animal.Type.FISH, Animal.Sex.M,
            1, 1000, 1,
            false
        );

        Animal toHighAndCanBiteAnimal = new Animal(
            "To High And Can Bite",
            Animal.Type.FISH, Animal.Sex.M,
            1, 1000, 1,
            true
        );

        final var res = Task.getEntityWithErrors(
            List.of(
                normalAnimal, canBiteAnimal,
                bigWeightAnimal, toHighAnimal,
                toHighAndCanBiteAnimal
            )
        );
        assertThat(res).isEqualTo(
            Map.of(
                canBiteAnimal.name(), Set.of(ValidationError.CAN_BITE),
                bigWeightAnimal.name(), Set.of(ValidationError.WEIGHT_INVALID_VALUE),
                toHighAnimal.name(), Set.of(ValidationError.TO_BIG),
                toHighAndCanBiteAnimal.name(), Set.of(ValidationError.CAN_BITE, ValidationError.TO_BIG)
            )
        );
    }
}
