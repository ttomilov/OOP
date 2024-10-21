package org.app;

public class Main {
    public static void main(String[] args) {
        Expression e = new Add(new Number(3), new Mul(new Number(2), new Variable("x")));
        System.out.print("Выражение: ");
        e.print();
        System.out.println();

        Expression de = e.derivative("x");
        System.out.print("Производная по x: ");
        de.print();
        System.out.println();

        int result = e.eval("x = 10");
        System.out.println("Результат: " + result); // 23
    }
}
