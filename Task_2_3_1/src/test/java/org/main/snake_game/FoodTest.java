package org.main.snake_game;

import org.junit.jupiter.api.Test;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.*;

class FoodTest {

    @Test
    void testFoodWithinBounds() {
        int width = 20, height = 20;
        Random random = new Random();
        FoodType type = new FoodType("Apple", "len*2", "#FF0000");

        Food food = new Food(type, width, height, random);

        assertTrue(food.getX() >= 0 && food.getX() < width);
        assertTrue(food.getY() >= 0 && food.getY() < height);
    }

    @Test
    void testFoodTypeStored() {
        FoodType type = new FoodType("Banana", "len*3", "#FFFF00");
        Food food = new Food(type, 10, 10, new Random());
        assertEquals("Banana", food.getType().getName());
    }
}
