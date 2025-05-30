package org.main.snake_game;

import java.awt.Color;
import java.util.function.IntUnaryOperator;

public class FoodType {
    private final String name;
    private final Color color;
    private final IntUnaryOperator bonusFunction;

    public FoodType(String name, String rawExpression, String colorHex) {
        this.name = name;
        this.color = Color.decode(colorHex);
        this.bonusFunction = compileExpression(rawExpression);
    }

    public int applyBonus(int length) {
        return bonusFunction.applyAsInt(length);
    }

    public String getName() {
        return name;
    }

    public Color getColor() {
        return color;
    }

    private IntUnaryOperator compileExpression(String expr) {
        expr = expr.replaceAll("\\s+", "");

        if (expr.matches("len\\+\\d+")) {
            int n = Integer.parseInt(expr.substring(4));
            return len -> len + n;
        } else if (expr.matches("len-\\d+")) {
            int n = Integer.parseInt(expr.substring(4));
            return len -> len - n;
        } else if (expr.matches("len\\*\\d+")) {
            int n = Integer.parseInt(expr.substring(4));
            return len -> len * n;
        } else if (expr.matches("len/\\d+")) {
            int n = Integer.parseInt(expr.substring(4));
            return len -> len / n;
        } else if (expr.matches("\\d+")) {
            int n = Integer.parseInt(expr);
            return len -> n;
        } else if (expr.equals("len")) {
            return len -> len;
        }

        System.err.println("Unknown bonus expression: " + expr);
        return len -> len;
    }
}
