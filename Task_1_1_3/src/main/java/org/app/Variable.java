package org.app;

import java.util.Map;

class Variable extends Expression {
    private final String name;

    public Variable(String name) {
        this.name = name;
    }

    @Override
    void print() {
        System.out.print(name);
    }

    @Override
    Expression derivative(String var) {
        return new Number(name.equals(var) ? 1 : 0);
    }

    @Override
    int eval(Map<String, Integer> variables) {
        return variables.getOrDefault(name, 0);
    }
}
