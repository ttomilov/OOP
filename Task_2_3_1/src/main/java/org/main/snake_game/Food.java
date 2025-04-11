package org.main.snake_game;

import java.util.Random;

public class Food {
    private FoodType type;
    private int x, y;

    public Food(FoodType type, int width, int height, Random random) {
        regenerate(type, width, height, random);
    }

    public void regenerate(FoodType type, int width, int height, Random random) {
        this.type = type;
        this.x = random.nextInt(width);
        this.y = random.nextInt(height);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public FoodType getType() {
        return type;
    }
}
