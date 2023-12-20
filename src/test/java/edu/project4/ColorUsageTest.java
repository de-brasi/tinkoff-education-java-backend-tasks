package edu.project4;

import edu.project4.utils.Color;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ColorUsageTest {
    @Test
    @DisplayName("Test color mixing")
    public void test() {
        Color tested = Color.of(1, 1, 1);
        Color colorMixin = Color.of(3, 3, 3);
        Color colorExpectedResult = Color.of(2, 2, 2);

        tested.mixColorWith(colorMixin);

        assertThat(tested.getRed()).isEqualTo(colorExpectedResult.getRed());
        assertThat(tested.getBlue()).isEqualTo(colorExpectedResult.getBlue());
        assertThat(tested.getGreen()).isEqualTo(colorExpectedResult.getGreen());
    }
}
