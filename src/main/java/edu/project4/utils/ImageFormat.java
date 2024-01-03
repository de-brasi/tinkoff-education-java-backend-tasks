package edu.project4.utils;

public enum ImageFormat {
    JPEG("jpeg"),
    BMP("bmp"),
    PNG("png");

    ImageFormat(String formatStr) {
        format = formatStr;
    }

    public String getStringRepresentation() {
        return format;
    }

    private final String format;
}
