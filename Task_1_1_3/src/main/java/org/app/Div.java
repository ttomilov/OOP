package org.app;

import java.util.Map;

class Div extends Expression {
    private final Expression numerator, denominator;

    public Div(Expression numerator, Expression denominator) {
        this.numerator = numerator;
        this.denominator = denominator;
    }

    @Override
    void print() {
        System.out.print("(");
        numerator.print();
        System.out.print("/");
        denominator.print();
        System.out.print(")");
    }

    @Override
    Expression derivative(String var) {
        return new Div(
                new Sub(new Mul(numerator.derivative(var), denominator),new Mul(numerator, denominator.derivative(var))),new Mul(denominator, denominator)
        );
    }

    @Override
    int eval(Map<String, Integer> variables) {
        return numerator.eval(variables) / denominator.eval(variables);
    }
}