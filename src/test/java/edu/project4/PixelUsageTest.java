package edu.project4;

import edu.project4.utils.Color;
import edu.project4.utils.ColorShortcuts;
import edu.project4.utils.Pixel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class PixelUsageTest {
    @Test
    @DisplayName("Test increasing hit count after hit")
    public void test1() {
        Pixel testedPixel = new Pixel(ColorShortcuts.WHITE);
        assertThat(testedPixel.getHitCount()).isEqualTo(0);

        testedPixel.hit();
        assertThat(testedPixel.getHitCount()).isEqualTo(1);
    }

    @Test
    @DisplayName("Test color is not changed implicitly")
    public void test2() {
        Pixel testedPixel = new Pixel(ColorShortcuts.WHITE);
        assertThat(testedPixel.getColor()).isEqualTo(ColorShortcuts.WHITE);

        testedPixel.hit();
        assertThat(testedPixel.getColor()).isEqualTo(ColorShortcuts.WHITE);
    }

    @Test
    @DisplayName("Test setting color")
    public void test3() {
        Color colorBefore = ColorShortcuts.WHITE;
        Color colorAfter = ColorShortcuts.BLACK;
        Pixel testedPixel = new Pixel(colorBefore);
        assertThat(testedPixel.getColor()).isEqualTo(colorBefore);

        testedPixel.setColor(colorAfter);
        assertThat(testedPixel.getColor().getRed()).isEqualTo(colorAfter.getRed());
        assertThat(testedPixel.getColor().getGreen()).isEqualTo(colorAfter.getGreen());
        assertThat(testedPixel.getColor().getBlue()).isEqualTo(colorAfter.getBlue());
    }
}
