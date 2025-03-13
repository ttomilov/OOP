package org.main;

import java.util.Vector;

import static org.junit.jupiter.api.Assertions.*;

class Test {
    private Vector<Integer> testArray1;
    private Vector<Integer> testArray2;

    void getArray() {
        testArray1 = new Vector<>();
        testArray2 = new Vector<>();
        for (int i = 2; i < 1000000000; i++){
            for (int j = 0; j * j < i; j++){
                if (i % j == 0){
                    break;
                }
            }
            testArray1.add(i);
            testArray2.add(i);
        }
        testArray1.add(6);
    }

    @org.junit.jupiter.api.Test
    void test() {
        ThreadChecker data = new ThreadChecker(testArray1, 8);
        ThreadChecker data2 = new ThreadChecker(testArray2, 8);
        assertTrue(data.findNotPrime());
        assertFalse(data2.findNotPrime());
    }

    @org.junit.jupiter.api.Test
    void test1() {
        Consistently data1 = new Consistently(testArray1);
        Consistently data2 = new Consistently(testArray2);
        assertTrue(data1.findNotPrime());
        assertFalse(data2.findNotPrime());
    }

    @org.junit.jupiter.api.Test
    void test2(){
        ParallelStream data1 = new ParallelStream(testArray1);
        ParallelStream data2 = new ParallelStream(testArray2);
        assertTrue(data1.check());
        assertFalse(data2.check());
    }
}