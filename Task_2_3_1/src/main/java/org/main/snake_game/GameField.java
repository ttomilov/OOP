package org.main.snake_game;

import java.awt.Point;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class GameField {
    private int width, height;
    private List<Food> foodList;
    private Random random;
    private List<FoodType> typeList;

    GameField(int width, int height) throws IOException {
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("Field dimensions must be positive");
        }
        this.width = width;
        this.height = height;
        this.foodList = new ArrayList<>();
        this.random = new Random();
        this.typeList = new ArrayList<>();
        loadFoodTypes();
        for (int i = 0; i < 3; i++) {
            generateFood();
        }
    }

    private void loadFoodTypes() throws IOException {
        File file = new File("src/main/resources/FoodNames.txt");
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(" ");
                if (tokens.length == 3) {
                    typeList.add(new FoodType(tokens[0], tokens[1], tokens[2]));
                }
            }
        }
    }

    void generateFood() {
        Food newFood;
        boolean positionOccupied;
        FoodType foodType = typeList.get(random.nextInt(typeList.size()));
        do {
            newFood = new Food(foodType, width, height, random);
            positionOccupied = false;
            for (Food existingFood : foodList) {
                if (existingFood.getX() == newFood.getX() && existingFood.getY() == newFood.getY()) {
                    positionOccupied = true;
                    break;
                }
            }
        } while (positionOccupied);
        foodList.add(newFood);
    }

   Food getFoodAt(Point p) {
        for (int i = 0; i < foodList.size(); i++) {
            Food f = foodList.get(i);
            if (f.getX() == p.x && f.getY() == p.y) {
                return foodList.remove(i);
            }
        }
        return null;
   }

   List<Food> getFoodPositions() {
        return new ArrayList<>(foodList);
    }
}
