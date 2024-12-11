package org.expressions;

import java.util.Map;
import org.app.Expression;

/**
 * Represents the division of two expressions.
 */
public class Div extends Expression {
    private final Expression numerator;
    private final Expression denominator;

    /**
     * Constructs a Div expression with a numerator and denominator.
     *
     * @param numerator   the numerator expression.
     * @param denominator the denominator expression.
     */
    public Div(Expression numerator, Expression denominator) {
        this.numerator = numerator;
        this.denominator = denominator;
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
        numerator.buildString(sb);
        sb.append("/");
        denominator.buildString(sb);
        sb.append(")");
    }

    @Override
    public Expression derivative(String var) {
        return new Div(
                new Sub(new Mul(numerator.derivative(var), denominator), new Mul(numerator, denominator.derivative(var))),
                new Mul(denominator, denominator)
        );
    }

    @Override
    public int eval(Map<String, Integer> variables) {
        return numerator.eval(variables) / denominator.eval(variables);
    }
}
