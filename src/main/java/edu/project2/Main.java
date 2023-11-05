package edu.project2;

import edu.project2.generators.RandomizedDFSGenerator;
import edu.project2.prettyprinter.PrettyPrinter;
import edu.project2.solvers.SolverBFS;
import edu.project2.solvers.SolverDFS;
import edu.project2.solvers.SolverRightHandRule;
import edu.project2.util.Coordinate;

public class Main {
    private Main() {
        // not allowed
    }

    public static void main(String[] args) {
        final var randomGenerator = new RandomizedDFSGenerator();
        final var dfsSolver = new SolverDFS();
        final var bfsSolver = new SolverBFS();
        final var rhrSolver = new SolverRightHandRule();
        final var printer = new PrettyPrinter();

        final var maze = randomGenerator.generate(5, 5);

        final var start = new Coordinate(0, 0);
        final var end = new Coordinate(maze.getHeight() - 1, maze.getWidth() - 1);

        var solution = dfsSolver.solve(maze, start, end);
        printer.print(maze, solution);

        maze.resetCells();

        solution = bfsSolver.solve(maze, start, end);
        printer.print(maze, solution);

        maze.resetCells();

        solution = rhrSolver.solve(maze, start, end);
        printer.print(maze, solution);
    }
}
