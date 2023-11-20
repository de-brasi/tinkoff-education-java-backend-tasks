package edu.project2.prettyprinter;

import edu.project2.util.Cell;
import edu.project2.util.Coordinate;
import edu.project2.util.Direction;
import edu.project2.util.Maze;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PrettyPrinter {
    public PrettyPrinter() {}

    public void print(Maze maze, List<Coordinate> path) {
        final var start = path.get(0);
        final var finish = path.get(path.size() - 1);

        final var mazePlan = getWalls(maze);
        smoothCorners(mazePlan);
        markUpPath(mazePlan, path);
        print(mazePlan);
    }

    private static void markUpPath(char[][] mazePlan, List<Coordinate> path) {
        Coordinate curMazePosition;
        Coordinate nextMazePosition;

        Direction directionToNext;

        Coordinate realPlanCoordinate;
        Coordinate intermediateStepCoordinate;

        for (int i = 0; i < path.size(); i++) {
            curMazePosition = path.get(i);
            realPlanCoordinate = getCoordinateInMazePlan(curMazePosition);

            if (i + 1 < path.size()) {
                nextMazePosition = path.get(i + 1);
                directionToNext = getDirectionTo(curMazePosition, nextMazePosition);
                intermediateStepCoordinate = realPlanCoordinate.coordinateFromDirection(directionToNext);

                mazePlan[intermediateStepCoordinate.row()][intermediateStepCoordinate.col()] = AMOGUS_PATH_MARKER;
            }

            mazePlan[realPlanCoordinate.row()][realPlanCoordinate.col()] = AMOGUS_PATH_MARKER;
        }

    }

    private static Direction getDirectionTo(Coordinate from, Coordinate to) {
        for (Direction directionToCheck : DIRECTIONS) {
            if (to.equals(from.coordinateFromDirection(directionToCheck))) {
                return directionToCheck;
            }
        }
        throw new IllegalArgumentException("Unexpected pair of coordinates! The must to be neighbours.");
    }

    private static void print(char[][] maze) {
        StringBuilder builder;
        for (var row: maze) {
            builder = new StringBuilder();
            for (char rowValue: row) {
                if (rowValue == AMOGUS_PATH_MARKER) {
                    builder.append(ANSI_RED).append(rowValue).append(ANSI_RED);
                } else {
                    builder.append(ANSI_BLACK).append(rowValue).append(ANSI_BLACK);
                }
            }
            LOGGER.info(builder.toString());
        }
    }

    private static Coordinate getCoordinateInMazePlan(Coordinate coordinateInMaze) {
        return getCoordinateInMazePlan(coordinateInMaze.row(), coordinateInMaze.col());
    }

    private static Coordinate getCoordinateInMazePlan(int rowInMaze, int colInMaze) {
        return new Coordinate(2 * rowInMaze + 1, 2 * colInMaze + 1);
    }

    private char[][] getWalls(Maze maze) {
        if (maze.getWidth() == 0 || maze.getHeight() == 0) {
            return new char[0][0];
        }

        var mazePlan = new char[2 * maze.getHeight() + 1][2 * maze.getWidth() + 1];

        setStartStatement(mazePlan);

        Cell[] curRow;
        Cell curCell;

        Coordinate curCellCoordinateInMazePlane;

        for (int mazeRowIndex = 0; mazeRowIndex < maze.getHeight(); mazeRowIndex++) {
            curRow = maze.getRowShallowCopy(mazeRowIndex);

            for (int mazeColIndex = 0; mazeColIndex < curRow.length; mazeColIndex++) {
                curCell = curRow[mazeColIndex];

                curCellCoordinateInMazePlane = getCoordinateInMazePlan(mazeRowIndex, mazeColIndex);

                buildWallsNearCell(mazePlan, curCellCoordinateInMazePlane, curCell);
            }
        }

        return mazePlan;
    }

    private static void setBorders(char[][] mazePlan) {

        if (mazePlan.length == 0) {
            return;
        }

        final int topRow = 0;
        final int bottomRow = mazePlan.length - 1;

        mazePlan[topRow][0] = LEFT_UP_ANGLE;
        mazePlan[topRow][mazePlan[topRow].length - 1] = RIGHT_UP_ANGLE;
        for (int colIdx = 1; colIdx < mazePlan[topRow].length - 1; colIdx++) {
            if (mazePlan[topRow + 1][colIdx] == EMPTY_SPACE) {
                mazePlan[topRow][colIdx] = HORIZONTAL_SMOOTH_WALL;
            } else {
                mazePlan[topRow][colIdx] = HORIZONTAL_DOWN_LEDGE_WALL;
            }
        }

        mazePlan[bottomRow][0] = LEFT_DOWN_ANGLE;
        mazePlan[bottomRow][mazePlan[bottomRow].length - 1] = RIGHT_DOWN_ANGLE;
        for (int colIdx = 1; colIdx < mazePlan[bottomRow].length - 1; colIdx++) {
            if (mazePlan[bottomRow - 1][colIdx] == EMPTY_SPACE) {
                mazePlan[bottomRow][colIdx] = HORIZONTAL_SMOOTH_WALL;
            } else {
                mazePlan[bottomRow][colIdx] = HORIZONTAL_UP_LEDGE_WALL;
            }
        }

        for (int middleRow = 1; middleRow < mazePlan.length - 1; middleRow++) {
            if (mazePlan[middleRow][/* right-side space */1] == EMPTY_SPACE) {
                mazePlan[middleRow][0] = VERTICAL_SMOOTH_WALL;
            } else {
                mazePlan[middleRow][0] = VERTICAL_RIGHT_LEDGE_WALL;
            }

            if (mazePlan[middleRow][/* left-side space */mazePlan[middleRow].length - 1 - 1] == EMPTY_SPACE) {
                mazePlan[middleRow][mazePlan[middleRow].length - 1] = VERTICAL_SMOOTH_WALL;
            } else {
                mazePlan[middleRow][mazePlan[middleRow].length - 1] = VERTICAL_LEFT_LEDGE_WALL;
            }
        }
    }

    private static void setStartStatement(char[][] mazePlan) {
        for (int i = 0; i < mazePlan.length; i++) {
            if (i % 2 == 1) {
                for (int j = 0; j < mazePlan[i].length; j++) {
                    if (j % 2 == 1) {
                        mazePlan[i][j] = EMPTY_SPACE;
                    } else {
                        mazePlan[i][j] = VERTICAL_SMOOTH_WALL;
                    }
                }
            } else {
                for (int j = 0; j < mazePlan[i].length; j++) {
                    if (j % 2 == 1) {
                        mazePlan[i][j] = HORIZONTAL_SMOOTH_WALL;
                    } else {
                        mazePlan[i][j] = CROSS_WALL;
                    }
                }
            }
        }

        setBorders(mazePlan);
    }

    private void buildWallsNearCell(char[][] plan, Coordinate cellCoordinate, Cell cell) {
        Coordinate wallCoordinate;

        for (final var direction: this.DIRECTIONS) {
            if (!cell.checkWallExistence(direction)) {
                wallCoordinate = cellCoordinate.coordinateFromDirection(direction);
                plan[wallCoordinate.row()][wallCoordinate.col()] = EMPTY_SPACE;
            }
        }
    }

    private static void smoothCorners(char[][] plan) {
        for (int rowIndex = 0; rowIndex < plan.length; rowIndex++) {
            for (int colIndex = 0; colIndex < plan[rowIndex].length; colIndex++) {
                if (plan[rowIndex][colIndex] != EMPTY_SPACE) {
                    polishWall(plan, rowIndex, colIndex);
                }
            }
        }
    }

    private static void polishWall(char[][] plan, int rowIndex, int colIndex) {
        if (rowIndex + 1 < plan.length && plan[rowIndex + 1][colIndex] == EMPTY_SPACE) {
            polishBottom(plan, rowIndex, colIndex);
        }

        if (0 < rowIndex - 1 && plan[rowIndex - 1][colIndex] == EMPTY_SPACE) {
            polishTop(plan, rowIndex, colIndex);
        }

        if (0 < colIndex - 1 && plan[rowIndex][colIndex - 1] == EMPTY_SPACE) {
            polishLeft(plan, rowIndex, colIndex);
        }

        if (colIndex + 1 < plan[rowIndex].length && plan[rowIndex][colIndex + 1] == EMPTY_SPACE) {
            polishRight(plan, rowIndex, colIndex);
        }
    }

    private static void polishBottom(char[][] plan, int rowIndex, int colIndex) {
        char curValue = plan[rowIndex][colIndex];
        plan[rowIndex][colIndex] = switch (curValue) {
            case LEFT_UP_ANGLE, RIGHT_UP_ANGLE, LEFT_DOWN_ANGLE,
                RIGHT_DOWN_ANGLE, VERTICAL_SMOOTH_WALL, HORIZONTAL_SMOOTH_WALL, HORIZONTAL_UP_LEDGE_WALL -> curValue;
            case VERTICAL_LEFT_LEDGE_WALL -> RIGHT_DOWN_ANGLE;
            case VERTICAL_RIGHT_LEDGE_WALL -> LEFT_DOWN_ANGLE;
            case HORIZONTAL_DOWN_LEDGE_WALL -> HORIZONTAL_SMOOTH_WALL;
            case CROSS_WALL -> HORIZONTAL_UP_LEDGE_WALL;
            default -> throw new RuntimeException(NOT_EXPECTED_CASE_MESSAGE + curValue);
        };
    }

    private static void polishTop(char[][] plan, int rowIndex, int colIndex) {
        char curValue = plan[rowIndex][colIndex];
        plan[rowIndex][colIndex] = switch (curValue) {
            case LEFT_UP_ANGLE, RIGHT_UP_ANGLE, LEFT_DOWN_ANGLE,
                RIGHT_DOWN_ANGLE, VERTICAL_SMOOTH_WALL, HORIZONTAL_SMOOTH_WALL, HORIZONTAL_DOWN_LEDGE_WALL -> curValue;
            case VERTICAL_LEFT_LEDGE_WALL -> RIGHT_UP_ANGLE;
            case VERTICAL_RIGHT_LEDGE_WALL -> LEFT_DOWN_ANGLE;
            case HORIZONTAL_UP_LEDGE_WALL -> HORIZONTAL_SMOOTH_WALL;
            case CROSS_WALL -> HORIZONTAL_DOWN_LEDGE_WALL;
            default -> throw new RuntimeException(NOT_EXPECTED_CASE_MESSAGE + curValue);
        };
    }

    private static void polishLeft(char[][] plan, int rowIndex, int colIndex) {
        char curValue = plan[rowIndex][colIndex];
        plan[rowIndex][colIndex] = switch (curValue) {
            case LEFT_UP_ANGLE, RIGHT_UP_ANGLE, LEFT_DOWN_ANGLE,
                RIGHT_DOWN_ANGLE, HORIZONTAL_SMOOTH_WALL -> curValue;
            case VERTICAL_SMOOTH_WALL, VERTICAL_LEFT_LEDGE_WALL -> VERTICAL_SMOOTH_WALL;
            case VERTICAL_RIGHT_LEDGE_WALL -> VERTICAL_RIGHT_LEDGE_WALL;
            case HORIZONTAL_UP_LEDGE_WALL -> LEFT_DOWN_ANGLE;
            case HORIZONTAL_DOWN_LEDGE_WALL -> LEFT_UP_ANGLE;
            case CROSS_WALL -> VERTICAL_RIGHT_LEDGE_WALL;
            default -> throw new RuntimeException(NOT_EXPECTED_CASE_MESSAGE + curValue);
        };
    }

    private static void polishRight(char[][] plan, int rowIndex, int colIndex) {
        char curValue = plan[rowIndex][colIndex];
        plan[rowIndex][colIndex] = switch (curValue) {
            case LEFT_UP_ANGLE, RIGHT_UP_ANGLE, LEFT_DOWN_ANGLE,
                RIGHT_DOWN_ANGLE, HORIZONTAL_SMOOTH_WALL -> curValue;
            case VERTICAL_SMOOTH_WALL, VERTICAL_RIGHT_LEDGE_WALL -> VERTICAL_SMOOTH_WALL;
            case VERTICAL_LEFT_LEDGE_WALL -> VERTICAL_LEFT_LEDGE_WALL;
            case HORIZONTAL_UP_LEDGE_WALL -> RIGHT_DOWN_ANGLE;
            case HORIZONTAL_DOWN_LEDGE_WALL -> RIGHT_UP_ANGLE;
            case CROSS_WALL -> VERTICAL_LEFT_LEDGE_WALL;
            default -> throw new RuntimeException(NOT_EXPECTED_CASE_MESSAGE + curValue);
        };
    }

    private final static char EMPTY_SPACE = '\u0020';
//    private final static char AMOGUS_PATH_MARKER = '*';
    private final static char AMOGUS_PATH_MARKER = '\u0D9E';

    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_BLACK = "\u001B[30m";

    private final static char LEFT_UP_ANGLE = '\u2554';
    private final static char RIGHT_UP_ANGLE = '\u2557';
    private final static char LEFT_DOWN_ANGLE = '\u255A';
    private final static char RIGHT_DOWN_ANGLE = '\u255D';

    private final static char VERTICAL_SMOOTH_WALL = '\u2551';
    private final static char HORIZONTAL_SMOOTH_WALL = '\u2550';

    private final static char VERTICAL_LEFT_LEDGE_WALL = '\u2563';
    private final static char VERTICAL_RIGHT_LEDGE_WALL = '\u2560';

    private final static char HORIZONTAL_UP_LEDGE_WALL = '\u2569';
    private final static char HORIZONTAL_DOWN_LEDGE_WALL = '\u2566';

    private final static char CROSS_WALL = '\u256C';

    private static final Direction[] DIRECTIONS = new Direction[] {
        Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT
    };

    private static final String NOT_EXPECTED_CASE_MESSAGE = "Not expected case while shaping: ";
    private final static Logger LOGGER = LogManager.getLogger();
}
