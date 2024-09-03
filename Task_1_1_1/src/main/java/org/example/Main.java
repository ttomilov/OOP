package org.example;

public class Main {

  public static void main(String[] args) {
    Heap.heapsort(new int[]{5, 4, 3, 2, 1});
  }
}

class Heap {

  /**
   * class for heap
   */
  public static void heapsort(int[] array) {
    /**
     * calls function to build heap, sorts it and prints sorted array
     */
    buildHeap(array);
    for (int i = array.length - 1; i >= 0; i--) {
      int help = array[i];
      array[i] = array[0];
      array[0] = help;
      heapify(array, i, 0);
    }
    System.out.print("[");
    for (int i = 0; i < array.length; i++) {
        if (i == array.length - 1) {
            System.out.print(array[i]);
        } else {
            System.out.print(array[i] + ", ");
        }
    }
    System.out.print("]");
  }

  private static void buildHeap(int[] array) {
    /**
     * build heap from array
     */
    int n = array.length;
    for (int i = n / 2 - 1; i >= 0; i--) {
      heapify(array, n, i);
    }
  }

  private static void heapify(int[] array, int n, int i) {
    /**
     * The function takes a vertex and its right and left child. Then they are compared with each other, and if the vertex is smaller than one of the children, then they switch places and the function starts again
     */
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