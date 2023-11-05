package edu.project2.solvers;

import edu.project2.util.Cell;
import edu.project2.util.Coordinate;
import edu.project2.util.Direction;
import edu.project2.util.Maze;
import edu.project2.util.Solver;
import java.util.ArrayList;

public class SolverRightHandRule implements Solver {
    public SolverRightHandRule() {}

    @Override
    public ArrayList<Coordinate> solve(Maze maze, Coordinate start, Coordinate finish) {
        maze.resetCells();

        // For simply connected maze only!
        var result = new ArrayList<Coordinate>();

        if (start.equals(finish)) {
            result.add(start);
            return result;
        }

        var finishCell = maze.getCell(finish);

        var curCellCoordinate = start;
        var curCell = maze.getCell(curCellCoordinate);

        result.add(curCellCoordinate);

        Direction prevDirection = choseDirection(maze, curCellCoordinate);

        while (!curCell.equals(finishCell)) {
            curCellCoordinate = curCellCoordinate.coordinateFromDirection(prevDirection);
            curCell = maze.getCell(curCellCoordinate);

            result.add(curCellCoordinate);

            prevDirection = switch (prevDirection) {
                case RIGHT -> handleFromRightStep(curCell);
                case LEFT -> handleFromLeftStep(curCell);
                case UP -> handleFromUpStep(curCell);
                case DOWN -> handleFromDownStep(curCell);
            };

        }

        return result;
    }

    private static Direction handleFromRightStep(Cell curCell) {
        if (!curCell.checkWallExistence(Direction.DOWN)) {
            return Direction.DOWN;
        } else if (!curCell.checkWallExistence(Direction.RIGHT)) {
            return Direction.RIGHT;
        } else if (!curCell.checkWallExistence(Direction.UP)) {
            return Direction.UP;
        } else {
            return Direction.LEFT;
        }
    }

    private static Direction handleFromLeftStep(Cell curCell) {
        if (!curCell.checkWallExistence(Direction.UP)) {
            return Direction.UP;
        } else if (!curCell.checkWallExistence(Direction.LEFT)) {
            return Direction.LEFT;
        } else if (!curCell.checkWallExistence(Direction.DOWN)) {
            return Direction.DOWN;
        } else {
            return Direction.LEFT;
        }
    }

    private static Direction handleFromUpStep(Cell curCell) {
        if (!curCell.checkWallExistence(Direction.RIGHT)) {
            return Direction.RIGHT;
        } else if (!curCell.checkWallExistence(Direction.UP)) {
            return Direction.UP;
        } else if (!curCell.checkWallExistence(Direction.LEFT)) {
            return Direction.LEFT;
        } else {
            return Direction.DOWN;
        }
    }

    private static Direction handleFromDownStep(Cell curCell) {
        if (!curCell.checkWallExistence(Direction.LEFT)) {
            return Direction.LEFT;
        } else if (!curCell.checkWallExistence(Direction.DOWN)) {
            return Direction.DOWN;
        } else if (!curCell.checkWallExistence(Direction.RIGHT)) {
            return Direction.RIGHT;
        } else {
            return Direction.UP;
        }
    }

    private static Direction choseDirection(Maze maze, Coordinate position) {
        Cell curCell = maze.getCell(position);
        for (Direction direction : DIRECTIONS) {
            if (!curCell.checkWallExistence(direction)) {
                return direction;
            }
        }
        throw new RuntimeException("Unexpected case");
    }

    private static final Direction[] DIRECTIONS = new Direction[] {
        Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT
    };
}
