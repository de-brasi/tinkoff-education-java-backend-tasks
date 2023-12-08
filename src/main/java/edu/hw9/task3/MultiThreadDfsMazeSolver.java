package edu.hw9.task3;

import edu.project2.util.Cell;
import edu.project2.util.Coordinate;
import edu.project2.util.Direction;
import edu.project2.util.Maze;
import edu.project2.util.Solver;
import org.apache.logging.log4j.util.PropertySource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class MultiThreadDfsMazeSolver implements Solver {
    @Override
    public ArrayList<Coordinate> solve(Maze maze, Coordinate start, Coordinate end) {
        maze.resetCells();

        try (ForkJoinPool forkJoinPool = new ForkJoinPool()) {
            return forkJoinPool.invoke(new MazeSolverTask(maze, start, end));
        }
    }

    private static final Direction[] directions = new Direction[] {
        Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT
    };

    private static boolean coordinateIsValid(Coordinate toCheck, Maze toCheckIn) {
        return (0 <= toCheck.row() && toCheck.row() < toCheckIn.getHeight())
            && (0 <= toCheck.col() && toCheck.col() < toCheckIn.getWidth());
    }

    private static class MazeSolverTask extends RecursiveTask<ArrayList<Coordinate>> {
        MazeSolverTask(Maze maze, Coordinate startCoordinate, Coordinate targetCoordinate) {
            this.maze = maze;
            this.start = startCoordinate;
            this.finish = targetCoordinate;
        }

        @Override
        protected ArrayList<Coordinate> compute() {
            if (start.equals(finish)) {
                return new ArrayList<>(List.of(finish));
            }

            var traversalTasks = Arrays.stream(directions)
                .map(this::visitDestination)
                .filter(Objects::nonNull)
                .toList();

            var res = awaitAndChoseBestSolution(traversalTasks);

            if (res != null) {
                var answer = new ArrayList<>(List.of(start));
                answer.addAll(res);
                return answer;
            } else {
                return null;
            }
        }

        private MazeSolverTask visitDestination(Direction direction) {
            Cell curCell = maze.getCell(start);
            Coordinate nextStepCoordinate = start.coordinateFromDirection(direction);

            if (curCell.checkWallExistence(direction) || !coordinateIsValid(nextStepCoordinate, maze)) {
                return null;
            }

            Cell nextCell = maze.getCell(nextStepCoordinate);

            synchronized (nextCell) {
                if (nextCell.checkIsVisited()) {
                    return null;
                }

                nextCell.visit();
            }

            return new MazeSolverTask(maze, nextStepCoordinate, finish);
        }

        private ArrayList<Coordinate> awaitAndChoseBestSolution(List<MazeSolverTask> tasks) {
            if (tasks.isEmpty()) {
                return null;
            }

            var pivotTask = tasks.getFirst();
            pivotTask.fork();

            var otherTasksResult = tasks.stream().skip(1).map(MazeSolverTask::compute).toList();
            var pivotResult = pivotTask.join();

            var shortestFromOtherTasksResults = otherTasksResult.stream()
                .filter(Objects::nonNull)
                .min(
                    Comparator.comparing(ArrayList::size)
                );

            if (shortestFromOtherTasksResults.isEmpty()) {
                return pivotResult;
            } else if (pivotResult == null) {
                return shortestFromOtherTasksResults.get();
            } else if (pivotResult.size() < shortestFromOtherTasksResults.get().size()) {
                return pivotResult;
            } else {
                return shortestFromOtherTasksResults.get();
            }
        }

        private final Maze maze;
        private final Coordinate start;
        private final Coordinate finish;
    }
}
