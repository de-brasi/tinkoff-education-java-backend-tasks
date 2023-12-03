package edu.project1;

import edu.project1.engine.Game;
import edu.project1.engine.GameResponse;

public class AsciiInterface extends CommandLineGameInterface {
    public AsciiInterface() {
        game = new Game(ATTEMPTS_COUNT);
    }

    @Override
    protected void showInviteInputMessage() {
        userInteractionInterface.showTitle();
        userInteractionInterface.showGallowsToUser();
        userInteractionInterface.showAttemptsCountToUser();
        userInteractionInterface.showInvitationToSolveTheWord();
    }

    @Override
    protected void showGameState() {
        userInteractionInterface.showGallowsToUser();
        userInteractionInterface.showAttemptsCountToUser();
        userInteractionInterface.showInvitationToSolveTheWord();
    }

    @Override
    protected void showResponseMessage(GameResponse response) {
        switch (response) {
            case WIN -> {
                userInteractionInterface.showWinMessage();
                userInteractionInterface.showWordsStateToUser();
            }
            case LOSS -> {
                gallows.continueTheExecution();
                userInteractionInterface.showGallowsToUser();
                userInteractionInterface.showLostMessage();
            }
            case SUCCESSFUL_ATTEMPT -> {
                userInteractionInterface.showHitMessage();
                userInteractionInterface.showWordsStateToUser();
            }
            case FAILURE_ATTEMPT -> gallows.continueTheExecution();
            case REQUEST_IGNORED -> userInteractionInterface.sayToUserAboutIncorrectInput();
            case null, default -> {
            }
        }
    }


    protected class UserInteractionInterface {
        UserInteractionInterface() {}

        public void showTitle() {
            System.out.print(
                """
                        ██╗  ██╗ █████╗ ███╗   ██╗ ██████╗ ███╗   ███╗ █████╗ ███╗   ██╗
                        ██║  ██║██╔══██╗████╗  ██║██╔════╝ ████╗ ████║██╔══██╗████╗  ██║
                        ███████║███████║██╔██╗ ██║██║  ███╗██╔████╔██║███████║██╔██╗ ██║
                        ██╔══██║██╔══██║██║╚██╗██║██║   ██║██║╚██╔╝██║██╔══██║██║╚██╗██║
                        ██║  ██║██║  ██║██║ ╚████║╚██████╔╝██║ ╚═╝ ██║██║  ██║██║ ╚████║
                        ╚═╝  ╚═╝╚═╝  ╚═╝╚═╝  ╚═══╝ ╚═════╝ ╚═╝     ╚═╝╚═╝  ╚═╝╚═╝  ╚═══╝
                        """
            );
        }

        public void showWinMessage() {
            System.out.print(
                """
                        ██╗   ██╗ ██████╗ ██╗   ██╗    ██╗    ██╗██╗███╗   ██╗██╗
                        ╚██╗ ██╔╝██╔═══██╗██║   ██║    ██║    ██║██║████╗  ██║██║
                         ╚████╔╝ ██║   ██║██║   ██║    ██║ █╗ ██║██║██╔██╗ ██║██║
                          ╚██╔╝  ██║   ██║██║   ██║    ██║███╗██║██║██║╚██╗██║╚═╝
                           ██║   ╚██████╔╝╚██████╔╝    ╚███╔███╔╝██║██║ ╚████║██╗
                           ╚═╝    ╚═════╝  ╚═════╝      ╚══╝╚══╝ ╚═╝╚═╝  ╚═══╝╚═╝

                                                ,////,
                                                /// 6|
                                                //  _|
                                               _/_,-'
                                          _.-/'/   \\   ,/;,
                                       ,-' /'  \\_   \\ / _/
                                       `\\ /     _/\\  ` /
                                         |     /,  `\\_/
                                         |     \\'
                             /\\_        /`      /\\
                           /' /_``--.__/\\  `,. /  \\
                          |_/`  `-._     `\\/  `\\   `.
                                    `-.__/'     `\\   |
                                                  `\\  \\
                                                    `\\ \\
                                                      \\_\\__
                                                       \\___)

                    """
            );
        }

        public void showLostMessage() {
            System.out.print(
                """
                        ██╗   ██╗ ██████╗ ██╗   ██╗    ██╗      ██████╗ ███████╗████████╗██╗
                        ╚██╗ ██╔╝██╔═══██╗██║   ██║    ██║     ██╔═══██╗██╔════╝╚══██╔══╝██║
                         ╚████╔╝ ██║   ██║██║   ██║    ██║     ██║   ██║███████╗   ██║   ██║
                          ╚██╔╝  ██║   ██║██║   ██║    ██║     ██║   ██║╚════██║   ██║   ╚═╝
                           ██║   ╚██████╔╝╚██████╔╝    ███████╗╚██████╔╝███████║   ██║   ██╗
                           ╚═╝    ╚═════╝  ╚═════╝     ╚══════╝ ╚═════╝ ╚══════╝   ╚═╝   ╚═╝

                                    ⠀⠀⠀     ⠀⠀⠀⠀⠀⠀⣀⣀⡀⠀⠀⠀⠀⠀⠀⠀⠀
                                        ⠀⠀⠀⠀⠀⠀⣠⣔⡿⠛⠒⠒⡕⢄⠀⠀⠀⠀⠀⠀
                                        ⠀⠀⠀⠀⣀⣴⣳⠃⠀⠀⠀⠀⠘⢎⡦⣄⠀⠀⠀⠀
                                        ⠀⠀⠀⣜⠟⠁⠀⠀⠀⠀⠀⠀⠀⠀⠈⠢⣳⠀⠀⠀
                                        ⠀⠀⢸⣸⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⡇⡆⠀⠀
                                        ⠀⠀⠘⡏⢀⢴⠶⣤⢄⢲⣲⠦⣦⣤⡤⡀⡇⠇⠀⠀
                                        ⠀⠀⠀⣧⠀⣾⢀⣸⡸⠘⢸⠀⣿⠀⣸⡏⣧⠀⠀⠀
                                        ⠀⠀⠀⢹⠀⣿⠿⡯⡀⢀⣼⢀⣿⠛⠉⠀⢻⠀⠀⠀
                                        ⠀⠀⠀⣿⠐⠛⠂⠘⠛⠒⠛⠊⠛⠂⠀⢸⢸⠀⠀⠀
                                        ⠀⠀⠀⣿⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢸⡼⠀⠀⠀
                                        ⠀⠀⠀⢻⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢸⡆⠀⠀⠀
                                        ⠀⠀⢀⢾⡇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢸⡷⡀⠀⠀
                                        ⠀⣠⠃⠘⠊⠉⠛⠛⠋⠩⠩⠭⠍⠛⠛⠛⠃⠐⡄⠀
                                        ⠀⣯⡉⠉⢉⡉⠉⠉⠉⠉⠉⠉⣉⣉⣉⣉⣉⣉⣹⠀
                                        ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                                        ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                                        ⡴⡤⡤⣤⡤⡤⣤⣤⢠⣤⣤⠀⢰⡄⣤⣶⡴⢶⣶⡴
                    """
            );
        }

        public void showWordsStateToUser() {
            System.out.println("The word is: " + game.getGuessedWordState());
        }

        public void showGallowsToUser() {
            System.out.println(gallows.getGallows());
        }

        public void sayToUserAboutIncorrectInput() {
            System.out.println("Your input is incorrect! Try once again.");
        }

        public void showHitMessage() {
            System.out.println("Hit!");
        }

        public void showAttemptsCountToUser() {
            System.out.println("You have " + game.getAttemptCount() + " attempts.");
        }

        public void showInvitationToSolveTheWord() {
            System.out.println("Guess a letter or word with length " + game.getWordLength() + ":");
        }
    }

    private static final int ATTEMPTS_COUNT = 9;
    private final Gallows gallows = new Gallows();
    private final UserInteractionInterface userInteractionInterface = new UserInteractionInterface();

    private static class Gallows {
        /*
            ╔════════════╗
            ║            │
            ║            │
            ║            │
            ║           @
            ║          |│\
            ║           |\
            ║
            ║
            ╠═══════════════╗
        */

        Gallows() {
            var source = """
                        ╔════════════╗
                        ║
                        ║
                        ║
                        ║
                        ║
                        ║
                        ║
                        ║
                        ╠═══════════════╗
            """;

            var splitted = source.split("\n");
            for (var row : splitted) {
                assert row.length() == splitted[0].length();
            }

            gallows = new char[splitted.length][];
            for (int i = 0; i < splitted.length; i++) {
                String row = splitted[i]
                    + String.valueOf(' ').repeat(GALLOWS_WIDTH - splitted[i].length());
                gallows[i] = row.toCharArray();
            }
        }

        public String getGallows() {
            StringBuilder builder = new StringBuilder();
            for (var row: gallows) {
                builder.append(row);
                builder.append("\n");
            }
            return builder.toString();
        }

        @SuppressWarnings("MagicNumber")
        public void continueTheExecution() {
            if (executionSteps < HANGMAN_ROPE_LENGTH) {
                gallows[executionSteps + 1][OFFSET_BEFORE_GALLOWS + 1 + OFFSET_AFTER_GALLOWS] = ROPE;
            } else {
                switch (executionSteps) {
                    case HANGMAN_ROPE_LENGTH ->
                        gallows[HANGMAN_ROPE_LENGTH + 1][OFFSET_BEFORE_GALLOWS + 1 + OFFSET_AFTER_GALLOWS] = HEAD;
                    case HANGMAN_ROPE_LENGTH + 1 ->
                        gallows[HANGMAN_ROPE_LENGTH + 2][OFFSET_BEFORE_GALLOWS + 1 + OFFSET_AFTER_GALLOWS - 1] =
                            LEFT_HAND;
                    case HANGMAN_ROPE_LENGTH + 2 ->
                        gallows[HANGMAN_ROPE_LENGTH + 2][OFFSET_BEFORE_GALLOWS + 1 + OFFSET_AFTER_GALLOWS] = BODY;
                    case HANGMAN_ROPE_LENGTH + 3 ->
                        gallows[HANGMAN_ROPE_LENGTH + 2][OFFSET_BEFORE_GALLOWS + 1 + OFFSET_AFTER_GALLOWS + 1] =
                            RIGHT_HAND;
                    case HANGMAN_ROPE_LENGTH + 4 ->
                        gallows[HANGMAN_ROPE_LENGTH + 3][OFFSET_BEFORE_GALLOWS + 1 + OFFSET_AFTER_GALLOWS - 1] =
                            LEFT_LEG;
                    case HANGMAN_ROPE_LENGTH + 5 ->
                        gallows[HANGMAN_ROPE_LENGTH + 3][OFFSET_BEFORE_GALLOWS + 1 + OFFSET_AFTER_GALLOWS + 1] =
                            RIGHT_LEG;
                    default -> {
                    }
                }
            }
            ++executionSteps;
        }

        private final char[][] gallows;

        private int executionSteps = 0;

        private static final int OFFSET_BEFORE_GALLOWS = 12;
        private static final int OFFSET_AFTER_GALLOWS = 12;
        private static final int HANGMAN_ROPE_LENGTH = 3;
        private static final int GALLOWS_WIDTH = 29;

        private static final char ROPE = '│';
        private static final char HEAD = '@';
        private static final char LEFT_HAND = '/';
        private static final char RIGHT_HAND = '\\';
        private static final char LEFT_LEG = '/';
        private static final char RIGHT_LEG = '\\';
        private static final char BODY = '│';
    }
}
