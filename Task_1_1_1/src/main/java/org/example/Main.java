package org.example;

public class Main {
    public static void main(String[] args){
        /**
         *Class for main.
         */
        int[] array = new int[] {5, 4, 3, 2, 1};
        int[] ans;
        ans = Heap.heapsort(array);
        System.out.printf("[");
        for (int i = 0; i < ans.length; i++){
            if (i == ans.length - 1){
                System.out.printf("%d]\n", ans[i]);
                continue;
            }
            System.out.printf("%d, ", ans[i]);
        }
    }
}
