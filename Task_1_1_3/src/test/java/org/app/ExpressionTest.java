package org.app;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.expressions.Add;
import org.expressions.Div;
import org.expressions.Mul;
import org.expressions.Number;
import org.expressions.Sub;
import org.expressions.Variable;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

class ExpressionTest {

    @Test
    void testAddPrint() {
        Expression expr = new Add(new Number(3), new Number(5));
        StringBuilder sb = new StringBuilder();
        expr.buildString(sb);
        assertEquals("(3+5)", sb.toString());
    }

    @Test
    void testAddBuildString() {
        Expression expr = new Add(new Number(3), new Number(5));
        StringBuilder sb = new StringBuilder();
        expr.buildString(sb);
        assertEquals("(3+5)", sb.toString());
    }

    @Test
    void testAddDerivative() {
        Expression expr = new Add(new Variable("x"), new Number(5));
        Expression derivative = expr.derivative("x");
        
        StringBuilder sb = new StringBuilder();
        derivative.buildString(sb);
        
        assertEquals("(1+0)", sb.toString());
    }

    @Test
    void testAddEval() {
        Expression expr = new Add(new Number(3), new Number(7));
        Map<String, Integer> variables = new HashMap<>();
        assertEquals(10, expr.eval(variables));
    }

    @Test
    void testAddEvalWithVariables() {
        Expression expr = new Add(new Variable("x"), new Number(5));
        Map<String, Integer> variables = new HashMap<>();
        variables.put("x", 10);
        assertEquals(15, expr.eval(variables));
    }

    @Test
    void testDivPrint() {
        Expression expr = new Div(new Number(10), new Number(2));
        StringBuilder sb = new StringBuilder();
        expr.buildString(sb);
        assertEquals("(10/2)", sb.toString());
    }

    @Test
    void testDivBuildString() {
        Expression expr = new Div(new Number(10), new Number(5));
        StringBuilder sb = new StringBuilder();
        expr.buildString(sb);
        assertEquals("(10/5)", sb.toString());
    }

    @Test
    void testDivDerivative() {
        Expression expr = new Div(new Variable("x"), new Variable("y"));
        Expression derivative = expr.derivative("x");
        
        StringBuilder sb = new StringBuilder();
        derivative.buildString(sb);
        
        assertEquals("(((1*y)-(x*0))/(y*y))", sb.toString());
    }

    @Test
    void testDivEval() {
        Expression expr = new Div(new Number(10), new Number(2));
        Map<String, Integer> variables = new HashMap<>();
        assertEquals(5, expr.eval(variables));
    }

    @Test
    void testDivEvalWithVariables() {
        Expression expr = new Div(new Variable("x"), new Number(2));
        Map<String, Integer> variables = new HashMap<>();
        variables.put("x", 10);
        assertEquals(5, expr.eval(variables));
    }

    @Test
    void testDivByZero() {
        Expression expr = new Div(new Number(10), new Number(0));
        Map<String, Integer> variables = new HashMap<>();
        assertThrows(ArithmeticException.class, () -> expr.eval(variables));
    }

    @Test
    void testMulPrint() {
        Expression expr = new Mul(new Number(3), new Number(5));
        StringBuilder sb = new StringBuilder();
        expr.buildString(sb);
        assertEquals("(3*5)", sb.toString());
    }

    @Test
    void testMulBuildString() {
        Expression expr = new Mul(new Number(3), new Variable("x"));
        StringBuilder sb = new StringBuilder();
        expr.buildString(sb);
        assertEquals("(3*x)", sb.toString());
    }

    @Test
    void testMulDerivative() {
        Expression expr = new Mul(new Variable("x"), new Variable("y"));
        Expression derivative = expr.derivative("x");

        StringBuilder sb = new StringBuilder();
        derivative.buildString(sb);

        assertEquals("((1*y)+(x*0))", sb.toString());
    }

    @Test
void testMulEval() {
    Expression expr = new Mul(new Number(3), new Number(5));
    Map<String, Integer> variables = new HashMap<>();
    // Ожидаем, что результат будет 3 * 5 = 15
    assertEquals(15, expr.eval(variables));
}

    @Test
    void testMulEvalWithVariables() {
        Expression expr = new Mul(new Number(3), new Variable("x"));
        Map<String, Integer> variables = new HashMap<>();
        variables.put("x", 10);
        assertEquals(30, expr.eval(variables));
    }

    @Test
    void testMulWithZero() {
        Expression expr = new Mul(new Number(0), new Variable("x"));
        Map<String, Integer> variables = new HashMap<>();
        variables.put("x", 10);
        assertEquals(0, expr.eval(variables));
    }

    @Test
    void testSubPrint() {
        Expression expr = new Sub(new Number(10), new Number(5));
        StringBuilder sb = new StringBuilder();
        expr.buildString(sb);
        assertEquals("(10-5)", sb.toString());
    }

    @Test
    void testSubDerivative() {
        Expression expr = new Sub(new Variable("x"), new Number(5));
        Expression derivative = expr.derivative("x");

        StringBuilder sb = new StringBuilder();
        derivative.buildString(sb);
        
        assertEquals("(1-0)", sb.toString());
    }

    @Test
    void testSubEval() {
        Expression expr = new Sub(new Number(10), new Number(5));
        Map<String, Integer> variables = new HashMap<>();
        assertEquals(5, expr.eval(variables));
    }

    @Test
    void testSubBuildString() {
        Expression expr = new Sub(new Number(10), new Number(5));
        StringBuilder sb = new StringBuilder();
        expr.buildString(sb);
        assertEquals("(10-5)", sb.toString());
    }

    @Test
    void testComplexSubEval() {
        Expression expr = new Sub(new Add(new Number(10), new Variable("x")), new Number(5));
        Map<String, Integer> variables = new HashMap<>();
        variables.put("x", 3);
        assertEquals(8, expr.eval(variables));
    }

    @Test
    void testVariableDerivative() {
        Expression expr = new Variable("x");
        Expression derivative = expr.derivative("x");

        StringBuilder sb = new StringBuilder();
        derivative.buildString(sb);
        
        assertEquals("1", sb.toString());
    }

    @Test
    void testVariableDerivativeNonMatching() {
        Expression expr = new Variable("x");
        Expression derivative = expr.derivative("y");

        StringBuilder sb = new StringBuilder();
        derivative.buildString(sb);
        
        assertEquals("0", sb.toString());
    }

    @Test
    void testVariableEval() {
        Expression expr = new Variable("x");
        Map<String, Integer> variables = new HashMap<>();
        variables.put("x", 10);
        
        assertEquals(10, expr.eval(variables));
    }

    @Test
    void testVariableEvalDefault() {
        Expression expr = new Variable("y");
        Map<String, Integer> variables = new HashMap<>();
        
        assertEquals(0, expr.eval(variables));
    }

    @Test
    void testVariableBuildString() {
        Expression expr = new Variable("x");
        StringBuilder sb = new StringBuilder();
        expr.buildString(sb);
        
        assertEquals("x", sb.toString());
    }

    @Test
    void testComplexExpressionWithVariableEval() {
        Expression expr = new Add(new Variable("x"), new Number(5));
        Map<String, Integer> variables = new HashMap<>();
        variables.put("x", 3);
        
        assertEquals(8, expr.eval(variables));
    }

    @Test
    void testComplexExpressionWithVariableDerivative() {
        Expression expr = new Add(new Variable("x"), new Number(5));
        Expression derivative = expr.derivative("x");

        StringBuilder sb = new StringBuilder();
        derivative.buildString(sb);
        
        assertEquals("(1+0)", sb.toString());
    }
}
