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
     * @return {@code true} if the array contains at least one non-prime number, {@code false} otherwise
     */
    public static boolean check(int[] array) {
        return IntStream.of(array).filter(ParallelStream::isNotPrime).findAny().isPresent();
    }

    /**
     * Determines if a given number is not prime.
     *
     * @param num the number to check
     * @return {@code true} if the number is not prime, {@code false} otherwise
     */
    private static boolean isNotPrime(int num) {
        if (num < 2) {
            return true;
        }
        for (int i = 2; i * i < num; i++) {
            if (num % i == 0) {
                return false;
            }
        }
        return true;
    }
}
