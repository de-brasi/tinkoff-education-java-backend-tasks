package edu.project4.utils;

public class Color {
    public static Color of(int r, int g, int b) {
        return new Color(r, g, b);
    }

    public static Color of(Color another) {
        return new Color(another.r(), another.g(), another.b());
    }

    public Color() {
        red = 0;
        green = 0;
        blue = 0;
    }

    public Color(int r, int g, int b) {
        red = r;
        green = g;
        blue = b;
    }

    public int getRed() {
        return red;
    }

    public int getGreen() {
        return green;
    }

    public int getBlue() {
        return blue;
    }

    public int r() {
        return getRed();
    }

    public int g() {
        return getGreen();
    }

    public int b() {
        return getBlue();
    }

    public void setRed(int red) {
        this.red = red;
    }

    public void setGreen(int green) {
        this.green = green;
    }

    public void setBlue(int blue) {
        this.blue = blue;
    }

    @SuppressWarnings("MagicNumber")
    public void mixColorWith(Color another) {
        red = ((red + another.red) / 2) % 256;
        green = ((green + another.green) / 2) % 256;
        blue = ((blue + another.blue) / 2) % 256;
    }

    private int red;
    private int green;
    private int blue;
}
