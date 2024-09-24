package org.example;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Random;

class test1 {
    /**
     * Test1.
     */

    @Test
    void main() {
        int[] arr = {5, 4, 3, 2, 1};
        Heap.heapsort(arr);
        int[] trueArr = {1, 2, 3, 4, 5};
        for (int i = 0; i < trueArr.length; i++){
            assertEquals(trueArr[i], arr[i]);
        }

        Random rand = new Random();
        int[] arr2 = new int[10000];
        for (int i = 0; i < 10000; i++){
            arr2[i] = rand.nextInt(10000000) - 5000000;
        }
        int[] arr3 = Heap.heapsort(arr2);
        Arrays.sort(arr2);
        for (int i = 0; i < arr2.length; i++){
            assertEquals(arr2[i], arr3[i]);
        }
    }

    @Test
    void timeTest(){
        Random rand = new Random();
        int len1 = 1000;
        int len2 = 100000;
        int len3 = 10000000;
        int[] arr1 = new int[len1];
        for (int i = 0; i < len1; i++){
            arr1[i] = rand.nextInt(10000000);
        }
        int[] arr2 = new int[len2];
        for (int i = 0; i < len2; i++){
            arr2[i] = rand.nextInt(10000000);
        }
        int[] arr3 = new int[len3];
        for (int i = 0; i < len3; i++){
            arr3[i] = rand.nextInt(10000000);
        }
        long startTime = System.currentTimeMillis();
        Heap.heapsort(arr1);
        long finishTime = System.currentTimeMillis();
        long duration1 = finishTime - startTime;
        System.out.println("1000 elem, ms: " + duration1);
        startTime = System.currentTimeMillis();
        Heap.heapsort(arr2);
        finishTime = System.currentTimeMillis();
        long duration2 = finishTime - startTime;
        System.out.println("100000 elem, ms: " + duration2);
        startTime = System.currentTimeMillis();
        Heap.heapsort(arr3);
        finishTime = System.currentTimeMillis();
        long duration3 = finishTime - startTime;
        System.out.println("10000000 elem, ms: " + duration3);
        double epsilon = 0.1;
        boolean isEqual1 = Math.abs((Math.log(len1) * len1)
                / (Math.log(len2) * len2) - duration1 / duration2) <= epsilon;
        boolean isEqual2 = Math.abs((Math.log(len2) * len2)
                / (Math.log(len3) * len3) - duration2 / duration3) <= epsilon;
        assert isEqual2 && isEqual1;
    }
}