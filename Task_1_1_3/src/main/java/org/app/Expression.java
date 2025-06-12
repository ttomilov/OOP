package org.app;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents an abstract mathematical expression that can be printed,
 * evaluated with variable assignments, and differentiated with respect to a variable.
 */
public abstract class Expression {
    /**
     * Prints the expression to the console.
     */
    public abstract void print();

    /**
     * Calculates the derivative of the expression with respect to a given variable.
     *
     * @param var the variable with respect to which the derivative is calculated.
     * @return the derivative of the expression as a new Expression object.
     */
    public abstract Expression derivative(String var);

    /**
     * Evaluates the expression given a map of variable assignments.
     *
     * @param variables a map containing variable names and their assigned integer values.
     * @return the integer result of the evaluation.
     */
    public abstract int eval(Map<String, Integer> variables);

    /**
     * Builds the string representation of the expression.
     *
     * @param sb a StringBuilder object used to build the expression string.
     */
    public abstract void buildString(StringBuilder sb);

    /**
     * Evaluates the expression given a string of variable assignments in the format "x=2;y=3".
     *
     * @param assignments a semicolon-separated string of variable assignments.
     * @return the integer result of the evaluation.
     */
    public int eval(String assignments) {
        Map<String, Integer> variables = parseAssignments(assignments);
        return eval(variables);
    }

    /**
     * Parses a string of variable assignments into a map.
     *
     * @param assignments a semicolon-separated string of variable assignments.
     * @return a map containing variable names and their assigned integer values.
     */
    Map<String, Integer> parseAssignments(String assignments) {
        Map<String, Integer> variables = new HashMap<>();
        String[] pairs = assignments.split(";");
        for (String pair : pairs) {
            String[] parts = pair.split("=");
            String varName = parts[0].trim();
            int value = Integer.parseInt(parts[1].trim());
            variables.put(varName, value);
        }
        return variables;
    }
}
