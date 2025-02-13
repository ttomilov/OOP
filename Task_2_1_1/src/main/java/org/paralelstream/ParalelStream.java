package org.paralelstream;

import java.util.stream.IntStream;

public class ParalelStream {
    public static boolean check(int[] array) {
        return IntStream.of(array).filter(ParalelStream::isNotPrime).findAny().isPresent();
    }

    private static boolean isNotPrime(int num) {
        if (num < 2){
            return true;
        }
        for (int i = 2; i * i < num; i++){
            if (num % i == 0){
                return false;
            }
        }
        return true;
    }
}
