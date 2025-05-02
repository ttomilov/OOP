package org.main.snake_game;

import org.junit.jupiter.api.Test;
import java.awt.Color;
import static org.junit.jupiter.api.Assertions.*;

class FoodTypeTest {

    @Test
    void testApplyBonusMultiply() {
        FoodType ft = new FoodType("Apple", "len*2", "#FF0000");
        assertEquals(10, ft.applyBonus(5));
    }

    @Test
    void testApplyBonusDivide() {
        FoodType ft = new FoodType("Rotten_egg", "len/2", "#808080");
        assertEquals(2, ft.applyBonus(5));
    }

    @Test
    void testApplyBonusInvalid() {
        FoodType ft = new FoodType("Weird", "wrong_syntax", "#000000");
        assertEquals(5, ft.applyBonus(5));
    }

    @Test
    void testColorParsing() {
        FoodType ft = new FoodType("Banana", "len*3", "#FFFF00");
        Color c = ft.getColor();
        assertEquals(255, c.getRed());
        assertEquals(255, c.getGreen());
        assertEquals(0, c.getBlue());
    }
}
