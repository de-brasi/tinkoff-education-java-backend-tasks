package edu.hw8.task3;

import java.util.List;
import java.util.Map;

public interface PasswordCracker {
    void loadRecords(List<String> source);

    void loadRecord(String newRecord);

    Map<String, String> getCrackedRecords();
}
