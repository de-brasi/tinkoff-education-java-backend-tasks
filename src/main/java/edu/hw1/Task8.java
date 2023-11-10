package edu.hw1;

public class Task8 {
    private Task8() {
        // not allowed
    }

    private final static int BOARD_SIZE = 8;

    public static boolean knightBoardCapture(final int[][] gameBoard) {
        assert gameBoard.length == BOARD_SIZE;
        for (int[] row : gameBoard) {
            assert row.length == BOARD_SIZE;
        }

        boolean result = true;

        for (int rowIndex = 0; rowIndex < BOARD_SIZE; ++rowIndex) {
            for (int colIndex = 0; colIndex < BOARD_SIZE; colIndex++) {
                if (gameBoard[rowIndex][colIndex] == 0) {
                    continue;
                }

                if (!result) {
                    break;
                }

                result &= checkUpLeft(gameBoard, rowIndex, colIndex);
                result &= checkUpRight(gameBoard, rowIndex, colIndex);
                result &= checkDownLeft(gameBoard, rowIndex, colIndex);
                result &= checkDownRight(gameBoard, rowIndex, colIndex);
                result &= checkLeftUp(gameBoard, rowIndex, colIndex);
                result &= checkLeftDown(gameBoard, rowIndex, colIndex);
                result &= checkRightUp(gameBoard, rowIndex, colIndex);
                result &= checkRightDown(gameBoard, rowIndex, colIndex);
            }
        }

        return result;
    }

    private static boolean checkUpLeft(final int[][] gameBoard, final int rowIndex, final int colIndex) {
        // up-left
        if (0 <= (rowIndex - 2)
            && 0 <= (colIndex - 1)
            && gameBoard[rowIndex - 2][colIndex - 1] == 1) {
            return false;
        }
        return true;
    }

    private static boolean checkUpRight(final int[][] gameBoard, final int rowIndex, final int colIndex) {
        // up-right
        if (0 <= (rowIndex - 2)
            && (colIndex + 1) < BOARD_SIZE
            && gameBoard[rowIndex - 2][colIndex + 1] == 1) {
            return false;
        }
        return true;
    }

    private static boolean checkDownLeft(final int[][] gameBoard, final int rowIndex, final int colIndex) {
        // down-left
        if ((rowIndex + 2) < BOARD_SIZE
            && 0 <= (colIndex - 1)
            && gameBoard[rowIndex + 2][colIndex - 1] == 1) {
            return false;
        }
        return true;
    }

    private static boolean checkDownRight(final int[][] gameBoard, final int rowIndex, final int colIndex) {
        // down-right
        if ((rowIndex + 2) < BOARD_SIZE
            && (colIndex + 1) < BOARD_SIZE
            && gameBoard[rowIndex + 2][colIndex + 1] == 1) {
            return false;
        }
        return true;
    }

    private static boolean checkLeftUp(final int[][] gameBoard, final int rowIndex, final int colIndex) {
        // left-up
        if (0 <= (rowIndex - 1)
            && 0 <= (colIndex - 2)
            && gameBoard[rowIndex - 1][colIndex - 2] == 1) {
            return false;
        }
        return true;
    }

    private static boolean checkLeftDown(final int[][] gameBoard, final int rowIndex, final int colIndex) {
        // left-down
        if ((rowIndex + 1) < BOARD_SIZE
            && 0 <= (colIndex - 2)
            && gameBoard[rowIndex + 1][colIndex - 2] == 1) {
            return false;
        }
        return true;
    }

    private static boolean checkRightUp(final int[][] gameBoard, final int rowIndex, final int colIndex) {
        // right-up
        if (0 <= (rowIndex - 1)
            && (colIndex + 2) < BOARD_SIZE
            && gameBoard[rowIndex - 1][colIndex + 2] == 1) {
            return false;
        }
        return true;
    }

    private static boolean checkRightDown(final int[][] gameBoard, final int rowIndex, final int colIndex) {
        // right-down
        if ((rowIndex + 1) < BOARD_SIZE
            && (colIndex + 2) < BOARD_SIZE
            && gameBoard[rowIndex + 1][colIndex + 2] == 1) {
            return false;
        }
        return true;
    }
}
