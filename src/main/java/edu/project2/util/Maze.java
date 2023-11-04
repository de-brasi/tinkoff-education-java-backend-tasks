package edu.project2.util;

import java.util.Arrays;
import java.util.List;

public final class Maze {
    public Maze(int width, int height) {
        this.height = height;
        this.width = width;

        grid = new Cell[height][width];

        // init as completely filled with walls
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                grid[i][j] = new Cell();
            }
        }
    }

    public void setRow(int rowIdx, List<Cell> values) {
        assert rowIdx < this.height;
        assert values.size() == this.width;

        for (int colIdx = 0; colIdx < this.width; colIdx++) {
            setCell(rowIdx, colIdx, values.get(colIdx));
        }
    }

    public void setCell(Coordinate coordinate, Cell cell) {
        grid[coordinate.row()][coordinate.col()] = cell;
    }

    public void setCell(int rowIdx, int colIdx, Cell cell) {
        grid[rowIdx][colIdx] = cell;
    }

    public Cell[] getRowShallowCopy(int rowIdx) {
        return Arrays.copyOf(this.grid[rowIdx], this.grid[rowIdx].length);
    }

    public Cell getCell(Coordinate coordinate) {
        return this.grid[coordinate.row()][coordinate.col()];
    }

    public Cell getCell(int rowIdx, int colIdx) {
        return this.grid[rowIdx][colIdx];
    }

    public Cell getCell(Coordinate coordinate, Direction direction) {
        return switch (direction) {
            case UP -> getCell(coordinate.row() - 1, coordinate.col());
            case DOWN -> getCell(coordinate.row() + 1, coordinate.col());
            case LEFT -> getCell(coordinate.row(), coordinate.col() - 1);
            case RIGHT -> getCell(coordinate.row(), coordinate.col() + 1);
        };
    }

    public Coordinate getCoordinate(Coordinate coordinate, Direction direction) {
        return switch (direction) {
            case UP -> new Coordinate(coordinate.row() - 1, coordinate.col());
            case DOWN -> new Coordinate(coordinate.row() + 1, coordinate.col());
            case LEFT -> new Coordinate(coordinate.row(), coordinate.col() - 1);
            case RIGHT -> new Coordinate(coordinate.row(), coordinate.col() + 1);
        };
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Direction[] getNotVisitedNeighboursDirection(Coordinate target) {
        assert 0 <= target.row() && target.row() < height;
        assert 0 <= target.col() && target.col() < width;

        final var buffer = new Direction[4];
        int indexToRecord = 0;

        if (target.row() > 0 && !grid[target.row() - 1][target.col()].checkIsVisited()) {
            buffer[indexToRecord++] = Direction.UP;
        }

        if (target.row() + 1 < height && !grid[target.row() + 1][target.col()].checkIsVisited()) {
            buffer[indexToRecord++] = Direction.DOWN;
        }

        if (target.col() > 0 && !grid[target.row()][target.col() - 1].checkIsVisited()) {
            buffer[indexToRecord++] = Direction.LEFT;
        }

        if (target.col() + 1 < width && !grid[target.row()][target.col() + 1].checkIsVisited()) {
            buffer[indexToRecord++] = Direction.RIGHT;
        }

        return Arrays.copyOf(buffer, indexToRecord);
    }

    private final int height;

    private final int width;

    private final Cell[][] grid;
}
