package org.main;

import java.util.Vector;

import static java.util.Arrays.copyOfRange;

/**
 * A class for checking whether an array contains any non-prime numbers using java.lang.Threads.
 */

public class ThreadChecker{
    private Vector<Integer> array;
    private int numThreads;
    private static final Object lock = new Object();

    ThreadChecker(Vector<Integer> array, int numThreads) {
        this.array = array;
        this.numThreads = numThreads;
    }

    /**
     * Gets the lock object used for thread synchronization.
     *
     * @return the lock object
     */
    public static Object getLock() {
        return lock;
    }

    /**
     * Uses multiple threads to determine if the array contains at least one non-prime number.
     *
     * @return true if a non-prime number is found, false otherwise
     */
    public boolean findNotPrime() {
        Vector<Threads> threads = new Vector<>();
        Vector<Thread> threads1 = new Vector<>();
        int start;
        int end = 0;
        int step = array.size() / numThreads;
        int rest = array.size() % numThreads;
        if (step == 0) {
            step = 1;
        }

        for (int i = 0; i < numThreads; i++) {
            start = end;
            end += step + rest;
            if (end > array.size()) {
                end = array.size();
            }
            int[] newArray = copyOfRange(array, start, end);
            threads.add(new Threads(newArray));
            threads1.add(new Thread(threads.get(i)));
            threads1.getLast().start();
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
                        for (Thread thread : threads1) {
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

    private int[] copyOfRange(Vector<Integer> array, int start, int end) {
        int[] newArray = new int[end - start];
        for (int i = 0; i < end - start; i++) {
            newArray[i] = array.get(start + i);
        }
        return newArray;
    }
}
