package org.main;

public class Consistently {
    public static boolean findNotPrime(int[] array) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] < 2) return true;
            for (int j = 2; j * j <= array[i]; j++) {
                if (array[i] % j == 0) break;
            }
        }
        return false;
    }
}


