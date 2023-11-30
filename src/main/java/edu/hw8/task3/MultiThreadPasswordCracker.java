package edu.hw8.task3;

import jdk.dynalink.beans.StaticClass;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class MultiThreadPasswordCracker implements PasswordCracker {
    public MultiThreadPasswordCracker() {
        this(4);
    }

    public MultiThreadPasswordCracker(int nThreads) {
        records = new HashMap<>();
        threadPoolThreadsCount = nThreads;
        threadPool = Executors.newFixedThreadPool(nThreads);
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
            try {
                cracked.put(entry.getKey(), nextPassword(entry.getValue()));
                LOGGER.info("Password found for user: " + entry.getKey() + " -------------------");
            } catch (InterruptedException e) {
                LOGGER.info(e);
            }
        }

        return cracked;
    }

    private String nextPassword(String hash) throws InterruptedException {
        var passwordsSeed = List.of(new String(validPasswordSymbols).split(""));
        var dividedSeeds = divideFairly(passwordsSeed);
        String password;


        List<CrackRoutine> tasks = dividedSeeds.stream().map(seed -> new CrackRoutine(hash, seed)).toList();
        try {
            password = threadPool.invokeAny(tasks);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }

        return password;
    }

    private List<List<String>> divideFairly(List<String> source) {
        final int batchCount = threadPoolThreadsCount;
        List<List<String>> splitted =
            Stream.generate(ArrayList<String>::new)
                .limit(batchCount)
                .collect(Collectors.toList());

        for (int i = 0; i < source.size(); i++) {
            splitted.get(i % batchCount).add(source.get(i));
        }

        return splitted;
    }

    private final Map<String, String> records;
    private final int threadPoolThreadsCount;
    private final ExecutorService threadPool;

    @SuppressWarnings("MagicNumber")
    private final static char[] validPasswordSymbols = IntStream.concat(
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


    private static class CrackRoutine implements Callable<String> {
        CrackRoutine(String hash, List<String> seed) {
            this.hash = hash;
            this.seed = seed;

            try {
                md = MessageDigest.getInstance("MD5");
            } catch (NoSuchAlgorithmException algorithmException) {
                LOGGER.info(algorithmException);
                throw new RuntimeException(algorithmException);
            }
        }

        @Override
        public String call() throws InterruptedException {
            var passwordsSeed = seed;
            boolean passwordFound = false;
            String password = null;

            while (!passwordFound) {
                handleInterruption();

                LOGGER.info(
                    "Thread-worker %s interruption status: %s"
                        .formatted(
                            Thread.currentThread().threadId(),
                            Thread.currentThread().isInterrupted()
                        )
                );

                for (var possiblePassword: passwordsSeed) {

                    String possiblePasswordHash = getMD5Hash(possiblePassword);
                    if (possiblePasswordHash.equals(hash)) {

                        // TODO: debug only
                        LOGGER.info(
                            "Thread-worker %s found password!"
                                .formatted(Thread.currentThread().threadId())
                        );

                        passwordFound = true;
                        password = possiblePassword;
                        break;
                    }
                }

                passwordsSeed = generatePasswords(passwordsSeed);
            }

            // TODO: debug only
            LOGGER.info(
                "Thread-worker %s finish!"
                    .formatted(
                        Thread.currentThread().threadId()
                    )
            );

            return password;
        }

        private void handleInterruption() throws InterruptedException {
            if (Thread.currentThread().isInterrupted()) {
                throw new InterruptedException();
            }
        }

        private List<String> generatePasswords(List<String> seed) {
            List<String> res = new ArrayList<>();

            for (var prefix: seed) {
                for (var suffix: validPasswordSymbols) {
                    res.add(prefix + suffix);
                }
            }

            // TODO: debug only
            if (res.isEmpty()) {
                LOGGER.info(
                    "Thread-worker %s generate empty passwords list"
                        .formatted(Thread.currentThread().threadId())
                );
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

        private final List<String> seed;
        private final String hash;
        private final MessageDigest md;
    }
}
