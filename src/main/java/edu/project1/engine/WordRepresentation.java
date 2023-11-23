package edu.project1.engine;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class WordRepresentation {
    public WordRepresentation(String word) {
        this.word = word;

        char letter;
        var wordAsCharArray = word.toCharArray();
        for (int i = 0; i < word.length(); ++i) {
            letter = wordAsCharArray[i];

            if (!lettersIndexes.containsKey(letter)) {
                lettersIndexes.put(letter, new HashSet<>());
            }

            lettersIndexes.get(letter).add(i);
        }

        this.wordRepresentation = this.word.toCharArray();
        Arrays.fill(this.wordRepresentation, '*');
    }

    public boolean letterGuessed(Character letter) {
        return alreadyGuessedLetters.contains(letter);
    }

    public boolean containsLetter(Character letter) {
        return lettersIndexes.containsKey(letter);
    }

    public void openLetter(Character letter) {
        if (lettersIndexes.containsKey(letter)) {
            alreadyGuessedLetters.add(letter);
        }

        for (var index: lettersIndexes.getOrDefault(letter, new HashSet<>())) {
            wordRepresentation[index] = letter;
        }
    }

    public String getWordRepresentation() {
        return String.copyValueOf(wordRepresentation);
    }

    public int length() {
        return word.length();
    }

    public String getContent() {
        return word;
    }

    private final String word;
    private final char[] wordRepresentation;
    private final Set<Character> alreadyGuessedLetters = new HashSet<>();
    private final Map<Character, Set<Integer>> lettersIndexes = new HashMap<>();
}
