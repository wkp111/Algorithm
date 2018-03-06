package com.wkp.algorithm;

import com.wkp.algorithm.utils.Sort;

import java.util.Random;

public class Main {
    public static void main(String[] args) {
        int[] original1 = new int[100000];
        int[] original2 = new int[100000];
        int[] original3 = new int[100000];
        int[] original4 = new int[100000];
        Random random = new Random();
        for (int i = 0; i < 100000; i++) {
            int ri = random.nextInt(9999);
            original1[i] = ri;
            original2[i] = ri;
            original3[i] = ri;
            original4[i] = ri;
        }
        long start = System.currentTimeMillis();
        Sort.bubbleSort(original1);
//        Sort.toString(original1);
        long end = System.currentTimeMillis();
        System.out.println(end - start);

        start = System.currentTimeMillis();
        Sort.selectSort(original2);
//        Sort.toString(original2);
        end = System.currentTimeMillis();
        System.out.println(end - start);

        start = System.currentTimeMillis();
        Sort.insertSort(original3);
//        Sort.toString(original3);
        end = System.currentTimeMillis();
        System.out.println(end - start);

        start = System.currentTimeMillis();
        Sort.mergeSort(original4);
//        Sort.toString(original4);
        end = System.currentTimeMillis();
        System.out.println(end - start);
    }
}
