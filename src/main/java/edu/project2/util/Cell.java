package edu.project2.util;

public class Cell {
    public Cell() {
        wallInformation = new boolean[4];
        for (int i = 0; i < wallInformation.length; i++) {
            wallInformation[i] = true;
        }
    }

    public void visit() {
        isVisited = true;
    }

    public boolean checkIsVisited() {
        return isVisited;
    }

    public boolean checkWall(Direction direction) {
        assert direction.getIndex() < wallInformation.length;
        return wallInformation[direction.getIndex()];
    }

    public void destroyWall(Direction direction) {
        assert direction.getIndex() < wallInformation.length;
        wallInformation[direction.getIndex()] = false;
    }

    public void forget() {
        isVisited = false;
    }

    public void markAsNotVisited() {
        this.forget();
    }

    private final boolean[] wallInformation;
    private boolean isVisited = false;
}
