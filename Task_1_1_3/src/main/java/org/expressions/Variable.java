package org.expressions;

import java.util.Map;
import org.app.Expression;

/**
 * Represents a variable in a mathematical expression.
 */
public class Variable extends Expression {
    private final String name;

    /**
     * Constructs a Variable expression with a specific name.
     *
     * @param name the name of the variable.
     */
    public Variable(String name) {
        this.name = name;
    }

    @Override
    public void print() {
        System.out.print(name);
    }

    @Override
    public Expression derivative(String var) {
        return new Number(name.equals(var) ? 1 : 0); // Derivative is 1 if matching variable, otherwise 0.
    }

    @Override
    public int eval(Map<String, Integer> variables) {
        return variables.getOrDefault(name, 0);
    }

    @Override
    public void buildString(StringBuilder sb) {
        sb.append(name);
    }
}
