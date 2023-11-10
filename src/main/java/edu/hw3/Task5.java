package edu.hw3;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;

public class Task5 {
    private Task5() {
        // not allowed
    }

    private static final String ASCENDING_ORDER = "ASC";
    private static final String DESCENDING_ORDER = "DESC";

    public static PersonInfo[] parseContacts(String[] contacts, final String order) {
        if (contacts == null) {
            return new PersonInfo[0];
        }

        var persons = getPersonsInfoFromContacts(contacts);
        Comparator<PersonInfo> cmp;

        if (order.equals(ASCENDING_ORDER)) {
            cmp = (lhs, rhs) -> {
                if (!lhs.lastName().isEmpty() && !rhs.lastName().isEmpty()) {
                    return lhs.lastName().compareTo(rhs.lastName());
                } else if (!lhs.lastName().isEmpty()) {
                    return lhs.lastName().compareTo(rhs.firstName());
                } else if (!rhs.lastName().isEmpty()) {
                    return lhs.firstName().compareTo(rhs.lastName());
                } else {
                    return lhs.firstName().compareTo(rhs.firstName());
                }
            };
        } else if (order.equals(DESCENDING_ORDER)) {
            cmp = (lhs, rhs) -> {
                if (!lhs.lastName().isEmpty() && !rhs.lastName().isEmpty()) {
                    return -1 * lhs.lastName().compareTo(rhs.lastName());
                } else if (!lhs.lastName().isEmpty()) {
                    return -1 * lhs.lastName().compareTo(rhs.firstName());
                } else if (!rhs.lastName().isEmpty()) {
                    return -1 * lhs.firstName().compareTo(rhs.lastName());
                } else {
                    return -1 * lhs.firstName().compareTo(rhs.firstName());
                }
            };
        } else {
            throw new IllegalArgumentException("Cant handle argument: " + order);
        }

        Arrays.sort(persons, cmp);

        return persons;
    }

    private static PersonInfo[] getPersonsInfoFromContacts(String[] contacts) {
        Objects.requireNonNull(contacts);       // throws NPE if object is null
        var persons = new PersonInfo[contacts.length];
        String[] personData;
        for (int i = 0; i < contacts.length; i++) {
            personData = contacts[i].split(" ");
            if (personData.length == 1) {
                persons[i] = new PersonInfo(personData[0], "");
            } else {
                persons[i] = new PersonInfo(personData[0], personData[1]);
            }
        }
        return persons;
    }
}
