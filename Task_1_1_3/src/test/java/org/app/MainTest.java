package org.app;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    @org.junit.jupiter.api.Test
    void test() {
        Expression e = new Add(new Number(45), new Div(new Number(50), new Number(25)));
        int result = e.eval("x = 10");
        int trueResult = 47;
        assertEquals(result, trueResult);
    }
}