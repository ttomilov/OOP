package org.app;

import java.util.Map;

class Mul extends Expression {
    private final Expression first, second;

    public Mul(Expression left, Expression right) {
        this.first = left;
        this.second = right;
    }

    @Override
    void print() {
        System.out.print("(");
        first.print();
        System.out.print("*");
        second.print();
        System.out.print(")");
    }

    @Override
    Expression derivative(String var) {
        return new Add(new Mul(first.derivative(var), second), new Mul(first, second.derivative(var)));
    }

    @Override
    int eval(Map<String, Integer> variables) {
        return first.eval(variables) * second.eval(variables);
    }
}
