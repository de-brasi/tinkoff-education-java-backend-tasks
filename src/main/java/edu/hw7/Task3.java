package edu.hw7;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import edu.hw7.util.Person;
import edu.hw7.util.PersonDatabase;
import org.jetbrains.annotations.Nullable;

public class Task3 implements PersonDatabase {
    @Override
    public synchronized void add(Person person) {
        dataBase.put(person.id(), person);

        updateNameStorage(person);
        updatePhoneStorage(person);
        updateAddressStorage(person);
    }

    @Override
    public synchronized void delete(int id) {
        Person deleted = dataBase.remove(id);
        clearAddressStorage(deleted);
        clearNameStorage(deleted);
        clearPhoneStorage(deleted);
    }

    @Override
    public synchronized @Nullable Person findByName(String name) {
        var iterator = nameReverseIndexes.getOrDefault(name, new HashSet<>()).iterator();
        return getPersonByIndexIterator(iterator);
    }

    @Override
    public synchronized @Nullable Person findByAddress(String address) {
        var iterator = addressReverseIndexes.getOrDefault(address, new HashSet<>()).iterator();
        return getPersonByIndexIterator(iterator);
    }

    @Override
    public synchronized @Nullable Person findByPhone(String phone) {
        var iterator = phoneReverseIndexes.getOrDefault(phone, new HashSet<>()).iterator();
        return getPersonByIndexIterator(iterator);
    }

    private synchronized @Nullable Person getPersonByIndexIterator(Iterator<Integer> iterator) {
        if (iterator.hasNext()) {
            int index = iterator.next();
            return dataBase.get(index);
        }

        return null;
    }

    private synchronized void updateNameStorage(Person person) {
        if (!nameReverseIndexes.containsKey(person.name())) {
            nameReverseIndexes.put(person.name(), new HashSet<>());
        }

        nameReverseIndexes.get(person.name()).add(person.id());
    }

    private synchronized void updatePhoneStorage(Person person) {
        if (!phoneReverseIndexes.containsKey(person.phoneNumber())) {
            phoneReverseIndexes.put(person.phoneNumber(), new HashSet<>());
        }

        phoneReverseIndexes.get(person.phoneNumber()).add(person.id());
    }

    private synchronized void updateAddressStorage(Person person) {
        if (!addressReverseIndexes.containsKey(person.address())) {
            addressReverseIndexes.put(person.address(), new HashSet<>());
        }

        addressReverseIndexes.get(person.address()).add(person.id());
    }

    private synchronized void clearNameStorage(Person person) {
        nameReverseIndexes.getOrDefault(person.name(), new HashSet<>()).remove(person.id());
    }

    private synchronized void clearPhoneStorage(Person person) {
        nameReverseIndexes.getOrDefault(person.phoneNumber(), new HashSet<>()).remove(person.id());
    }

    private synchronized void clearAddressStorage(Person person) {
        nameReverseIndexes.getOrDefault(person.address(), new HashSet<>()).remove(person.id());
    }

    private final Map<Integer, Person> dataBase = new HashMap<>();
    private final Map<String, Set<Integer>> nameReverseIndexes = new HashMap<>();
    private final Map<String, Set<Integer>> phoneReverseIndexes = new HashMap<>();
    private final Map<String, Set<Integer>> addressReverseIndexes = new HashMap<>();
}
