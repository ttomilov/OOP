package org.main.snake_game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.Point;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameFieldTest {
    private static final String FOOD_FILE = "src/main/resources/FoodNames.txt";

    @BeforeEach
    void setupFile() throws IOException {
        try (FileWriter writer = new FileWriter(FOOD_FILE)) {
            writer.write("Apple len+1 #FF0000\n");
            writer.write("Banana len*2 #FFFF00\n");
        }
    }

    @Test
    void testFoodGeneration() throws IOException {
        GameField field = new GameField(10, 10);
        List<Food> food = field.getFoodPositions();
        assertEquals(3, food.size());
    }

    @Test
    void testGetFoodAtRemovesIt() throws IOException {
        GameField field = new GameField(10, 10);
        Food f = field.getFoodPositions().get(0);
        Point p = new Point(f.getX(), f.getY());

        Food collected = field.getFoodAt(p);
        assertNotNull(collected);
        assertEquals(2, field.getFoodPositions().size());
    }
}
