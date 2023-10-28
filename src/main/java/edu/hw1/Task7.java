package edu.hw1;

public class Task7 {
    public static int rotateLeft(int n, int shift) {
        var bitsAsStr = Integer.toBinaryString(n).split("");
        shift %= bitsAsStr.length;

        String firstElement;

        for (int i = 0; i < shift; i++) {
            firstElement = bitsAsStr[0];
            for (int j = 0; j < bitsAsStr.length - 1; j++) {
                bitsAsStr[j] = bitsAsStr[j + 1];
            }

            bitsAsStr[bitsAsStr.length - 1] = firstElement;
        }
        String result = String.join("", bitsAsStr);
        return Integer.parseInt(result, 2);
    }

    public static int rotateRight(int n, int shift) {
        var bitsAsStr = Integer.toBinaryString(n).split("");
        shift %= bitsAsStr.length;

        String tmp;
        String toShift;
        for (int i = 0; i < shift; i++) {
            toShift = bitsAsStr[0];

            for (int j = 1; j < bitsAsStr.length - 1; j++) {
                tmp = bitsAsStr[j];
                bitsAsStr[j] = toShift;
                toShift = tmp;
            }

            bitsAsStr[0] = toShift;
        }

        String result = String.join("", bitsAsStr);
        return Integer.parseInt(result, 2);
    }
}
