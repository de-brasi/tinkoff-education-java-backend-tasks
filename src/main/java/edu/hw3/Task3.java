package edu.hw3;

import java.util.HashMap;

public class Task3 {
    private Task3() {
        // not allowed
    }

    public static <T> HashMap<T, Integer> freqDict(T[] src) {
        var res = new HashMap<T, Integer>();

        for (T item : src) {
            if (!res.containsKey(item)) {
                res.put(item, 0);
            }

            res.put(item, res.get(item) + 1);
        }

        return res;
    }
}
