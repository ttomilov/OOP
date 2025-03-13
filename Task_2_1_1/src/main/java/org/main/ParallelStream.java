package org.main;

import java.util.Vector;
import java.util.stream.IntStream;

/**
 * A class for checking whether an array contains any non-prime numbers using the parallelStream method.
 */

public class ParallelStream extends NotPrimeChecker {
    private Vector<Integer> array;

    ParallelStream(Vector<Integer> array) {
        this.array = array;
    }
    /**
     * Checks if the given array contains at least one non-prime number.
     *
     * @return true if the array contains at least one non-prime number, false otherwise
     */
    public boolean check() {
        return array.parallelStream().anyMatch(this::isNotPrime);
    }
}
