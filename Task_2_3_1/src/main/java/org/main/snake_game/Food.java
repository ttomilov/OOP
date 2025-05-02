package org.main.snake_game;

public class Food {
    private FoodType type;
    private int x, y;

    public Food(FoodType type, int x, int y) {
        this.type = type;
        this.x = x;
        this.y = y;
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
