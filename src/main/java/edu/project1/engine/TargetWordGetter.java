package edu.project1.engine;

import java.security.SecureRandom;
import java.util.Random;

public class TargetWordGetter {
    private TargetWordGetter() {}

    @SuppressWarnings("MultipleStringLiterals")
    public static String makeWord() {
        String[] words = {
            "Never", "gonna", "give", "you", "up",
            "Never", "gonna", "let", "you", "down",
            "Never", "gonna", "run", "around", "and", "desert", "you",
            "Never", "gonna", "make", "you", "cry",
            "Never", "gonna", "say", "goodbye",
            "Never", "gonna", "tell", "a", "lie", "and", "hurt", "you"
        };
        return words[RANDOM.nextInt(0, words.length)];
    }

    private static final Random RANDOM = new SecureRandom();
}
