package edu.project2.util;

public record Coordinate(int row, int col) {
    public Coordinate coordinateFromDirection(Direction direction) {
        return switch (direction) {
            case UP -> new Coordinate(this.row - 1, this.col);
            case DOWN -> new Coordinate(this.row + 1, this.col);
            case LEFT -> new Coordinate(this.row, this.col - 1);
            case RIGHT -> new Coordinate(this.row, this.col + 1);
        };
    }
}
