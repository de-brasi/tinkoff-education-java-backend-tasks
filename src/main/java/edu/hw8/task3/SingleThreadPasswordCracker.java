package edu.hw8.task3;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SingleThreadPasswordCracker implements PasswordCracker {
    public SingleThreadPasswordCracker() {
        records = new HashMap<>();

        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException algorithmException) {
            LOGGER.info(algorithmException);
            throw new RuntimeException(algorithmException);
        }
    }

    @Override
    public void loadRecords(List<String> source) {
        for (var curRecord: source) {
            loadRecord(curRecord);
        }
    }

    @Override
    public void loadRecord(String newRecord) {
        var info = newRecord.split(" ");
        assert info.length == 2;

        final int name = 0;
        final int hash = 1;

        records.put(info[name], info[hash]);
    }

    @Override
    public Map<String, String> getCrackedRecords() {
        Map<String, String> cracked = new HashMap<>();

        for (var entry: records.entrySet()) {
            cracked.put(entry.getKey(), nextPassword(entry.getValue()));
        }

        return cracked;
    }

    private String nextPassword(String hash) {
        var possiblePasswords = List.of("");
        boolean passwordFound = false;
        String password = null;

        while (!passwordFound) {
            possiblePasswords = generatePasswords(possiblePasswords);

            for (var possiblePassword: possiblePasswords) {
                String possiblePasswordHash = getMD5Hash(possiblePassword);
                if (possiblePasswordHash.equals(hash)) {
                    passwordFound = true;
                    password = possiblePassword;
                    break;
                }
            }

        }

        return password;
    }

    private List<String> generatePasswords(List<String> seed) {
        List<String> res = new ArrayList<>();

        for (var prefix: seed) {
            for (var suffix: validPasswordSymbols) {
                res.add(prefix + String.valueOf(suffix));
            }
        }

        return res;
    }

    private String getMD5Hash(String source) {
        md.update(source.getBytes());
        byte[] digest = md.digest();
        StringBuilder result = new StringBuilder();
        for (byte b : digest) {
            result.append(String.format("%02x", b));
        }
        return result.toString();
    }

    private final Map<String, String> records;
    private final MessageDigest md;

    @SuppressWarnings("MagicNumber")
    private final char[] validPasswordSymbols = IntStream.concat(
            IntStream.rangeClosed(48, 57),
            IntStream.concat(
                IntStream.rangeClosed('a', 'z'),
                IntStream.rangeClosed('A', 'Z')
            )
        )
        .mapToObj(codePoint -> String.valueOf((char) codePoint))
        .collect(Collectors.joining())
        .toCharArray();

    private final static Logger LOGGER = LogManager.getLogger();
}
