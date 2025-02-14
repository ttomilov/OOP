package org.consistently;

/**
 * A class for checking whether an array contains any non-prime numbers.
 * using method of iterating through the divisors for each number in an array.
 */

public class Consistently {
    /**
     * Checks if the given array contains at least one non-prime number.
     *
     * @param array the array of integers to check
     * @return {@code true} if the array contains at least one non-prime number, {@code false} otherwise
     */
    public static boolean findNotPrime(int[] array) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] < 2) {
                return true;
            }
            for (int j = 2; j * j <= array[i]; j++) {
                if (array[i] % j == 0) break;
            }
        }
        return false;
    }
}