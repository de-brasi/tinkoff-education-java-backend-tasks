package edu.project1;

import edu.project1.engine.Game;
import edu.project1.engine.GameResponse;
import java.io.PrintStream;

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


    private static final int ATTEMPTS_COUNT = 9;
    private final Gallows gallows = new Gallows();
    private final UserInteractionInterface userInteractionInterface = new UserInteractionInterface();


    protected class UserInteractionInterface {
        UserInteractionInterface() {
            stream = System.out;
        }

        UserInteractionInterface(PrintStream stream) {
            this.stream = stream;
        }

        public void showTitle() {
            stream.print(
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
            stream.print(
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
            stream.print(
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
            stream.println("The word is: " + game.getGuessedWordState());
        }

        public void showGallowsToUser() {
            stream.println(gallows.getGallows());
        }

        public void sayToUserAboutIncorrectInput() {
            stream.println("Your input is incorrect! Try once again.");
        }

        public void showHitMessage() {
            stream.println("Hit!");
        }

        public void showAttemptsCountToUser() {
            stream.println("You have " + game.getAttemptCount() + " attempts.");
        }

        public void showInvitationToSolveTheWord() {
            stream.println("Guess a letter or word with length " + game.getWordLength() + ":");
        }

        private final PrintStream stream;
    }

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
                    case HANGMAN_ROPE_LENGTH -> {
                        int headRowIndex = HANGMAN_ROPE_LENGTH + 1;
                        int headColIndex = OFFSET_BEFORE_GALLOWS + 1 + OFFSET_AFTER_GALLOWS;
                        gallows[headRowIndex][headColIndex] = HEAD;
                    }
                    case HANGMAN_ROPE_LENGTH + 1 -> {
                        int lHandRowIndex = HANGMAN_ROPE_LENGTH + 2;
                        int lHandColIndex = OFFSET_BEFORE_GALLOWS + OFFSET_AFTER_GALLOWS;
                        gallows[lHandRowIndex][lHandColIndex] = LEFT_HAND;
                    }
                    case HANGMAN_ROPE_LENGTH + 2 -> {
                        int bodyRowIndex = HANGMAN_ROPE_LENGTH + 2;
                        int bodyColIndex = OFFSET_BEFORE_GALLOWS + OFFSET_AFTER_GALLOWS + 1;
                        gallows[bodyRowIndex][bodyColIndex] = BODY;
                    }
                    case HANGMAN_ROPE_LENGTH + 3 -> {
                        int rHandRowIndex = HANGMAN_ROPE_LENGTH + 2;
                        int rHandColIndex = OFFSET_BEFORE_GALLOWS + OFFSET_AFTER_GALLOWS + 2;
                        gallows[rHandRowIndex][rHandColIndex] = RIGHT_HAND;
                    }
                    case HANGMAN_ROPE_LENGTH + 4 -> {
                        int lLegRowIndex = HANGMAN_ROPE_LENGTH + 3;
                        int lLegColIndex = OFFSET_BEFORE_GALLOWS + OFFSET_AFTER_GALLOWS;
                        gallows[lLegRowIndex][lLegColIndex] = LEFT_LEG;
                    }
                    case HANGMAN_ROPE_LENGTH + 5 -> {
                        int rLegRowIndex = HANGMAN_ROPE_LENGTH + 3;
                        int rLegColIndex = OFFSET_BEFORE_GALLOWS + OFFSET_AFTER_GALLOWS + 2;
                        gallows[rLegRowIndex][rLegColIndex] = RIGHT_LEG;
                    }
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
