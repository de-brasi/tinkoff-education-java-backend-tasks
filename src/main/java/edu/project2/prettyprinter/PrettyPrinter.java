package edu.project2.prettyprinter;

import edu.project2.util.Cell;
import edu.project2.util.Coordinate;
import edu.project2.util.Direction;
import edu.project2.util.Maze;

import java.util.List;

public class PrettyPrinter {
    public PrettyPrinter() {}

    public void Print(Maze maze, List<Coordinate> path) {
        final var start = path.get(0);
        final var finish = path.get(path.size() - 1);

        // TODO:
        //  1) print maze;
        //  2) print path;

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
                // TODO: чекать следующего кандидата,
                //  смотреть по какому направлению он находится,
                //  брать клетку этого направления, помечать ее
                nextMazePosition = path.get(i + 1);
                directionToNext = getDirectionTo(curMazePosition, nextMazePosition);
                intermediateStepCoordinate = realPlanCoordinate.coordinateFromDirection(directionToNext);

                mazePlan[intermediateStepCoordinate.row()][intermediateStepCoordinate.col()] = amogusPathMarker;
            }

            mazePlan[realPlanCoordinate.row()][realPlanCoordinate.col()] = amogusPathMarker;
        }

    }

    private static Direction getDirectionTo(Coordinate from, Coordinate to) {
        for (Direction directionToCheck : directions) {
            if (to.equals(from.coordinateFromDirection(directionToCheck))) {
                return directionToCheck;
            }
        }
        throw new IllegalArgumentException("Unexpected pair of coordinates! The must to be neighbours.");
    }

    private static void print(char[][] maze) {
        // TODO: отличать метки пути и выводить их с другим цветом
        for (var row: maze) {
            for (char rowValue: row) {
                if (rowValue == amogusPathMarker) {
                    System.out.print(ANSI_RED + rowValue + ANSI_RED);
//                    System.out.print(rowValue);
                } else {
                    System.out.print(ANSI_BLACK + rowValue + ANSI_BLACK);
//                    System.out.print(rowValue);
                }
            }
            System.out.println();
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

        mazePlan[topRow][0] = leftUpAngle;
        mazePlan[topRow][mazePlan[topRow].length - 1] = rightUpAngle;
        for (int colIdx = 1; colIdx < mazePlan[topRow].length - 1; colIdx++) {
            if (mazePlan[topRow + 1][colIdx] == emptySpace) {
                mazePlan[topRow][colIdx] = horizontalSmoothWall;
            } else {
                mazePlan[topRow][colIdx] = horizontalDownLedgeWall;
            }
        }

        mazePlan[bottomRow][0] = leftDownAngle;
        mazePlan[bottomRow][mazePlan[bottomRow].length - 1] = rightDownAngle;
        for (int colIdx = 1; colIdx < mazePlan[bottomRow].length - 1; colIdx++) {
            if (mazePlan[bottomRow - 1][colIdx] == emptySpace) {
                mazePlan[bottomRow][colIdx] = horizontalSmoothWall;
            } else {
                mazePlan[bottomRow][colIdx] = horizontalUpLedgeWall;
            }
        }

        for (int middleRow = 1; middleRow < mazePlan.length - 1; middleRow++) {
            if (mazePlan[middleRow][/* right-side space */1] == emptySpace) {
                mazePlan[middleRow][0] = verticalSmoothWall;
            } else {
                mazePlan[middleRow][0] = verticalRightLedgeWall;
            }

            if (mazePlan[middleRow][/* left-side space */mazePlan[middleRow].length - 1 - 1] == emptySpace) {
                mazePlan[middleRow][mazePlan[middleRow].length - 1] = verticalSmoothWall;
            } else {
                mazePlan[middleRow][mazePlan[middleRow].length - 1] = verticalLeftLedgeWall;
            }
        }
    }

    private static void setStartStatement(char[][] mazePlan) {
        for (int i = 0; i < mazePlan.length; i++) {
            if (i % 2 == 1) {
                for (int j = 0; j < mazePlan[i].length; j++) {
                    if (j % 2 == 1) {
                        mazePlan[i][j] = emptySpace;
                    } else {
                        mazePlan[i][j] = verticalSmoothWall;
                    }
                }
            } else {
                for (int j = 0; j < mazePlan[i].length; j++) {
                    if (j % 2 == 1) {
                        mazePlan[i][j] = horizontalSmoothWall;
                    } else {
                        mazePlan[i][j] = crossWall;
                    }
                }
            }
        }

        setBorders(mazePlan);
    }

    private void buildWallsNearCell(char[][] plan, Coordinate cellCoordinate, Cell cell) {
        Coordinate wallCoordinate;

        for (final var direction: this.directions) {
            if (!cell.checkWall(direction)) {
                wallCoordinate = cellCoordinate.coordinateFromDirection(direction);
                plan[wallCoordinate.row()][wallCoordinate.col()] = emptySpace;
            }
        }
    }

    private static void smoothCorners(char[][] plan) {
        for (int rowIndex = 0; rowIndex < plan.length; rowIndex++) {
            for (int colIndex = 0; colIndex < plan[rowIndex].length; colIndex++) {
                if (plan[rowIndex][colIndex] != emptySpace) {
                    polishWall(plan, rowIndex, colIndex);
                }
            }
        }
    }

    private static void polishWall(char[][] plan, int rowIndex, int colIndex) {
        if (rowIndex + 1 < plan.length && plan[rowIndex + 1][colIndex] == emptySpace) {
            polishBottom(plan, rowIndex, colIndex);
        }

        if (0 < rowIndex - 1 && plan[rowIndex - 1][colIndex] == emptySpace) {
            polishTop(plan, rowIndex, colIndex);
        }

        if (0 < colIndex - 1 && plan[rowIndex][colIndex - 1] == emptySpace) {
            polishLeft(plan, rowIndex, colIndex);
        }

        if (colIndex + 1 < plan[rowIndex].length && plan[rowIndex][colIndex + 1] == emptySpace) {
            polishRight(plan, rowIndex, colIndex);
        }
    }

    private static void polishBottom(char[][] plan, int rowIndex, int colIndex) {
        char curValue = plan[rowIndex][colIndex];
        plan[rowIndex][colIndex] = switch (curValue) {
            case leftUpAngle, rightUpAngle, leftDownAngle, rightDownAngle, verticalSmoothWall, horizontalSmoothWall, horizontalUpLedgeWall -> curValue;
            case verticalLeftLedgeWall -> rightDownAngle;
            case verticalRightLedgeWall -> leftDownAngle;
            case horizontalDownLedgeWall -> horizontalSmoothWall;
            case crossWall -> horizontalUpLedgeWall;
            default -> throw new RuntimeException("Not expected case while shaping: " + curValue);
        };
    }
    private static void polishTop(char[][] plan, int rowIndex, int colIndex) {
        char curValue = plan[rowIndex][colIndex];
        plan[rowIndex][colIndex] = switch (curValue) {
            case leftUpAngle, rightUpAngle, leftDownAngle, rightDownAngle, verticalSmoothWall, horizontalSmoothWall, horizontalDownLedgeWall -> curValue;
            case verticalLeftLedgeWall -> rightUpAngle;
            case verticalRightLedgeWall -> leftDownAngle;
            case horizontalUpLedgeWall -> horizontalSmoothWall;
            case crossWall -> horizontalDownLedgeWall;
            default -> throw new RuntimeException("Not expected case while shaping: " + curValue);
        };
    }
    private static void polishLeft(char[][] plan, int rowIndex, int colIndex) {
        char curValue = plan[rowIndex][colIndex];
        plan[rowIndex][colIndex] = switch (curValue) {
            case leftUpAngle, rightUpAngle, leftDownAngle, rightDownAngle, horizontalSmoothWall -> curValue;
            case verticalSmoothWall, verticalLeftLedgeWall -> verticalSmoothWall;
            case verticalRightLedgeWall -> verticalRightLedgeWall;
            case horizontalUpLedgeWall -> leftDownAngle;
            case horizontalDownLedgeWall -> leftUpAngle;
            case crossWall -> verticalRightLedgeWall;
            default -> throw new RuntimeException("Not expected case while shaping: " + curValue);
        };
    }
    private static void polishRight(char[][] plan, int rowIndex, int colIndex) {
        char curValue = plan[rowIndex][colIndex];
        plan[rowIndex][colIndex] = switch (curValue) {
            case leftUpAngle, rightUpAngle, leftDownAngle, rightDownAngle, horizontalSmoothWall -> curValue;
            case verticalSmoothWall, verticalRightLedgeWall -> verticalSmoothWall;
            case verticalLeftLedgeWall -> verticalLeftLedgeWall;
            case horizontalUpLedgeWall -> rightDownAngle;
            case horizontalDownLedgeWall -> rightUpAngle;
            case crossWall -> verticalLeftLedgeWall;
            default -> throw new RuntimeException("Not expected case while shaping: " + curValue);
        };
    }

    private final static char emptySpace = '\u0020';
    private final static char amogusPathMarker = '*';
//    private final static char amogusPathMarker = '\u0D9E';

    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_BLACK = "\u001B[30m";

    private final static char leftUpAngle = '\u2554';
    private final static char rightUpAngle = '\u2557';
    private final static char leftDownAngle = '\u255A';
    private final static char rightDownAngle = '\u255D';

    private final static char verticalSmoothWall = '\u2551';
    private final static char horizontalSmoothWall = '\u2550';

    private final static char verticalLeftLedgeWall = '\u2563';
    private final static char verticalRightLedgeWall = '\u2560';

    private final static char horizontalUpLedgeWall = '\u2569';
    private final static char horizontalDownLedgeWall = '\u2566';

    private final static char crossWall = '\u256C';

    private static final Direction[] directions = new Direction[] {
        Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT
    };
}
