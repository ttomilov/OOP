package org.app;

import java.util.HashMap;
import java.util.Map;

abstract class Expression {
    abstract void print();

    abstract Expression derivative(String var);

    abstract int eval(Map<String, Integer> variables);

    int eval(String assignments) {
        Map<String, Integer> variables = parseAssignments(assignments);
        return eval(variables);
    }
    
    private Map<String, Integer> parseAssignments(String assignments) {
        Map<String, Integer> variables = new HashMap<>();
        String[] pairs = assignments.split(";");
        for (int i = 0; i < pairs.length; i++) {
            String[] parts = pairs[i].split("=");
            String varName = parts[0].trim();
            int value = Integer.parseInt(parts[1].trim());
            variables.put(varName, value);
        }
        return variables;
    }
}
