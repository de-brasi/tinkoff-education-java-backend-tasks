package edu.hw9.task3;

import edu.project2.generators.RandomizedDFSGenerator;
import edu.project2.prettyprinter.PrettyPrinter;
import edu.project2.util.Coordinate;

public class SolverDemonstration {
    public static void main(String[] args) {
        final var randomGenerator = new RandomizedDFSGenerator();
        final var multiThreadDfsSolver = new MultiThreadDfsMazeSolver();
        final var maze = randomGenerator.generate(20, 10);
        final var printer = new PrettyPrinter();

        final var start = new Coordinate(0, 0);
        final var end = new Coordinate(maze.getHeight() - 1, maze.getWidth() - 1);

        var solution = multiThreadDfsSolver.solve(maze, start, end);
        printer.print(maze, solution);
    }
}
