package org.expressions;

import java.util.Map;
import org.app.Expression;

/**
 * Represents the addition of two expressions.
 */
public class Add extends Expression {
    private final Expression left; 
    private final Expression right;

    /**
     * Constructs an Add expression with two sub-expressions.
     *
     * @param left  the left operand expression.
     * @param right the right operand expression.
     */
    public Add(Expression left, Expression right) {
        this.left = left;
        this.right = right;
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
        left.buildString(sb);
        sb.append("+");
        right.buildString(sb);
        sb.append(")");
    }

    @Override
    public Expression derivative(String var) {
        return new Add(left.derivative(var), right.derivative(var));
    }

    @Override
    public int eval(Map<String, Integer> variables) {
        return left.eval(variables) + right.eval(variables);
    }
}
