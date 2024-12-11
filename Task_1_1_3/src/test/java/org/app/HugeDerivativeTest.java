package org.app;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.expressions.Add;
import org.expressions.Mul;
import org.expressions.Number;
import org.expressions.Sub;
import org.expressions.Variable;
import org.junit.jupiter.api.Test;

class HugeDerivativeTest {

    @Test
    void testHugeDerivative() {
        Expression part1 = new Mul(
                new Add(new Variable("x"), new Number(1)),
                new Add(new Variable("x"), new Number(2))
        );

        Expression part2 = new Mul(
                new Add(new Variable("x"), new Number(3)),
                new Add(new Variable("x"), new Number(4))
        );

        Expression part3 = new Mul(
                new Add(new Variable("x"), new Number(5)),
                new Add(new Variable("x"), new Number(6))
        );

        Expression expr = new Sub(
                new Add(part1, part2),
                part3
        );

        Expression derivative = expr.derivative("x");

        StringBuilder sb = new StringBuilder();
        derivative.buildString(sb);

        assertEquals("(((((1+0)*(x+2))+((x+1)*(1+0)))+(((1+0)*(x+4))+((x+3)*(1+0))))-(((1+0)*(x+6))+((x+5)*(1+0))))", sb.toString());
    }
}
