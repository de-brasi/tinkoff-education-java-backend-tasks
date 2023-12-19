package edu.hw2.task2;

public class Square extends Rectangle {
    public Square() {
        super(0, 0);
    }

    public Square(int size) {
        super(size, size);
    }

    public Square setSize(int size) {
        return new Square(size);
    }
}
