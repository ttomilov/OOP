package org.main;

import java.util.stream.IntStream;

/**
 * A class for checking whether an array contains any non-prime numbers using the parallelStream method.
 */

public class ParallelStream {

    /**
     * Checks if the given array contains at least one non-prime number.
     *
     * @param array the array of integers to check
     * @return true if the array contains at least one non-prime number, false otherwise
     */
    public static boolean check(int[] array) {
        return IntStream.of(array).parallel().filter(NotPrime::isNotPrime).findAny().isPresent();
    }
}
