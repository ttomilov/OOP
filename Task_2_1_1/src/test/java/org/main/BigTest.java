package org.main;

import org.junit.jupiter.api.BeforeAll;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class BigTest {
    private static ArrayList<Integer> array;

    @BeforeAll
    static void getArray() {
        array = new ArrayList<>(3_000_000);
        int count = 0;
        int number = 2;

        while (count < 1_999_999) {
            if (isPrime(number)) {
                array.add(number);
                count++;
            }
            number++;
        }

        array.add(1);
    }

    private static boolean isPrime(int number) {
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
        ThreadChecker[] threads = new ThreadChecker[8];
        int val = 2;
        for (int i = 0; i < 8; i++){
            threads[i] = new ThreadChecker(array, val);
            val += 2;
        }
        val = 2;
        for (int i = 0; i < 8; i++){
            long start = System.currentTimeMillis();
            assertTrue(threads[i].findNotPrime());
            long end = System.currentTimeMillis();
            System.out.println("ThreadChecker, " + val + ": " + (end - start));
            val += 2;
        }
    }

    @org.junit.jupiter.api.Test
    void test1() {
        Consistently data = new Consistently(array);
        long start = System.currentTimeMillis();
        assertTrue(data.findNotPrime());
        long end = System.currentTimeMillis();
        System.out.println("Consistently: " + (end - start));
    }

    @org.junit.jupiter.api.Test
    void test2(){
        ParallelStream data = new ParallelStream(array);
        long start = System.currentTimeMillis();
        assertTrue(data.check());
        long end = System.currentTimeMillis();
        System.out.println("ParallelStream: " + (end - start));
    }
}
