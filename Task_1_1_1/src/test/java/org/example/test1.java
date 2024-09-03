package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class test1 {
    @Test
    void main() {
        int[] arr = {5, 4, 3, 2, 1};
        Heap.heapsort(arr);
        int[] true_arr = {1, 2, 3, 4, 5};
        for (int i = 0; i < true_arr.length; i++) {
            assertEquals(true_arr[i], arr[i]);
        }
    }
}