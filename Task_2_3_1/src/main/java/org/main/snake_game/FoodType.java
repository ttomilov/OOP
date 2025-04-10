package org.main.snake_game;

import java.awt.*;

public class FoodType {
    private final String name;
    private final String formula;
    private final Color color;

    public FoodType(String name, String formula, String colorCode) {
        this.name = name;
        this.formula = formula;
        this.color = Color.decode(colorCode);
    }

    public String getName() {
        return name;
    }

    public Color getColor() {
        return color;
    }

    public int applyBonus(int length) {
        if (formula.contains("*")) {
            String[] parts = formula.split("\\*");
            return length * Integer.parseInt(parts[1]);
        } else if (formula.contains("/")) {
            String[] parts = formula.split("/");
            return Math.max(1, length / Integer.parseInt(parts[1]));
        } else if (formula.contains("+")) {
            String[] parts = formula.split("\\+");
            return length + Integer.parseInt(parts[1]);
        } else if (formula.contains("-")) {
            String[] parts = formula.split("-");
            return length - Integer.parseInt(parts[1]);
        } else {
            return length;
        }
    }
}
