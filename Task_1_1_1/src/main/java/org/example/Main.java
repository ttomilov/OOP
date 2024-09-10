package org.example;

public class Main {
    public static void main(String[] args){
        /**
         *Class for main.
         */
        int[] array = new int[] {5, 4, 3, 2, 1};
        Heap.heapsort(array);
        System.out.printf("[");
        for (int i = 0; i < array.length; i++){
            if (i == array.length - 1){
                System.out.printf("%d]", array[i]);
                continue;
            }
            System.out.printf("%d, ", array[i]);
        }
    }
}
