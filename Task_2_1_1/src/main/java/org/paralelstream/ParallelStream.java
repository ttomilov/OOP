package org.paralelstream;

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
        return IntStream.of(array).parallel().filter(ParallelStream::isNotPrime).findAny().isPresent();
    }

    /**
     * Determines if a given number is not prime.
     *
     * @param num the number to check
     * @return true if the number is not prime, false otherwise
     */
    private static boolean isNotPrime(int num) {
        if (num < 2) {
            return true;
        }
        for (int i = 2; i * i <= num; i++) {
            if (num % i == 0) {
                return true;
            }
        }
        return false;
    }
}
