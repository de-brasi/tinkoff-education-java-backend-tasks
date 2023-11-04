package edu.project2.prettyprinter;

import edu.project2.util.Cell;
import edu.project2.util.Coordinate;
import edu.project2.util.Direction;
import edu.project2.util.Maze;

import java.util.Arrays;
import java.util.List;

public class PrettyPrinter {
    public PrettyPrinter() {}

    public void Print(Maze maze, List<Coordinate> path) {
//        final var start = path.get(0);
//        final var finish = path.get(path.size() - 1);

        // TODO:
        //  1) print maze;
        //  2) print path;

        final var walls = getWalls(maze);
        print(walls);
    }

    private static void print(char[][] maze) {
        for (var row: maze) {
            System.out.println(row);
        }
    }

    private char[][] getWalls(Maze maze) {
        if (maze.getWidth() == 0 || maze.getHeight() == 0) {
            return new char[0][0];
        }

        var mazePlane = new char[2 * maze.getHeight() + 1][2 * maze.getWidth() + 1];

        setEmptySpace(mazePlane);

        Cell[] curRow;
        Cell curCell;

        int curCellRowInMazePlane;
        int curCellColInMazePlane;

        Coordinate curCellCoordinateInMazePlane;

        for (int mazeRowIndex = 0; mazeRowIndex < maze.getHeight(); mazeRowIndex++) {
            curRow = maze.getRowShallowCopy(mazeRowIndex);

            for (int mazeColIndex = 0; mazeColIndex < curRow.length; mazeColIndex++) {
                curCell = curRow[mazeColIndex];

                curCellColInMazePlane = 2 * mazeColIndex + 1;
                curCellRowInMazePlane = 2 * mazeRowIndex + 1;

                curCellCoordinateInMazePlane = new Coordinate(curCellRowInMazePlane, curCellColInMazePlane);

                buildWallsNearCell(mazePlane, curCellCoordinateInMazePlane, curCell);
            }
        }

        setBorders(mazePlane);

        return mazePlane;
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

    private static void setEmptySpace(char[][] mazePlan) {
        for (char[] chars : mazePlan) {
            Arrays.fill(chars, emptySpace);
        }
    }

    private void buildWallsNearCell(char[][] plan, Coordinate cellCoordinate, Cell cell) {
        Coordinate neighbourCell;
        int neighbourCellRowInMazePlane;
        int neighbourCellColInMazePlane;

        for (final var direction: this.directions) {
            if (!cell.checkWall(direction)) {
                // TODO: может просто удалять стену??
                continue;
            }

            // TODO: тут была проблема в том, что я получал координаты соседа как если бы я работал с типом Maze,
            //  однако эти координаты я использовал как координаты соседа (НЕ буфера каждой клетки, а соседа)
            //  в массиве plan.
            //  Вроде как исправил - стал брать реальные координаты от этих координат, но словил кринж((
            neighbourCell = cellCoordinate.coordinateFromDirection(direction);
            plan[neighbourCell.row()][neighbourCell.col()] =
                mergeWalls(
                    plan[neighbourCell.row()][neighbourCell.col()],
                    direction
                );

//            neighbourCell = cellCoordinate.coordinateFromDirection(direction);
//            neighbourCellRowInMazePlane = 2 * neighbourCell.row() + 1;
//            neighbourCellColInMazePlane = 2 * neighbourCell.col() + 1;
//
//            if (0 <= neighbourCellRowInMazePlane
//                && neighbourCellRowInMazePlane < plan.length
//                && 0 <= neighbourCellColInMazePlane
//                && neighbourCellColInMazePlane < plan[neighbourCellRowInMazePlane].length - 1) {
//                // TODO: real coordinates
//                plan[neighbourCellRowInMazePlane][neighbourCellColInMazePlane] =
//                    mergeWalls(
//                        plan[neighbourCellRowInMazePlane][neighbourCellColInMazePlane],
//                        direction
//                    );
//            }
        }

        mergeAllAnglesWithNeighbourWalls(plan, cellCoordinate);
    }

    private static char mergeWalls(char alreadyBuildWall,
        Direction newWallDirection /*relative to the center of the cell*/) {
        return switch (newWallDirection) {
            case UP -> {
                if (alreadyBuildWall == emptySpace
                    || alreadyBuildWall == horizontalSmoothWall
                    || alreadyBuildWall == leftUpAngle
                    || alreadyBuildWall == rightUpAngle
                    || alreadyBuildWall == horizontalDownLedgeWall) {
                    yield horizontalSmoothWall;
                } else if (alreadyBuildWall == verticalSmoothWall
                    || alreadyBuildWall == horizontalUpLedgeWall
                    || alreadyBuildWall == verticalLeftLedgeWall
                    || alreadyBuildWall == verticalRightLedgeWall
                    || alreadyBuildWall == leftDownAngle
                    || alreadyBuildWall == rightDownAngle) {
                    yield horizontalUpLedgeWall;
                } else {
                    throw new RuntimeException("Not expected case while wall merging");
                }
            }
            case DOWN -> {
                if (alreadyBuildWall == emptySpace
                    || alreadyBuildWall == horizontalSmoothWall
                    || alreadyBuildWall == leftDownAngle
                    || alreadyBuildWall == rightDownAngle
                    || alreadyBuildWall == horizontalUpLedgeWall) {
                    yield horizontalSmoothWall;
                } else if (alreadyBuildWall == verticalSmoothWall
                    || alreadyBuildWall == horizontalDownLedgeWall
                    || alreadyBuildWall == verticalLeftLedgeWall
                    || alreadyBuildWall == verticalRightLedgeWall
                    || alreadyBuildWall == leftUpAngle
                    || alreadyBuildWall == rightUpAngle) {
                    yield horizontalDownLedgeWall;
                } else {
                    throw new RuntimeException("Not expected case while wall merging");
                }
            }
            case LEFT -> {
                if (alreadyBuildWall == emptySpace
                    || alreadyBuildWall == verticalSmoothWall
                    || alreadyBuildWall == leftUpAngle
                    || alreadyBuildWall == leftDownAngle
                    || alreadyBuildWall == verticalRightLedgeWall) {
                    yield verticalSmoothWall;
                } else if (alreadyBuildWall == rightUpAngle
                    || alreadyBuildWall == rightDownAngle
                    || alreadyBuildWall == horizontalSmoothWall
                    || alreadyBuildWall == verticalLeftLedgeWall
                    || alreadyBuildWall == horizontalUpLedgeWall
                    || alreadyBuildWall == horizontalDownLedgeWall) {
                    yield verticalLeftLedgeWall;
                } else {
                    throw new RuntimeException("Not expected case while wall merging");
                }
            }
            case RIGHT -> {
                if (alreadyBuildWall == emptySpace
                    || alreadyBuildWall == verticalSmoothWall
                    || alreadyBuildWall == rightUpAngle
                    || alreadyBuildWall == rightDownAngle
                    || alreadyBuildWall == verticalLeftLedgeWall) {
                    yield verticalSmoothWall;
                } else if (alreadyBuildWall == leftUpAngle
                    || alreadyBuildWall == leftDownAngle
                    || alreadyBuildWall == horizontalSmoothWall
                    || alreadyBuildWall == verticalRightLedgeWall
                    || alreadyBuildWall == horizontalUpLedgeWall
                    || alreadyBuildWall == horizontalDownLedgeWall) {
                    yield verticalRightLedgeWall;
                } else {
                    throw new RuntimeException("Not expected case while wall merging");
                }
            }
        };
    }

    private static char mergeWalls(char alreadyBuildWall, char toBuildWall) {
        return switch (toBuildWall) {
            case leftDownAngle -> mergeLeftDownAngleWith(alreadyBuildWall);
            case leftUpAngle -> mergeLeftUpAngleWith(alreadyBuildWall);
            case rightUpAngle -> mergeRightUpAngleWith(alreadyBuildWall);
            case rightDownAngle -> mergeRightDownAngleWith(alreadyBuildWall);
            case emptySpace -> alreadyBuildWall;
            default -> throw new RuntimeException("Not expected case while wall merging");
        };
    }

    private static char mergeLeftDownAngleWith(char toMerge) {
        return switch (toMerge) {
            case emptySpace, leftDownAngle -> leftDownAngle;
            case leftUpAngle, rightUpAngle, verticalLeftLedgeWall, horizontalDownLedgeWall, crossWall -> crossWall;
            case rightDownAngle, horizontalSmoothWall, horizontalUpLedgeWall -> horizontalUpLedgeWall;
            case verticalSmoothWall, verticalRightLedgeWall -> verticalRightLedgeWall;
            default -> throw new RuntimeException("Not expected case while wall merging with toMerge:" + toMerge);
        };
    }

    private static char mergeLeftUpAngleWith(char toMerge) {
        return switch (toMerge) {
            case emptySpace, leftUpAngle -> leftUpAngle;
            case rightUpAngle, horizontalSmoothWall, horizontalDownLedgeWall -> horizontalDownLedgeWall;
            case leftDownAngle, verticalSmoothWall, verticalRightLedgeWall -> verticalRightLedgeWall;
            case rightDownAngle, verticalLeftLedgeWall, horizontalUpLedgeWall, crossWall -> crossWall;
            default -> throw new RuntimeException("Not expected case while wall merging with toMerge:" + toMerge);
        };
    }

    private static char mergeRightUpAngleWith(char toMerge) {
        return switch (toMerge) {
            case emptySpace, rightUpAngle -> rightUpAngle;
            case leftUpAngle, horizontalSmoothWall, horizontalDownLedgeWall -> horizontalDownLedgeWall;
            case leftDownAngle, verticalRightLedgeWall, horizontalUpLedgeWall, crossWall -> crossWall;
            case rightDownAngle, verticalSmoothWall, verticalLeftLedgeWall -> verticalLeftLedgeWall;
            default -> throw new RuntimeException("Not expected case while wall merging with toMerge:" + toMerge);
        };
    }

    private static char mergeRightDownAngleWith(char toMerge) {
        return switch (toMerge) {
            case emptySpace, rightDownAngle -> rightDownAngle;
            case leftUpAngle, verticalRightLedgeWall, horizontalDownLedgeWall, crossWall -> crossWall;
            case rightUpAngle, verticalSmoothWall, verticalLeftLedgeWall -> verticalLeftLedgeWall;
            case leftDownAngle, horizontalSmoothWall, horizontalUpLedgeWall -> horizontalUpLedgeWall;
            default -> throw new RuntimeException("Not expected case while wall merging with toMerge:" + toMerge);
        };
    }

    private static void mergeAllAnglesWithNeighbourWalls(char[][] plan, Coordinate centre) {
        final char leftValue = plan[centre.row()][centre.col() - 1];
        final char topValue = plan[centre.row() - 1][centre.col()];
        final char rightValue = plan[centre.row()][centre.col() + 1];
        final char bottomValue = plan[centre.row() + 1][centre.col()];

        char leftDownAngleValueForMerging =
            (bottomValue != emptySpace && leftValue != emptySpace) ? leftDownAngle : emptySpace;
        char leftUpAngleValueForMerging =
            (leftValue != emptySpace && topValue != emptySpace) ? leftUpAngle : emptySpace;
        char rightUpAngleValueForMerging =
            (topValue != emptySpace && rightValue != emptySpace) ? rightUpAngle : emptySpace;
        char rightDownAngleValueForMerging =
            (rightValue != emptySpace && bottomValue != emptySpace) ? rightDownAngle : emptySpace;

        plan[centre.row() + 1][centre.col() - 1] = mergeWalls(
            plan[centre.row() + 1][centre.col() - 1],
            leftDownAngleValueForMerging);
        plan[centre.row() - 1][centre.col() - 1] = mergeWalls(
            plan[centre.row() - 1][centre.col() - 1],
            leftUpAngleValueForMerging);
        plan[centre.row() - 1][centre.col() + 1] = mergeWalls(
            plan[centre.row() - 1][centre.col() + 1],
            rightUpAngleValueForMerging);
        plan[centre.row() + 1][centre.col() + 1] = mergeWalls(
            plan[centre.row() + 1][centre.col() + 1],
            rightDownAngleValueForMerging);
    }

//    private final static char emptySpace = '\u0020';
    private final static char emptySpace = ' ';

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

    private final Direction[] directions = new Direction[] {
        Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT
    };
}
