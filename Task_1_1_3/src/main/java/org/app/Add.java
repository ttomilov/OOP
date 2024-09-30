package org.app;

import java.util.Map;

class Add extends Expression {
    private final Expression left, right;

    public Add(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    void print() {
        System.out.print("(");
        left.print();
        System.out.print("+");
        right.print();
        System.out.print(")");
    }

    @Override
    Expression derivative(String var) {
        return new Add(left.derivative(var), right.derivative(var));
    }

    @Override
    int eval(Map<String, Integer> variables) {
        return left.eval(variables) + right.eval(variables);
    }
}
