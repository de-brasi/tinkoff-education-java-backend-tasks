package edu.project1.engine;

public class Game {
    @SuppressWarnings("MagicNumber")
    public Game() {
        targetWordRepresentation = new WordRepresentation(
            TargetWordGetter.makeWord()
        );
        attemptCounter = 8;
    }

    public Game(int attemptCount) {
        targetWordRepresentation = new WordRepresentation(
            TargetWordGetter.makeWord()
        );
        attemptCounter = attemptCount;
    }

    public int getWordLength() {
        return targetWordRepresentation.length();
    }

    public int getAttemptCount() {
        return attemptCounter;
    }

    public String getGuessedWordState() {
        return targetWordRepresentation.getWordRepresentation();
    }

    public GameResponse guessLetter(String guessedLetter) {
        if (attemptCounter == 0) {
            return  GameResponse.GAME_FINISHED;
        }

        assert !guessedLetter.isEmpty();

        char curLetter = guessedLetter.toLowerCase().toCharArray()[0];
        if (
            guessedLetter.length() > 1
            || targetWordRepresentation.letterGuessed(curLetter)
        ) {
            return GameResponse.REQUEST_IGNORED;
        }

        if (targetWordRepresentation.containsLetter(curLetter)) {
            targetWordRepresentation.openLetter(curLetter);
            return GameResponse.SUCCESSFUL_ATTEMPT;
        } else {
            --attemptCounter;
            return (attemptCounter > 0) ? GameResponse.FAILURE_ATTEMPT : GameResponse.LOSS;
        }
    }

    public GameResponse guessLetter(char guessedLetter) {
        return guessLetter(String.valueOf(guessedLetter));
    }

    public GameResponse guessEntireWord(String guessedWord) {
        if (attemptCounter == 0) {
            return  GameResponse.GAME_FINISHED;
        }

        assert !guessedWord.isEmpty();

        if (guessedWord.length() != targetWordRepresentation.length()) {
            return GameResponse.REQUEST_IGNORED;
        }

        if (guessedWord.equalsIgnoreCase(targetWordRepresentation.getContent())) {
            attemptCounter = 0;
            return GameResponse.WIN;
        } else {
            --attemptCounter;
            return (attemptCounter > 0) ? GameResponse.FAILURE_ATTEMPT : GameResponse.LOSS;
        }
    }

    private int attemptCounter;
    private final WordRepresentation targetWordRepresentation;
}
