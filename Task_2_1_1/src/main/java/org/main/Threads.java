package org.main;

import static org.main.NotPrime.isNotPrime;

/**
 * This class realise thread to find non-prime numbers.
 */

public class Threads extends Thread {
    private int[] array;
    private boolean res = false;
    private boolean done = false;

    /**
     * Constructs a Threads instance with the specified array.
     *
     * @param array the array of integers to check
     */
    Threads(int[] array) {
        this.array = array;
    }

    /**
     * Checks if the thread has completed execution.
     *
     * @return true if execution is complete, false otherwise
     */
    public boolean isDone() {
        return done;
    }

    /**
     * Gets the result of the check.
     *
     * @return true if a non-prime number was found, false otherwise
     */
    public boolean getResult() {
        return res;
    }

    /**
     * My {@code run} realisation.
     */
    @Override
    public void run() {
        Object lock = ThreadChecker.getLock();
        for (int num : array) {
            boolean interRes = isNotPrime(num);
            if (interRes){
                res = true;
                break;
            }
        }
        done = true;
        synchronized (lock) {
            lock.notify();
        }
    }
}