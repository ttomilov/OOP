package org.main;

import java.util.ArrayList;
import java.util.Vector;

/**
 * A class for checking whether an array contains any non-prime numbers.
 * using method of iterating through the divisors for each number in an array.
 */

public class Consistently extends NotPrimeChecker {
    private final ArrayList<Integer> array;
    /**
     * Checks if the given array contains at least one non-prime number.
     *
     * @param array the array of integers to check
     * @return true if the array contains at least one non-prime number, false otherwise
     */

    Consistently(ArrayList<Integer> array) {
        this.array = array;
    }

    @Override
    public boolean findNotPrime() {
        for (int num : array) {
            boolean res = isNotPrime(num);
            if (res) {
                return true;
            }
        }
        return false;
    }
}