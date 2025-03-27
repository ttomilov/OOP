package org.main.task_2_3_1;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameField {
    private int width, height;
    private List<Food> foodList;
    private Random random;

    public GameField(int width, int height) {
        this.width = width;
        this.height = height;
        this.foodList = new ArrayList<>();
        this.random = new Random();
        generateFood();
    }

    public void generateFood() {
        foodList.clear();
        for (int i = 0; i < 3; i++) {
            foodList.add(new Food(width, height, random));
        }
    }

    public boolean isFood(Point p) {
        return foodList.removeIf(f -> f.getX() == p.x && f.getY() == p.y);
    }

    public List<Food> getFoodPositions() {
        return foodList;
    }
}
