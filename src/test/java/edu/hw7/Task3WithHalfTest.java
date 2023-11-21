package edu.hw7;

import edu.hw7.util.Person;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Task3WithHalfTest {
    @Test
    @DisplayName("Single thread usage: add method")
    public void test1() throws InterruptedException {
        var database = new Task3WithHalf();

        Person beautifulPerson = new Person(
            1,
            "BeautifulName",
            "BeautifulPlace",
            "BeautifulNumber"
        );

        database.add(beautifulPerson);

        assertThat(database.findByAddress("BeautifulPlace")).isEqualTo(beautifulPerson);
        assertThat(database.findByName("BeautifulName")).isEqualTo(beautifulPerson);
        assertThat(database.findByPhone("BeautifulNumber")).isEqualTo(beautifulPerson);
    }

    @Test
    @DisplayName("Single thread usage: not existing person")
    public void test2() throws InterruptedException {
        var database = new Task3WithHalf();

        Person beautifulPerson = new Person(
            1,
            "BeautifulName",
            "BeautifulPlace",
            "BeautifulNumber"
        );

        database.add(beautifulPerson);

        assertThat(database.findByAddress("notExistedPlace")).isEqualTo(null);
        assertThat(database.findByName("notExistedName")).isEqualTo(null);
        assertThat(database.findByPhone("notExistedNumber")).isEqualTo(null);

    }

    @Test
    @DisplayName("Single thread usage: delete method")
    public void test3() throws InterruptedException {
        var database = new Task3WithHalf();

        Person beautifulPerson = new Person(
            1,
            "BeautifulName",
            "BeautifulPlace",
            "BeautifulNumber"
        );

        database.add(beautifulPerson);
        database.delete(beautifulPerson.id());

        assertThat(database.findByAddress("BeautifulPlace")).isEqualTo(null);
        assertThat(database.findByName("BeautifulName")).isEqualTo(null);
        assertThat(database.findByPhone("BeautifulNumber")).isEqualTo(null);
    }

    @Test
    @DisplayName("Two threads concurrent call same add method")
    public void test4() throws InterruptedException {
        var database = new Task3WithHalf();
        final int batchSize = 1000;

        final Person[] personsForWorker1 = new Person[batchSize];
        final Person[] personsForWorker2 = new Person[batchSize];

        for (int i = 0; i < batchSize; i++) {
            personsForWorker1[i] =
                new Person(
                    i,
                    Integer.toString(i),
                    Integer.toString(i),
                    Integer.toString(i)
                );
        }

        for (int i = 0; i < batchSize; i++) {
            personsForWorker2[i] =
                new Person(
                    i + batchSize,
                    Integer.toString(i + batchSize),
                    Integer.toString(i + batchSize),
                    Integer.toString(i + batchSize)
                );
        }

        Thread worker1 = new Thread(
            () -> {
                for (var person: personsForWorker1) { database.add(person); }
            }
        );
        Thread worker2 = new Thread(
            () -> {
                for (var person: personsForWorker2) { database.add(person); }
            }
        );

        worker1.start();
        worker2.start();

        worker1.join();
        worker2.join();

        // By address
        assertThat(personsForWorker1).allMatch(
            (
                person -> {
                    return database.findByAddress(person.address()) != null;
                }
            )
        );
        assertThat(personsForWorker2).allMatch(
            (
                person -> {
                    return database.findByAddress(person.address()) != null;
                }
            )
        );

        // By name
        assertThat(personsForWorker1).allMatch(
            (
                person -> {
                    return database.findByName(person.name()) != null;
                }
            )
        );
        assertThat(personsForWorker2).allMatch(
            (
                person -> {
                    return database.findByName(person.name()) != null;
                }
            )
        );

        // By phone
        assertThat(personsForWorker1).allMatch(
            (
                person -> {
                    return database.findByPhone(person.phoneNumber()) != null;
                }
            )
        );
        assertThat(personsForWorker2).allMatch(
            (
                person -> {
                    return database.findByPhone(person.phoneNumber()) != null;
                }
            )
        );
    }

    @Test
    @DisplayName("Two threads concurrent call same delete method")
    public void test5() throws InterruptedException {
        var database = new Task3WithHalf();
        final int batchSize = 1000;

        final Person[] personsForWorker1 = new Person[batchSize];
        final Person[] personsForWorker2 = new Person[batchSize];

        for (int i = 0; i < batchSize; i++) {
            personsForWorker1[i] =
                new Person(
                    i,
                    Integer.toString(i),
                    Integer.toString(i),
                    Integer.toString(i)
                );
        }

        for (int i = 0; i < batchSize; i++) {
            personsForWorker2[i] =
                new Person(
                    i + batchSize,
                    Integer.toString(i + batchSize),
                    Integer.toString(i + batchSize),
                    Integer.toString(i + batchSize)
                );
        }

        // Add persons
        Thread worker1 = new Thread(
            () -> {
                for (var person: personsForWorker1) { database.add(person); }
            }
        );
        Thread worker2 = new Thread(
            () -> {
                for (var person: personsForWorker2) { database.add(person); }
            }
        );

        worker1.start();
        worker2.start();

        worker1.join();
        worker2.join();

        // Delete persons
        worker1 = new Thread(
            () -> {
                for (var person: personsForWorker1) { database.delete(person.id()); }
            }
        );
        worker2 = new Thread(
            () -> {
                for (var person: personsForWorker2) { database.delete(person.id()); }
            }
        );

        worker1.start();
        worker2.start();

        worker1.join();
        worker2.join();

        // By address
        assertThat(personsForWorker1).allMatch(
            (
                person -> {
                    return database.findByAddress(person.address()) == null;
                }
            )
        );
        assertThat(personsForWorker2).allMatch(
            (
                person -> {
                    return database.findByAddress(person.address()) == null;
                }
            )
        );

        // By name
        assertThat(personsForWorker1).allMatch(
            (
                person -> {
                    return database.findByName(person.name()) == null;
                }
            )
        );
        assertThat(personsForWorker2).allMatch(
            (
                person -> {
                    return database.findByName(person.name()) == null;
                }
            )
        );

        // By phone
        assertThat(personsForWorker1).allMatch(
            (
                person -> {
                    return database.findByPhone(person.phoneNumber()) == null;
                }
            )
        );
        assertThat(personsForWorker2).allMatch(
            (
                person -> {
                    return database.findByPhone(person.phoneNumber()) == null;
                }
            )
        );
    }

    @Test
    @DisplayName("Two threads concurrent call different method")
    public void test6() throws InterruptedException {
        var database = new Task3WithHalf();
        final int batchSize = 100;

        final Person[] personsForWorkers = new Person[batchSize];

        for (int i = 0; i < batchSize; i++) {
            personsForWorkers[i] =
                new Person(
                    i,
                    Integer.toString(i),
                    Integer.toString(i),
                    Integer.toString(i)
                );
        }

        // Workers routine
        Thread worker1 = new Thread(
            () -> {
                for (var person: personsForWorkers) { database.add(person); }
            }
        );
        Thread worker2 = new Thread(
            () -> {
                for (var person: personsForWorkers) {
                    database.findByPhone(person.phoneNumber());
                    database.findByAddress(person.address());
                    database.findByName(person.name());
                }
            }
        );

        worker1.start();
        worker2.start();

        worker1.join();
        worker2.join();


        // By address
        assertThat(personsForWorkers).allMatch(
            (
                person -> {
                    return database.findByAddress(person.address()) != null;
                }
            )
        );

        // By name
        assertThat(personsForWorkers).allMatch(
            (
                person -> {
                    return database.findByName(person.name()) != null;
                }
            )
        );


        // By phone
        assertThat(personsForWorkers).allMatch(
            (
                person -> {
                    return database.findByPhone(person.phoneNumber()) != null;
                }
            )
        );
    }
}
