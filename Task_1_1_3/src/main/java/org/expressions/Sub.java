package org.expressions;

import java.util.Map;
import org.app.Expression;

/**
 * Represents the subtraction of two expressions.
 */
public class Sub extends Expression {
    private final Expression first;
    private final Expression second;

    /**
     * Constructs a Sub expression with two sub-expressions.
     *
     * @param left  the left operand expression.
     * @param right the right operand expression.
     */
    public Sub(Expression left, Expression right) {
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
        sb.append("-");
        second.buildString(sb);
        sb.append(")");
    }

    @Override
    public Expression derivative(String var) {
        return new Sub(first.derivative(var), second.derivative(var));
    }

    @Override
    public int eval(Map<String, Integer> variables) {
        return first.eval(variables) - second.eval(variables);
    }
}
