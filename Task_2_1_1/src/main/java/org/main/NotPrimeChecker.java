package org.main;

abstract class NotPrimeChecker {

    abstract boolean findNotPrime();

    boolean isNotPrime(int num) {
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