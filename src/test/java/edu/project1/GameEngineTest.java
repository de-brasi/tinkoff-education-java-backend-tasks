package edu.project1;

import edu.hw3.Task4;
import edu.project1.engine.Game;
import edu.project1.engine.GameResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class GameEngineTest {
    @Test
    @DisplayName("Word \"hello\"; fail attempt (letter)")
    public void test1() {
        var game = new Game();

        var faultyAttempt = game.guessLetter("w");
        assertThat(faultyAttempt).isEqualTo(GameResponse.FAILURE_ATTEMPT);
    }

    @Test
    @DisplayName("Word \"hello\"; fail attempt (entire word)")
    public void test2() {
        var game = new Game();

        var faultyAttempt = game.guessEntireWord("hola!");
        assertThat(faultyAttempt).isEqualTo(GameResponse.FAILURE_ATTEMPT);
    }

    @Test
    @DisplayName("Word \"hello\"; fail attempt (entire word, shorter than it must be)")
    public void test3() {
        var game = new Game();

        var faultyAttempt = game.guessEntireWord("hola");
        assertThat(faultyAttempt).isEqualTo(GameResponse.REQUEST_IGNORED);
    }

    @Test
    @DisplayName("Word \"hello\"; successful attempts")
    public void test4() {
        var game = new Game();

        assertThat(game.guessLetter('h')).isEqualTo(GameResponse.SUCCESSFUL_ATTEMPT);
        assertThat(game.guessLetter('e')).isEqualTo(GameResponse.SUCCESSFUL_ATTEMPT);
        assertThat(game.guessLetter('l')).isEqualTo(GameResponse.SUCCESSFUL_ATTEMPT);
        assertThat(game.guessLetter('o')).isEqualTo(GameResponse.SUCCESSFUL_ATTEMPT);
    }

    @Test
    @DisplayName("Word \"hello\"; double existed letter try attempts")
    public void test5() {
        var game = new Game();

        game.guessLetter('h');
        assertThat(game.guessLetter('h')).isEqualTo(GameResponse.REQUEST_IGNORED);
    }

    @Test
    @DisplayName("Word \"hello\"; won game state")
    public void test6() {
        var game = new Game();

        var correctLetters = "hello".toCharArray();

        for (var letter: correctLetters) {
            game.guessLetter(letter);
        }

        assertThat(game.getGuessedWordState()).isEqualTo("hello");
    }

    @Test
    @DisplayName("Word \"hello\"; end-game state")
    public void test7() {
        var game = new Game();

        var correctLetters = "hello".toCharArray();
        for (var letter: correctLetters) {
            game.guessLetter(letter);
        }

        assertThat(game.getGuessedWordState()).isEqualTo("hello");
    }

    @Test
    @DisplayName("Word \"hello\"; failed game; letter by letter")
    public void test8() {
        var game = new Game();
        var absolutelyIncorrectLetters = "abcdefgijkmnpqrst".toCharArray();

        int wrongLetterIndex = 0;
        while (game.getAttemptCount() > 1) {
            game.guessLetter(absolutelyIncorrectLetters[wrongLetterIndex++]);
        }

        assertThat(game.guessLetter(absolutelyIncorrectLetters[wrongLetterIndex])).isEqualTo(GameResponse.LOSS);
    }

    @Test
    @DisplayName("Word \"hello\"; failed game; by entire words")
    public void test9() {
        var game = new Game();
        var absolutelyIncorrectLetters = "abcde";

        while (game.getAttemptCount() > 1) {
            game.guessEntireWord(absolutelyIncorrectLetters);
        }

        assertThat(game.guessEntireWord(absolutelyIncorrectLetters)).isEqualTo(GameResponse.LOSS);
        assertThat(game.guessEntireWord(absolutelyIncorrectLetters)).isEqualTo(GameResponse.GAME_FINISHED);
    }

    @Test
    @DisplayName("Word \"hello\"; test attempt counter")
    public void test10() {
        var game = new Game();
        int attemptsCount = game.getAttemptCount();

        GameResponse successAttempt;
        GameResponse faultyAttempt;

        successAttempt = game.guessLetter("h");
        assertThat(game.getAttemptCount()).isEqualTo(attemptsCount);

        successAttempt = game.guessLetter("h");
        assertThat(game.getAttemptCount()).isEqualTo(attemptsCount);

        faultyAttempt = game.guessLetter("a");
        --attemptsCount;
        assertThat(game.getAttemptCount()).isEqualTo(attemptsCount);

        faultyAttempt = game.guessLetter("a");
        --attemptsCount;
        assertThat(game.getAttemptCount()).isEqualTo(attemptsCount);

        successAttempt = game.guessLetter("e");
        assertThat(game.getAttemptCount()).isEqualTo(attemptsCount);

        faultyAttempt = game.guessLetter("b");
        --attemptsCount;
        assertThat(game.getAttemptCount()).isEqualTo(attemptsCount);
    }
}
