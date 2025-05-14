package org.main;

import java.util.ArrayList;

/**
 * A class for checking whether an array contains any non-prime numbers using the parallelStream method.
 */

public class ParallelStream extends NotPrimeChecker {
    private final ArrayList<Integer> array;

    ParallelStream(ArrayList<Integer> array) {
        this.array = array;
    }
    /**
     * Checks if the given array contains at least one non-prime number.
     *
     * @return true if the array contains at least one non-prime number, false otherwise
     */
    @Override
    boolean findNotPrime(){
        return array.parallelStream().anyMatch(this::isNotPrime);
    }
    public boolean check() {
        return findNotPrime();
    }
}
