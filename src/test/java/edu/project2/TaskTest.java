package edu.project2;

import edu.project2.generators.RandomizedDFSGenerator;
import edu.project2.solvers.SolverBFS;
import edu.project2.solvers.SolverDFS;
import edu.project2.solvers.SolverRightHandRule;
import edu.project2.util.Coordinate;
import edu.project2.util.Direction;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class TaskTest {
    @Test
    @DisplayName("Проверка генерации единичного лабиринта")
    public void test1() {
        final var randomGenerator = new RandomizedDFSGenerator();
        final var maze = randomGenerator.generate(1, 1);

        assertThat(maze.getHeight()).isEqualTo(1);
        assertThat(maze.getWidth()).isEqualTo(1);
        assertThat(maze.getCell(0, 0).checkWallExistence(Direction.UP)).isEqualTo(true);
        assertThat(maze.getCell(0, 0).checkWallExistence(Direction.DOWN)).isEqualTo(true);
        assertThat(maze.getCell(0, 0).checkWallExistence(Direction.LEFT)).isEqualTo(true);
        assertThat(maze.getCell(0, 0).checkWallExistence(Direction.RIGHT)).isEqualTo(true);
    }

    @Test
    @DisplayName("Проверка генерации лабиринта с неверными параметрами")
    public void test2() {
        final var randomGenerator = new RandomizedDFSGenerator();
        assertThatThrownBy(() -> randomGenerator.generate(-1, 0)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Проверка трех различных solver'ов при детерминированном лабиринте (единичной высоты)")
    public void test3() {
        final var randomGenerator = new RandomizedDFSGenerator();
        final var maze = randomGenerator.generate(10, 1);

        final var dfsSolver = new SolverDFS();
        final var bfsSolver = new SolverBFS();
        final var rhrSolver = new SolverRightHandRule();

        final var start = new Coordinate(0, 0);
        final var end = new Coordinate(maze.getHeight() - 1, maze.getWidth() - 1);

        final var dfsSolution = dfsSolver.solve(maze, start, end);
        final var bfsSolution = bfsSolver.solve(maze, start, end);
        final var rhrSolution = rhrSolver.solve(maze, start, end);

        Assertions.assertThat(dfsSolution).isEqualTo(bfsSolution).isEqualTo(rhrSolution);
    }

    @Test
    @DisplayName("Проверка трех различных solver'ов при детерминированном лабиринте (единичной ширины)")
    public void test4() {
        final var randomGenerator = new RandomizedDFSGenerator();
        final var maze = randomGenerator.generate(1, 10);

        final var dfsSolver = new SolverDFS();
        final var bfsSolver = new SolverBFS();
        final var rhrSolver = new SolverRightHandRule();

        final var start = new Coordinate(0, 0);
        final var end = new Coordinate(maze.getHeight() - 1, maze.getWidth() - 1);

        final var dfsSolution = dfsSolver.solve(maze, start, end);
        final var bfsSolution = bfsSolver.solve(maze, start, end);
        final var rhrSolution = rhrSolver.solve(maze, start, end);

        Assertions.assertThat(dfsSolution).isEqualTo(bfsSolution).isEqualTo(rhrSolution);
    }

    @Test
    @DisplayName("Проверка dfs solver'а, что он достигает финиша")
    public void test5() {
        final var randomGenerator = new RandomizedDFSGenerator();
        final var maze = randomGenerator.generate(1, 10);

        final var dfsSolver = new SolverDFS();

        final var start = new Coordinate(0, 0);
        final var end = new Coordinate(maze.getHeight() - 1, maze.getWidth() - 1);

        final var dfsSolution = dfsSolver.solve(maze, start, end);

        Assertions.assertThat(dfsSolution.getLast()).isEqualTo(end);
    }

    @Test
    @DisplayName("Проверка bfs solver'а, что он достигает финиша")
    public void test6() {
        final var randomGenerator = new RandomizedDFSGenerator();
        final var maze = randomGenerator.generate(1, 10);

        final var bfsSolver = new SolverBFS();

        final var start = new Coordinate(0, 0);
        final var end = new Coordinate(maze.getHeight() - 1, maze.getWidth() - 1);

        final var bfsSolution = bfsSolver.solve(maze, start, end);

        Assertions.assertThat(bfsSolution.getLast()).isEqualTo(end);
    }

    @Test
    @DisplayName("Проверка right hand rule solver'а, что он достигает финиша")
    public void test7() {
        final var randomGenerator = new RandomizedDFSGenerator();
        final var maze = randomGenerator.generate(10, 10);

        final var rhrSolver = new SolverRightHandRule();

        final var start = new Coordinate(0, 0);
        final var end = new Coordinate(maze.getHeight() - 1, maze.getWidth() - 1);

        final var rhrSolution = rhrSolver.solve(maze, start, end);

        Assertions.assertThat(rhrSolution.getLast()).isEqualTo(end);
    }

    @Test
    @DisplayName("Проверка работы solver'а")
    public void test8() {
        final var randomGenerator = new RandomizedDFSGenerator();
        final var maze = randomGenerator.generate(10, 10);

        final var bfsSolver = new SolverBFS();

        final var start = new Coordinate(0, 0);
        final var end = new Coordinate(maze.getHeight() - 1, maze.getWidth() - 1);

        assertAll(() -> bfsSolver.solve(maze, start, end));
    }

    @Test
    @DisplayName("Проверка работы solver'а")
    public void test9() {
        final var randomGenerator = new RandomizedDFSGenerator();
        final var maze = randomGenerator.generate(10, 10);

        final var dfsSolver = new SolverDFS();

        final var start = new Coordinate(0, 0);
        final var end = new Coordinate(maze.getHeight() - 1, maze.getWidth() - 1);

        assertAll(() -> dfsSolver.solve(maze, start, end));
    }
}
