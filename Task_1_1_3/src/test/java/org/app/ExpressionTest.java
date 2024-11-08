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

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

class ExpressionTest {

    @Test
    void testNumberEval() {
        Expression num = new Number(5);
        assertEquals(5, num.eval(new HashMap<>()));
    }

    @Test
    void testVariableEval() {
        Expression var = new Variable("x");
        Map<String, Integer> vars = new HashMap<>();
        vars.put("x", 10);
        assertEquals(10, var.eval(vars));
    }

    @Test
    void testAddEval() {
        Expression expr = new Add(new Number(3), new Number(7));
        assertEquals(10, expr.eval(new HashMap<>()));
    }

    @Test
    void testSubEval() {
        Expression expr = new Sub(new Number(10), new Number(4));
        assertEquals(6, expr.eval(new HashMap<>()));
    }

    @Test
    void testMulEval() {
        Expression expr = new Mul(new Number(3), new Number(7));
        assertEquals(21, expr.eval(new HashMap<>()));
    }

    @Test
    void testDivEval() {
        Expression expr = new Div(new Number(20), new Number(5));
        assertEquals(4, expr.eval(new HashMap<>()));
    }

    @Test
    void testDivByZero() {
        Expression expr = new Div(new Number(20), new Number(0));
        assertThrows(ArithmeticException.class, () -> expr.eval(new HashMap<>()));
    }

    @Test
    void testDivVariable() {
        Expression expr = new Div(new Variable("x"), new Variable("y"));
        Map<String, Integer> vars = new HashMap<>();
        vars.put("x", 10);
        vars.put("y", 2);
        assertEquals(5, expr.eval(vars));
    }

    @Test
    void testDivByOne() {
        Expression expr = new Div(new Variable("x"), new Number(1));
        Map<String, Integer> vars = new HashMap<>();
        vars.put("x", 10);
        assertEquals(10, expr.eval(vars));
    }

    @Test
    void testDivMultipleVariables() {
        Expression expr = new Div(new Variable("a"), new Variable("b"));
        Map<String, Integer> vars = new HashMap<>();
        vars.put("a", 8);
        vars.put("b", 4);
        assertEquals(2, expr.eval(vars));
    }

    @Test
    void testDivByVariableOne() {
        Expression expr = new Div(new Variable("x"), new Variable("y"));
        Map<String, Integer> vars = new HashMap<>();
        vars.put("x", 10);
        vars.put("y", 1);
        assertEquals(10, expr.eval(vars));
    }

    @Test
    void testComplexExpressionEval() {
        Expression expr = new Add(new Number(3), new Mul(new Number(2), new Variable("x")));
        int result = expr.eval("x = 10");
        assertEquals(23, result);
    }

    @Test
    void testComplexExpressionPrint() {
        Expression expr = new Add(new Number(3), new Mul(new Number(2), new Variable("x")));
        
        StringBuilder sb = new StringBuilder();
        expr.buildString(sb);
        
        assertEquals("(3+(2*x))", sb.toString());
    }

    @Test
    void testParseAssignments() {
        Expression expr = new Variable("x");
        Map<String, Integer> vars = expr.parseAssignments("x=10;y=20;z=30");
        assertEquals(10, vars.get("x"));
        assertEquals(20, vars.get("y"));
        assertEquals(30, vars.get("z"));
    }

    @Test
    void testInvalidParseAssignments() {
        Expression expr = new Variable("x");
        assertThrows(NumberFormatException.class, () -> expr.eval("x=a"));
    }

    @Test
    void testDerivative() {
        Expression expr = new Add(new Number(3), new Mul(new Number(2), new Variable("x")));
        Expression derivative = expr.derivative("x");

        StringBuilder sb = new StringBuilder();
        derivative.buildString(sb);

        assertEquals("(0+((0*x)+(2*1)))", sb.toString());
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
    void testSubPrint() {
        Expression expr = new Sub(new Variable("x"), new Number(2));
        StringBuilder sb = new StringBuilder();
        expr.buildString(sb);
        assertEquals("(x-2)", sb.toString());
    }

    @Test
    void testDivPrint() {
        Expression expr = new Div(new Variable("z"), new Number(2));
        StringBuilder sb = new StringBuilder();
        expr.buildString(sb);
        assertEquals("(z/2)", sb.toString());
    }

    @Test
    void testNestedExpressionEval() {
        Expression expr = new Add(new Mul(new Number(2), new Variable("x")), new Sub(new Variable("y"), new Number(5)));
        Map<String, Integer> vars = new HashMap<>();
        vars.put("x", 3);
        vars.put("y", 10);
        assertEquals(11, expr.eval(vars));
    }

    @Test
    void testDivDerivative() {
        Expression numerator = new Variable("x");
        Expression denominator = new Variable("y");
        Expression expr = new Div(numerator, denominator);

        Expression derivativeX = expr.derivative("x");
        StringBuilder sbX = new StringBuilder();
        derivativeX.buildString(sbX);
        assertEquals("(((1*y)-(x*0))/(y*y))", sbX.toString());

        Expression derivativeY = expr.derivative("y");
        StringBuilder sbY = new StringBuilder();
        derivativeY.buildString(sbY);
        assertEquals("(((0*y)-(x*1))/(y*y))", sbY.toString());
    }

    @Test
    void testMainDivByZero() {
        // Создаем поток для захвата вывода
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        
        // Перенаправляем System.out на наш поток
        System.setOut(printStream);
        
        // Запускаем main метод с делением на 0
        Expression e = new Add(new Number(3), new Div(new Number(2), new Number(0)));
        e.print();
        
        // Сравниваем вывод
        String output = outputStream.toString().trim();
        // Ожидаемый вывод в случае ошибки деления на 0
        String expectedOutput = "(3+(2/0))";
        assertEquals(expectedOutput, output);
    }
}
