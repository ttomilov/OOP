package org.expressions;

import java.util.Map;
import org.app.Expression;

/**
 * Represents a constant numeric expression.
 */
public class Number extends Expression {
    private final int value;

    /**
     * Constructs a Number expression with a specific value.
     *
     * @param value the integer value of the number.
     */
    public Number(int value) {
        this.value = value;
    }

    @Override
    public void print() {
        System.out.print(value);
    }

    @Override
    public Expression derivative(String var) {
        return new Number(0); // Derivative of a constant is 0.
    }

    @Override
    public int eval(Map<String, Integer> variables) {
        return value;
    }

    @Override
    public void buildString(StringBuilder sb) {
        sb.append(value);
    }
}
