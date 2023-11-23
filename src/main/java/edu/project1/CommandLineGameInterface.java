package edu.project1;

import edu.project1.engine.Game;
import edu.project1.engine.GameResponse;
import java.util.Scanner;

public class CommandLineGameInterface {
    public CommandLineGameInterface() {
        game = new Game();
    }

    public CommandLineGameInterface(int attemptsCount) {
        game = new Game(attemptsCount);
    }

    public void startGame() {
        GameResponse response;
        Scanner scanner = new Scanner(System.in);

        do {
            showInviteInputMessage();
            String playerAttempt = scanner.nextLine();
            response =
                (playerAttempt.length() > 1)
                    ? game.guessEntireWord(playerAttempt)
                    : game.guessLetter(playerAttempt);
            showResponseMessage(response);
        } while (!(response == GameResponse.LOSS || response == GameResponse.WIN));

    }

    @SuppressWarnings({"RegexpSinglelineJava", "MultipleStringLiterals"})
    protected void showInviteInputMessage() {
        System.out.println("Guess a letter or word with length " + game.getWordLength() + ":");
    }

    @SuppressWarnings({"RegexpSinglelineJava", "MultipleStringLiterals"})
    protected void showResponseMessage(GameResponse response) {
        switch (response) {
            case WIN -> {
                System.out.println("Hit! The word is: " + game.getGuessedWordState());
                System.out.println("You won!");
            }
            case LOSS -> {
                System.out.println("Missed! You have no attempts.");
                System.out.println("You lost!");
            }
            case SUCCESSFUL_ATTEMPT -> {
                System.out.println("Hit! The word is: " + game.getGuessedWordState());
            }
            case FAILURE_ATTEMPT -> {
                System.out.println("Missed! You have " + game.getAttemptCount() + " attempts.");
            }
            case REQUEST_IGNORED -> {
                System.out.println("Your input is incorrect! Try once again.");
                System.out.println("You have " + game.getAttemptCount() + " attempts.");
            }
            case null, default -> {

            }
        }
    }

    private final Game game;
}
