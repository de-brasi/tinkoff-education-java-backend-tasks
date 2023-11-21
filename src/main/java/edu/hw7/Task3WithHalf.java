package edu.hw7;

import edu.hw7.util.Person;
import edu.hw7.util.PersonDatabase;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import org.jetbrains.annotations.Nullable;

public class Task3WithHalf implements PersonDatabase {
    @Override
    public void add(Person person) {
        locker.writeLock().lock();
        try {
            dataBase.put(person.id(), person);

            updateNameStorage(person);
            updatePhoneStorage(person);
            updateAddressStorage(person);
        } finally {
            locker.writeLock().unlock();
        }
    }

    @Override
    public void delete(int id) {
        locker.writeLock().lock();
        try {
            Person deleted = dataBase.remove(id);
            clearAddressStorage(deleted);
            clearNameStorage(deleted);
            clearPhoneStorage(deleted);
        } finally {
            locker.writeLock().unlock();
        }
    }

    @Override
    public @Nullable Person findByName(String name) {
        locker.readLock().lock();
        try {
            var iterator = nameReverseIndexes.getOrDefault(name, new HashSet<>()).iterator();
            return getPersonByIndexIterator(iterator);
        } finally {
            locker.readLock().unlock();
        }
    }

    @Override
    public @Nullable Person findByAddress(String address) {
        locker.readLock().lock();
        try {
            var iterator = addressReverseIndexes.getOrDefault(address, new HashSet<>()).iterator();
            return getPersonByIndexIterator(iterator);
        } finally {
            locker.readLock().unlock();
        }
    }

    @Override
    public @Nullable Person findByPhone(String phone) {
        locker.readLock().lock();
        try {
            var iterator = phoneReverseIndexes.getOrDefault(phone, new HashSet<>()).iterator();
            return getPersonByIndexIterator(iterator);
        } finally {
            locker.readLock().unlock();
        }
    }

    private @Nullable Person getPersonByIndexIterator(Iterator<Integer> iterator) {
        if (iterator.hasNext()) {
            int index = iterator.next();
            return dataBase.get(index);
        }

        return null;
    }

    private void updateNameStorage(Person person) {
        if (!nameReverseIndexes.containsKey(person.name())) {
            nameReverseIndexes.put(person.name(), new HashSet<>());
        }

        nameReverseIndexes.get(person.name()).add(person.id());
    }

    private void updatePhoneStorage(Person person) {
        if (!phoneReverseIndexes.containsKey(person.phoneNumber())) {
            phoneReverseIndexes.put(person.phoneNumber(), new HashSet<>());
        }

        phoneReverseIndexes.get(person.phoneNumber()).add(person.id());
    }

    private void updateAddressStorage(Person person) {
        if (!addressReverseIndexes.containsKey(person.address())) {
            addressReverseIndexes.put(person.address(), new HashSet<>());
        }

        addressReverseIndexes.get(person.address()).add(person.id());
    }

    private void clearNameStorage(Person person) {
        nameReverseIndexes.getOrDefault(person.name(), new HashSet<>()).remove(person.id());
    }

    private void clearPhoneStorage(Person person) {
        nameReverseIndexes.getOrDefault(person.phoneNumber(), new HashSet<>()).remove(person.id());
    }

    private void clearAddressStorage(Person person) {
        nameReverseIndexes.getOrDefault(person.address(), new HashSet<>()).remove(person.id());
    }

    private final Map<Integer, Person> dataBase = new HashMap<>();
    private final Map<String, Set<Integer>> nameReverseIndexes = new HashMap<>();
    private final Map<String, Set<Integer>> phoneReverseIndexes = new HashMap<>();
    private final Map<String, Set<Integer>> addressReverseIndexes = new HashMap<>();
    private final ReadWriteLock locker = new ReentrantReadWriteLock();
}
