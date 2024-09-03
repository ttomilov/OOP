package org.example;

public class Heap {
    /**
     * calls function to build heap and sorts it
     * @param
     * @return
     */
    public static int[] heapsort(int[] array) {
        buildHeap(array);
        for (int i = array.length - 1; i >= 0; i--) {
            int help = array[i];
            array[i] = array[0];
            array[0] = help;
            heapify(array, i, 0);
        }
        return array;
    }

    /**
     * build heap from array
     * @param
     */
    private static void buildHeap(int[] array) {
        int n = array.length;
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(array, n, i);
        }
    }

    /**
     * The function takes a vertex and its right and left child. Then they are compared with each other, and if the vertex is smaller than one of the children, then they switch places and the function starts again
     * The function takes a vertex and its right and left child.
     * Then they are compared with each other, and if the vertex is smaller than one of the children,
     * then they switch places and the function starts again
     * @param
     * @param
     * @param
     */

    private static void heapify(int[] array, int n, int i) {
        int max = i;
        int right = 2 * i + 1;
        int left = 2 * i + 2;
        if (right < n && array[right] > array[max]) {
            max = right;
        }
        if (left < n && array[left] > array[max]) {
            max = left;
        }
        if (max != i) {
            int help = array[i];
            array[i] = array[max];
            array[max] = help;
            heapify(array, n, max);
        }
    }
}
