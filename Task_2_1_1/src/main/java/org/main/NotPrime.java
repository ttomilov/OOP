package org.main;

public class NotPrime {
    /**
     * Determines if a given number is not prime.
     *
     * @param num the number to check
     * @return true if the number is not prime, false otherwise
     */
    static boolean isNotPrime(int num) {
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
