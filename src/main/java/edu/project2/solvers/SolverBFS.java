package edu.project2.solvers;

import edu.project2.util.Cell;
import edu.project2.util.Coordinate;
import edu.project2.util.Direction;
import edu.project2.util.Maze;
import edu.project2.util.Solver;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class SolverBFS implements Solver {
    public SolverBFS() {}

    @Override
    public ArrayList<Coordinate> solve(Maze maze, Coordinate start, Coordinate finish) {
        maze.resetCells();

        final var distanceToCoordinatesMap = new HashMap<Integer, HashSet<Coordinate>>();

        final int maxDistance = maze.getHeight() * maze.getWidth();
        int distanceFromStart = 0;
        distanceToCoordinatesMap.put(distanceFromStart, new HashSet<>());
        distanceToCoordinatesMap.get(distanceFromStart).add(start);
        maze.getCell(start).visit();

        Coordinate suspectNextCellCoordinate;

        HashSet<Coordinate> setLookupFrom;
        HashSet<Coordinate> setToAddNew;

        boolean finishReached = start.equals(finish);

        for (distanceFromStart = 1; distanceFromStart < maxDistance + 1; distanceFromStart++) {
            if (finishReached) {
                --distanceFromStart;
                break;
            }

            setLookupFrom = distanceToCoordinatesMap.get(distanceFromStart - 1);
            distanceToCoordinatesMap.put(distanceFromStart, new HashSet<>());
            setToAddNew = distanceToCoordinatesMap.get(distanceFromStart);

            for (Coordinate prevDistanceCellCoordinate : setLookupFrom) {
                for (Direction directionToNext: DIRECTIONS) {
                    suspectNextCellCoordinate = prevDistanceCellCoordinate.coordinateFromDirection(directionToNext);

                    if (coordinateIsValid(suspectNextCellCoordinate, maze)
                        && !maze.getCell(suspectNextCellCoordinate).checkIsVisited()
                        && !maze.getCell(prevDistanceCellCoordinate).checkWallExistence(directionToNext)) {

                        setToAddNew.add(suspectNextCellCoordinate);
                        maze.getCell(suspectNextCellCoordinate).visit();

                        if (maze.getCell(suspectNextCellCoordinate).equals(maze.getCell(finish))) {
                            finishReached = true;
                            break;
                        }
                    }

                }
            }
        }

        if (!finishReached) {
            throw new RuntimeException("Can't reach finish :(");
        }

        var solution = new ArrayDeque<Coordinate>();

        if (start.equals(finish)) {
            solution.addFirst(finish);
            return new ArrayList<Coordinate>(solution);
        }

        Coordinate prevCoordinate = finish;
        solution.addFirst(finish);

        for (int distance = distanceFromStart - 1; distance > 0; distance--) {
            for (var prev : distanceToCoordinatesMap.get(distance)) {
                if (isOneStepReachable(maze, prevCoordinate, prev)) {
                    solution.addFirst(prev);
                    prevCoordinate = prev;
                }
            }
        }
        solution.addFirst(start);

        return new ArrayList<>(solution);
    }

    private static boolean coordinateIsValid(Coordinate toCheck, Maze toCheckIn) {
        return (0 <= toCheck.row() && toCheck.row() < toCheckIn.getHeight())
            && (0 <= toCheck.col() && toCheck.col() < toCheckIn.getWidth());
    }

    private static boolean isOneStepReachable(Maze maze, Coordinate from, Coordinate to) {
        Cell toCell = maze.getCell(to);

        for (var direction : DIRECTIONS) {
            if (coordinateIsValid(from.coordinateFromDirection(direction), maze)
                && maze.getCell(from.coordinateFromDirection(direction)).equals(toCell)) {
                return true;
            }
        }

        return false;
    }

    private static final Direction[] DIRECTIONS = new Direction[] {
        Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT
    };
}
