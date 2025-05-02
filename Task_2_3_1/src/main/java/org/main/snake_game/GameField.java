package org.main.snake_game;

import java.awt.Point;
import java.io.*;
import java.util.*;

class GameField {
    private int width, height;
    private List<Food> foodList;
    private Random random;
    private List<FoodType> typeList;
    private static String filePath = "src/main/resources/FoodNames.txt";

    private final Set<Point> occupiedCells = new HashSet<>();
    private final List<Point> freeCells = new ArrayList<>();

    GameField(int width, int height) throws IOException {
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("Field dimensions must be positive");
        }
        this.width = width;
        this.height = height;
        this.foodList = new ArrayList<>();
        this.random = new Random();
        this.typeList = new ArrayList<>();
        initializeFreeCells();
        loadFoodTypes();
        for (int i = 0; i < 3; i++) {
            generateFood();
        }
    }

    private void initializeFreeCells() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                freeCells.add(new Point(x, y));
            }
        }
    }

    private void loadFoodTypes() throws IOException {
        File file = new File(filePath);
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

    void loadFoodTypesFromFile(String path) throws IOException {
        filePath = path;
        this.typeList.clear();
        loadFoodTypes();

        foodList.clear();
        for (int i = 0; i < 3; i++) {
            generateFood();
        }
    }

    public static void setGlobalFoodFilePath(String path) {
        filePath = path;
    }

    public void generateFood() {
        if (freeCells.isEmpty() || typeList.isEmpty()) return;

        Point selected = freeCells.get(random.nextInt(freeCells.size()));
        FoodType foodType = typeList.get(random.nextInt(typeList.size()));
        Food newFood = new Food(foodType, selected.x, selected.y);

        foodList.add(newFood);
        occupyCell(selected);
    }

    Food getFoodAt(Point p) {
        Iterator<Food> iterator = foodList.iterator();
        while (iterator.hasNext()) {
            Food f = iterator.next();
            if (f.getX() == p.x && f.getY() == p.y) {
                iterator.remove();
                return f;
            }
        }
        return null;
    }

    List<Food> getFoodPositions() {
        return new ArrayList<>(foodList);
    }

    void occupyCell(Point p) {
        if (freeCells.remove(p)) {
            occupiedCells.add(p);
        }
    }

    void releaseCell(Point p) {
        if (occupiedCells.remove(p)) {
            freeCells.add(p);
        }
    }

}
