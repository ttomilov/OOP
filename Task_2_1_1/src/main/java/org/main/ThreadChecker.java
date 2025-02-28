package org.main;

import java.util.ArrayList;
import java.util.Vector;

import static java.util.Arrays.copyOfRange;

/**
 * A class for checking whether an array contains any non-prime numbers using java.lang.Threads.
 */

public class ThreadChecker {
    private int[] array;
    private static final Object lock = new Object();

    /**
     * Gets the lock object used for thread synchronization.
     *
     * @return the lock object
     */
    public static Object getLock() {
        return lock;
    }

    /**
     * Constructs a ThreadChecker with the given array.
     *
     * @param array the array of integers to check
     */
    public ThreadChecker(int[] array) {
        this.array = array;
    }

    /**
     * Uses multiple threads to determine if the array contains at least one non-prime number.
     *
     * @param numOfThreads the number of threads to use for checking
     * @return true if a non-prime number is found, false otherwise
     */
    public boolean findNotPrime(int numOfThreads) {
        Vector<Threads> threads = new Vector<>();
        int end = 0;
        int step = array.length / numOfThreads;
        int rest = array.length % numOfThreads;
        if (step == 0) {
            step = 1;
        }

        for (int i = 0; i < numOfThreads; i++) {
            int start = end;
            end += step + rest;
            if (end > array.length) {
                end = array.length;
            }
            int[] newArray = copyOfRange(array, start, end);
            threads.add(new Threads(newArray));
            threads.get(i).start();
        }

        while (!threads.isEmpty()) {
            synchronized (lock) {
                try {
                    lock.wait();
                } catch (InterruptedException ignored) {
                }
            }
            for (int i = 0; i < threads.size(); i++) {
                if (threads.get(i).isDone()) {
                    if (threads.get(i).getResult()) {
                        for (Threads thread : threads) {
                            thread.interrupt();
                        }
                        return true;
                    } else {
                        threads.remove(i);
                        i--;
                    }
                }
            }
        }
        return false;
    }
}
