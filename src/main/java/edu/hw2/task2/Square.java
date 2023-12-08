package edu.hw2.task2;

public class Square extends Rectangle {
    public Square(int size) {
        super(size, size);
    }

    @Override
    public Rectangle setWidth(int width) {
        return new Square(width);
    }

    @Override
    public Rectangle setHeight(int height) {
        return new Square(height);
    }
}
