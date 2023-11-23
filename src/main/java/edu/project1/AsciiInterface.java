package edu.project1;

import edu.project1.engine.Game;
import edu.project1.engine.GameResponse;

public class AsciiInterface extends CommandLineGameInterface {
    public AsciiInterface() {
        game = new Game(ATTEMPTS_COUNT);
    }

    @Override
    @SuppressWarnings({"RegexpSinglelineJava", "MultipleStringLiterals"})
    protected void showInviteInputMessage() {
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
        System.out.println(gallows.getGallows());
        System.out.println("You have " + game.getAttemptCount() + " attempts.");
        System.out.println("Guess a letter or word with length " + game.getWordLength() + ":");
    }

    @Override
    @SuppressWarnings("RegexpSinglelineJava")
    protected void showGameState() {
        System.out.println(gallows.getGallows());
        System.out.println("You have " + game.getAttemptCount() + " attempts.");
        System.out.println("Guess a letter or word with length " + game.getWordLength() + ":");
    }

    @Override
    @SuppressWarnings("RegexpSinglelineJava")
    protected void showResponseMessage(GameResponse response) {
        switch (response) {
            case WIN -> {
                printYouWinMessage();
                System.out.println("The word is: " + game.getGuessedWordState());
            }
            case LOSS -> {
                gallows.continueTheExecution();
                System.out.println(gallows.getGallows());
                printYouLostMessage();
            }
            case SUCCESSFUL_ATTEMPT -> {
                System.out.println("Hit! The word is: " + game.getGuessedWordState());
            }
            case FAILURE_ATTEMPT -> {
                gallows.continueTheExecution();
            }
            case REQUEST_IGNORED -> {
                System.out.println("Your input is incorrect! Try once again.");
            }
            case null, default -> {
            }
        }
    }

    @SuppressWarnings("RegexpSinglelineJava")
    private static void printYouWinMessage() {
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

    @SuppressWarnings("RegexpSinglelineJava")
    private static void printYouLostMessage() {
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

    private static final int ATTEMPTS_COUNT = 9;
    private final Gallows gallows = new Gallows();

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
                        gallows[HANGMAN_ROPE_LENGTH + 1][OFFSET_BEFORE_GALLOWS + 1 + OFFSET_AFTER_GALLOWS] = HEAD;
                    }
                    case HANGMAN_ROPE_LENGTH + 1 -> {
                        gallows[HANGMAN_ROPE_LENGTH + 2][OFFSET_BEFORE_GALLOWS + 1 + OFFSET_AFTER_GALLOWS - 1] =
                            LEFT_HAND;
                    }
                    case HANGMAN_ROPE_LENGTH + 2 -> {
                        gallows[HANGMAN_ROPE_LENGTH + 2][OFFSET_BEFORE_GALLOWS + 1 + OFFSET_AFTER_GALLOWS] = BODY;
                    }
                    case HANGMAN_ROPE_LENGTH + 3 -> {
                        gallows[HANGMAN_ROPE_LENGTH + 2][OFFSET_BEFORE_GALLOWS + 1 + OFFSET_AFTER_GALLOWS + 1] =
                            RIGHT_HAND;
                    }
                    case HANGMAN_ROPE_LENGTH + 4 -> {
                        gallows[HANGMAN_ROPE_LENGTH + 3][OFFSET_BEFORE_GALLOWS + 1 + OFFSET_AFTER_GALLOWS - 1] =
                            LEFT_LEG;
                    }
                    case HANGMAN_ROPE_LENGTH + 5 -> {
                        gallows[HANGMAN_ROPE_LENGTH + 3][OFFSET_BEFORE_GALLOWS + 1 + OFFSET_AFTER_GALLOWS + 1] =
                            RIGHT_LEG;
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
