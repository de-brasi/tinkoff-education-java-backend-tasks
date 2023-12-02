package edu.project4.utils;

public class Pixel {
    public Pixel(Color color) {
        this.color = color;
        hitCount = 0;
    }

    public int getHitCount() {
        return hitCount;
    }

    public Pixel hit() {
        ++hitCount;
        return this;
    }

    public void setColor(Color toSet) {
        this.color = Color.of(toSet);
    }

    public Color getColor() {
        return color;
    }

    private Color color;
    private int hitCount;
}
