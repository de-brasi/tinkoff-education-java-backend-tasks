package edu.project2.solvers;

import edu.project2.util.Cell;
import edu.project2.util.Coordinate;
import edu.project2.util.Direction;
import edu.project2.util.Maze;
import edu.project2.util.Solver;
import java.util.ArrayDeque;
import java.util.ArrayList;

public class SolverDFS implements Solver {
    public SolverDFS() {}

    @Override
    public ArrayList<Coordinate> solve(Maze maze, Coordinate start, Coordinate end) {
        var solution = new ArrayDeque<Coordinate>();

        Coordinate curCoordinate;
        Coordinate nextStepCoordinate;

        Cell curCell;
        Cell nextCell;

        maze.getCell(start).visit();
        solution.addLast(start);

        while (!solution.isEmpty() && !solution.getLast().equals(end)) {
            curCoordinate = solution.removeLast();
            curCell = maze.getCell(curCoordinate);


            for (var direction : this.directions) {
                nextStepCoordinate = curCoordinate.coordinateFromDirection(direction);

                if (curCell.checkWallExistence(direction)
                    || !coordinateIsValid(nextStepCoordinate, maze)
                    || maze.getCell(nextStepCoordinate).checkIsVisited()) {
                    continue;
                }

                nextCell = maze.getCell(nextStepCoordinate);
                nextCell.visit();
                solution.addLast(curCoordinate);
                solution.addLast(nextStepCoordinate);
                break;
            }
        }

        return new ArrayList<>(solution);
    }

    private final Direction[] directions = new Direction[] {
        Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT
    };

    private static boolean coordinateIsValid(Coordinate toCheck, Maze toCheckIn) {
        return (0 <= toCheck.row() && toCheck.row() < toCheckIn.getHeight())
            && (0 <= toCheck.col() && toCheck.col() < toCheckIn.getWidth());
    }
}
