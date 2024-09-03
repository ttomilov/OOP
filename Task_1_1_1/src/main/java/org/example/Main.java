package org.example;

public class Main {
  /**
   * Main class
   */
  public static void main(String[] args) {
        int[] arr = {5, 4, 3, 2, 1};
        Heap.heapsort(arr);
        System.out.print("[");
        for (int i = 0; i < arr.length; i++) {
            if (i == arr.length - 1) {
                System.out.print(arr[i]);
            } else {
                System.out.print(arr[i] + ", ");
            }
        }
        System.out.print("]");
    }
}