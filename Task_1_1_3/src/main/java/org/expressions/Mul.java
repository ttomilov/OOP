package org.expressions;

import java.util.Map;
import org.app.Expression;

/**
 * Represents the multiplication of two expressions.
 */
public class Mul extends Expression {
    private final Expression first, second;

    /**
     * Constructs a Mul expression with two sub-expressions.
     *
     * @param left  the left operand expression.
     * @param right the right operand expression.
     */
    public Mul(Expression left, Expression right) {
        this.first = left;
        this.second = right;
    }

    @Override
    public void print() {
        StringBuilder sb = new StringBuilder();
        buildString(sb);
        System.out.print(sb.toString());
    }

    @Override
    public void buildString(StringBuilder sb) {
        sb.append("(");
        first.buildString(sb);
        sb.append("*");
        second.buildString(sb);
        sb.append(")");
    }

    @Override
    public Expression derivative(String var) {
        return new Add(new Mul(first.derivative(var), second), new Mul(first, second.derivative(var)));
    }

    @Override
    public int eval(Map<String, Integer> variables) {
        return first.eval(variables) * second.eval(variables);
    }
}
