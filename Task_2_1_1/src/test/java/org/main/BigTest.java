package org.main;

import java.util.Vector;

import static org.junit.jupiter.api.Assertions.*;

public class BigTest {
    private Vector<Integer> array;

    void getArray() {
        array = new Vector<>();
        for (int i = 2; i < 1000000000; i++){
            for (int j = 0; j * j < i; j++){
                if (i % j == 0){
                    break;
                }
            }
            array.add(i);
        }
    }

    @org.junit.jupiter.api.Test
    void test() {
        getArray();
        ThreadChecker[] threads = new ThreadChecker[8];
        int val = 2;
        for (int i = 0; i < 8; i++){
            threads[i] = new ThreadChecker(array, val);
            val += 2;
        }
        val = 2;
        for (int i = 0; i < 8; i++){
            long start = System.currentTimeMillis();
            threads[i].findNotPrime();
            long end = System.currentTimeMillis();
            System.out.println("ThreadChecker, " + val + ": " + (end - start));
        }
    }

    @org.junit.jupiter.api.Test
    void test1() {
        getArray();
        Consistently data = new Consistently(array);
        long start = System.currentTimeMillis();
        assertTrue(data.findNotPrime());
        long end = System.currentTimeMillis();
        System.out.println("Consistenly: " + (end - start));
    }

    @org.junit.jupiter.api.Test
    void test2(){
        getArray();
        ParallelStream data = new ParallelStream(array);
        long start = System.currentTimeMillis();
        assertTrue(data.check());
        long end = System.currentTimeMillis();
        System.out.println("ParallelStream: " + (end - start));
    }
}
