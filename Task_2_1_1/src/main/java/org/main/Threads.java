package org.main;

import java.util.ArrayList;

/**
 * This class realizes a thread to find non-prime numbers.
 */
public class Threads extends NotPrimeChecker implements Runnable {
    private final ArrayList<Integer> array;
    private boolean res = false;
    private boolean done = false;

    /**
     * Constructs a Threads instance with the specified array.
     *
     * @param array the array of integers to check
     */
    Threads(ArrayList<Integer> array) {
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
     * Runs the thread to check for non-prime numbers.
     */
    @Override
    public boolean findNotPrime() {
        Object lock = ThreadChecker.getLock();
        for (int num : array) {
            if (isNotPrime(num)) {
                res = true;
                break;
            }
        }
        done = true;
        synchronized (lock) {
            lock.notify();
        }
        return true;
    }

    /**
     * The run method for the thread.
     */
    @Override
    public void run() {
        findNotPrime();
    }
}
