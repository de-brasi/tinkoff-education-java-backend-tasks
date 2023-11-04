package edu.project2.util;

public enum Direction {
    UP(0),
    DOWN(1),
    LEFT(2),
    RIGHT(3);

    Direction(int directionIndex) {
        this.index = directionIndex;
    }

    private final int index;

    public int getIndex() {
        return this.index;
    }

    public Direction getOppositeDirection() {
        return switch (this) {
            case RIGHT -> LEFT;
            case LEFT -> RIGHT;
            case UP -> DOWN;
            case DOWN -> UP;
        };
    }
}
