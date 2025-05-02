package org.main;

import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

class Test {
    private ArrayList<Integer> testArray1;
    private ArrayList<Integer> testArray2;

    void getArray() {
        testArray1 = new ArrayList<>();
        testArray2 = new ArrayList<>();

        for (int i = 2; i <= 100; i++) {
            if (isPrime(i)) {
                testArray1.add(i);
                testArray2.add(i);
            }
        }

        testArray1.add(1);
    }

    private boolean isPrime(int number) {
        if (number <= 1) {
            return false;
        }
        for (int i = 2; i <= Math.sqrt(number); i++) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }

    @org.junit.jupiter.api.Test
    void test() {
        getArray();
        ThreadChecker data = new ThreadChecker(testArray1, 8);
        ThreadChecker data2 = new ThreadChecker(testArray2, 8);
        assertTrue(data.findNotPrime());
        assertFalse(data2.findNotPrime());
    }

    @org.junit.jupiter.api.Test
    void test1() {
        getArray();
        Consistently data1 = new Consistently(testArray1);
        Consistently data2 = new Consistently(testArray2);
        assertTrue(data1.findNotPrime());
        assertFalse(data2.findNotPrime());
    }

    @org.junit.jupiter.api.Test
    void test2() {
        getArray();
        ParallelStream data1 = new ParallelStream(testArray1);
        ParallelStream data2 = new ParallelStream(testArray2);
        assertTrue(data1.check());
        assertFalse(data2.check());
    }
}
