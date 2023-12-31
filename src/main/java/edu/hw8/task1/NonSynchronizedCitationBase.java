package edu.hw8.task1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class NonSynchronizedCitationBase {
    public NonSynchronizedCitationBase() {
        citations = new ArrayList<>();
        wordsReverseIndex = new HashMap<>();

        fillWithDefaultCitations();
        doReverseIndexes();
    }

    public String getCitation(String keyWord) {
        String keyWordLowercase = keyWord.toLowerCase();
        if (wordsReverseIndex.containsKey(keyWordLowercase)) {
            return citations.get(
                wordsReverseIndex.get(keyWordLowercase).iterator().next()
            );
        }
        return "Пока обидной цитаты нет, но ещё не вечер!";
    }

    private void fillWithDefaultCitations() {
        citations.addAll(
            List.of(
                "Не переходи на личности там, где их нет",
                "Если твои противники перешли на личные оскорбления, будь уверена — твоя победа не за горами",
                "А я тебе говорил, что ты глупый? Так вот, я забираю свои слова обратно... Ты просто бог идиотизма.",
                "Чем ниже интеллект, тем громче оскорбления"
            )
        );
    }

    private void doReverseIndexes() {
        for (int i = 0; i < citations.size(); ++i) {
            final int finalI = i;
            erasePunctuationMarks(citations.get(i).split(" "))
                .forEach(
                    (String word) -> {
                        String wordLowercase = word.toLowerCase();
                        if (!wordsReverseIndex.containsKey(wordLowercase)) {
                            wordsReverseIndex.put(wordLowercase, new HashSet<>());
                        }
                        wordsReverseIndex.get(wordLowercase).add(finalI);
                    }
                );
        }
    }

    private List<String> erasePunctuationMarks(String[] sentence) {
        return Arrays
            .stream(sentence)
            .filter(this::notPunctuationMark)
            .toList();
    }

    private boolean notPunctuationMark(String toCheck) {
        return !PUNCTUATION_MARKS.contains(toCheck);
    }

    private static final String PUNCTUATION_MARKS = "...,;:!?";

    private final List<String> citations;
    private final Map<String, Set<Integer>> wordsReverseIndex;
}
