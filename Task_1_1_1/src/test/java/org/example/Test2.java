package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Test2 {

    @Test
    void main() {
        int[] arr = {-1000, -2000, 1, -90, 5};
        Heap.heapsort(arr);
        int[] true_arr = {-2000, -1000, -90, 1, 5};
        for (int i = 0; i < true_arr.length; i++) {
            assertEquals(true_arr[i], arr[i]);
        }
    }
}