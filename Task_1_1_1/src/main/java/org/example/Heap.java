package org.example;

public class Heap {

    /**
     * calls function to build heap and sorts it.
     *
     * @return array
     */
    public static int[] heapsort(int[] array) {
        int[] ans = array.clone();
        buildHeap(ans);
        for (int i = ans.length - 1; i >= 0; i--) {
            int help = ans[i];
            ans[i] = ans[0];
            ans[0] = help;
            heapify(ans, i, 0);
        }
        return ans;
    }

    /**
     * build heap from array.
     */

    private static void buildHeap(int[] array) {
        int n = array.length;
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(array, n, i);
        }
    }

    /**
     * The function takes a vertex and its right and left child.
     * Then they are compared with each other, and if the vertex is smaller than one of the children,
     * then they switch places and the function starts again
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
