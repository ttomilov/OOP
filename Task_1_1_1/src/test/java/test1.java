package org.example;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class Test1 {
    /**
     * Test1.
     */

    @Test
    void main() {
        int[] arr = {5, 4, 3, 2, 1};
        Heap.heapsort(arr);
        int[] trueArr = {1, 2, 3, 4, 5};
        for (int i = 0; i < trueArr.length; i++) {
            assertEquals(trueArr[i], arr[i]);
        }
    }
}