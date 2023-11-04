package edu.project2.prettyprinter;

import edu.project2.util.Cell;
import edu.project2.util.Coordinate;
import edu.project2.util.Direction;
import edu.project2.util.Maze;

import java.util.Arrays;
import java.util.List;

public class PrettyPrinter {
    private PrettyPrinter() {
        // not allowed
    }

    public static void Print(Maze maze, List<Coordinate> path) {
//        final var start = path.get(0);
//        final var finish = path.get(path.size() - 1);

        // TODO:
        //  1) print maze;
        //  2) print path;

        final var walls = getWalls(maze);

        for (var row: walls) {
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
            curCellRowInMazePlane = 2 * mazeRowIndex + 1;

            for (int mazeColIndex = 0; mazeColIndex < curRow.length; mazeColIndex++) {
                curCell = curRow[mazeColIndex];
                curCellColInMazePlane = 2 * mazeColIndex + 1;

                curCellCoordinateInMazePlane = new Coordinate(curCellRowInMazePlane, curCellColInMazePlane);

                buildWalls(mazePlane, curCellCoordinateInMazePlane, curCell);
            }
        }

        setBorders(mazePlane);

        return mazePlane;
    }

    private static void setBorders(char[][] mazePlan) {
        // TODO: учесть что стены могут быть уже установленны на ячейку, тогда "сгладить" их по внешнему контуру

        if (mazePlan.length == 0) {
            return;
        }

        final int topRow = 0;
        final int bottomRow = mazePlan.length - 1;

        mazePlan[topRow][0] = leftUpAngle;
        mazePlan[topRow][mazePlan[topRow].length - 1] = rightUpAngle;
        for (int colIdx = 1; colIdx < mazePlan[topRow].length - 1; colIdx++) {
            mazePlan[topRow][colIdx] = horizontalSmoothWall;
        }

        mazePlan[bottomRow][0] = leftDownAngle;
        mazePlan[bottomRow][mazePlan[bottomRow].length - 1] = rightDownAngle;
        for (int colIdx = 1; colIdx < mazePlan[bottomRow].length - 1; colIdx++) {
            mazePlan[bottomRow][colIdx] = horizontalSmoothWall;
        }

        for (int middleRow = 1; middleRow < mazePlan.length - 1; middleRow++) {
            mazePlan[middleRow][0] = verticalSmoothWall;
            mazePlan[middleRow][mazePlan[middleRow].length - 1] = verticalSmoothWall;
        }
    }

    private static void setEmptySpace(char[][] mazePlan) {
        for (char[] chars : mazePlan) {
            Arrays.fill(chars, emptySpace);
        }
    }

    private void buildWalls(char[][] plane, Coordinate cellCoordinate, Cell cell) {
        Coordinate neighbour;
        for (final var direction: this.directions) {
            // todo: что за хуйня тут происходит
            if (!cell.checkWall(direction)) {
                continue;
            }

            neighbour = cellCoordinate.coordinateFromDirection(direction);
            plane[neighbour.row()][neighbour.col()] = joinWalls(plane[neighbour.row()][neighbour.col()], direction);
        }

        // todo: сгладить угловые клетки
    }

    private static char joinWalls(char alreadyBuildWall,
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

    private final static char emptySpace = '\u0020';

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
