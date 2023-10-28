package edu.hw1;

public class Task8 {
    private final static int BOARD_SIZE = 8;

    public static boolean knightBoardCapture(final int[][] gameBoard) {
        assert gameBoard.length == BOARD_SIZE;
        for (int[] row : gameBoard) {
            assert row.length == BOARD_SIZE;
        }

        for (int rowIndex = 0; rowIndex < BOARD_SIZE; ++rowIndex) {
            for (int colIndex = 0; colIndex < BOARD_SIZE; colIndex++) {
                if (gameBoard[rowIndex][colIndex] == 0) {
                    continue;
                }

                // up-left
                if (0 <= (rowIndex - 2) &&
                    0 <= (colIndex - 1) &&
                    gameBoard[rowIndex - 2][colIndex - 1] == 1) {
                    return false;
                }

                // up-right
                if (0 <= (rowIndex - 2) &&
                    (colIndex + 1) < BOARD_SIZE &&
                    gameBoard[rowIndex - 2][colIndex + 1] == 1) {
                    return false;
                }

                // down-left
                if ((rowIndex + 2) < BOARD_SIZE &&
                    0 <= (colIndex - 1) &&
                    gameBoard[rowIndex + 2][colIndex - 1] == 1) {
                    return false;
                }

                // down-right
                if ((rowIndex + 2) < BOARD_SIZE &&
                    (colIndex + 1) < BOARD_SIZE &&
                    gameBoard[rowIndex + 2][colIndex + 1] == 1) {
                    return false;
                }

                // left-up
                if (0 <= (rowIndex - 1) &&
                    0 <= (colIndex - 2) &&
                    gameBoard[rowIndex - 1][colIndex - 2] == 1) {
                    return false;
                }

                // left-down
                if ((rowIndex + 1) < BOARD_SIZE &&
                    0 <= (colIndex - 2) &&
                    gameBoard[rowIndex + 1][colIndex - 2] == 1) {
                    return false;
                }

                // right-up
                if (0 <= (rowIndex - 1) &&
                    (colIndex + 2) < BOARD_SIZE &&
                    gameBoard[rowIndex - 1][colIndex + 2] == 1) {
                    return false;
                }

                // right-down
                if ((rowIndex + 1) < BOARD_SIZE &&
                    (colIndex + 2) < BOARD_SIZE &&
                    gameBoard[rowIndex + 1][colIndex + 2] == 1) {
                    return false;
                }
            }
        }

        return true;
    }
}
