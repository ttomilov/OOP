package org.app;

import org.expressions.Add;
import org.expressions.Mul;
import org.expressions.Number;
import org.expressions.Variable;

/**
 * Main class to demonstrate the usage of the Expression framework.
 * Constructs, differentiates, and evaluates a mathematical expression.
 */
public class Main {
    /**
     * The entry point of the application, where an expression is created,
     * its derivative is computed, and its value is evaluated with a given assignment.
     *
     * @param args the command-line arguments (not used)
     */
    public static void main(String[] args) {
        // Create an expression 3 + 2 * x
        Expression e = new Add(new Number(3), new Mul(new Number(2), new Variable("x")));
        
        // Print the expression
        System.out.print("Expression: ");
        e.print();
        System.out.println();

        // Compute the derivative of the expression with respect to 'x'
        Expression de = e.derivative("x");
        System.out.print("Derivative with respect to x: ");
        de.print();
        System.out.println();

        // Evaluate the expression with x = 10
        int result = e.eval("x = 10");
        System.out.println("Result: " + result);
    }
}
