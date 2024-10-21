package org.app;
import java.util.Map;

public class Number extends Expression{
    private final int value;

    public Number(int value){
        this.value = value;
    }

    @Override
    void print(){
        System.out.print(value);
    }

    @Override
    Expression derivative(String var) {
        return new Number(0);
    }

    @Override
    int eval(Map<String, Integer> variables) {
        return value;
    }
}
