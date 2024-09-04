package org.example;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class test2 {
    /**
     * Test2.
     */

    @Test
    void main() {
        int[] arr = {-1000, -2000, 1, -90, 5};
        Heap.heapsort(arr);
        int[] trueArr = {-2000, -1000, -90, 1, 5};
        for (int i = 0; i < trueArr.length; i++) {
            assertEquals(trueArr[i], arr[i]);
        }
    }
}