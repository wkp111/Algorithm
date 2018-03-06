package com.wkp.algorithm.utils;

import java.util.Arrays;

/**
 * 排序算法
 */
public class Sort {
    /**
     * 冒泡排序，从小到大，相邻比较，大数依次上浮O(n^2)，测试性能最差
     * @param original 原始数组
     */
    public static void bubbleSort(int[] original) {
        for (int i = 0; i < original.length - 1; i++) {
            for (int j = 0; j < original.length - 1 - i; j++) {
                if (original[j] > original[j + 1]) {
                    int temp = original[j];
                    original[j] = original[j + 1];
                    original[j + 1] = temp;
                }
            }
        }
    }

    /*--------------------------------------------------------------分割线-------------------------------------------------------------------------*/

    /**
     * 选择排序，从小到大，循环比较，依次找出较小数O(n^2)，测试性能少量数据（1000）较优，不过总比冒泡强
     * @param original 原始数组
     */
    public static void selectSort(int[] original) {
        for (int i = 0; i < original.length - 1; i++) {
            for (int j = i + 1; j < original.length; j++) {
                if (original[i] > original[j]) {
                    int temp = original[i];
                    original[i] = original[j];
                    original[j] = temp;
                }
            }
        }
    }

    /*--------------------------------------------------------------分割线-------------------------------------------------------------------------*/

    /**
     * 插入排序，从小到大，依次向有序列表插入新数O(n^2)，测试性能与原数组排序优劣有关，不过总比冒泡强，大量数据效果明显
     * @param original
     */
    public static void insertSort(int[] original) {
        int temp,j;
        for (int i = 1; i < original.length; i++) {
            temp = original[i];
            for(j = i; j > 0 && original[j-1] > temp; j--) {
                original[j] = original[j-1];
            }
            original[j] = temp;
        }
    }

    /*--------------------------------------------------------------分割线-------------------------------------------------------------------------*/

    /**
     * 归并排序，从小到大，依次归并有序数组O(nlogn)，测试性能小量数据与选择排序相当，超大量数据性能极佳
     * @param original
     */
    public static void mergeSort(int[] original) {
        sort(original,0,original.length - 1);
    }

    /**
     * 排序
     * @param arr
     * @param start
     * @param end
     */
    private static void sort(int[] arr, int start, int end) {
        int mid = (start + end) >> 1;
        if (start < end) {
            sort(arr,start,mid);
            sort(arr,mid + 1,end);
            merge(arr,start,mid,end);
        }
    }

    /**
     * 合并
     * @param arr
     * @param start
     * @param mid
     * @param end
     */
    private static void merge(int[] arr, int start, int mid, int end) {
        //创建临时缓存数组
        int[] temp = new int[end - start + 1];
        //左、右指针
        int i = start, j = mid + 1, k = 0;
        //依次将较小数移入临时缓存
        while (i <= mid && j <= end) {
            temp[k++] = arr[i] < arr[j] ? arr[i++] : arr[j++];
        }
        //互斥，移入左半区剩余数
        while (i <= mid) {
            temp[k++] = arr[i++];
        }
        //互斥，移入右半区剩余数
        while (j <= end) {
            temp[k++] = arr[j++];
        }
        //临时缓存数组替换指定区间
        System.arraycopy(temp, 0, arr, start, temp.length);
    }

    /*--------------------------------------------------------------分割线-------------------------------------------------------------------------*/

    /**
     * 打印
     * @param original
     */
    public static void toString(int[] original) {
        for (int i = 0; i < original.length - 1; i++) {
            System.out.print(original[i]);
            System.out.print(", ");
        }
        System.out.println(original[original.length - 1]);
    }

    /*--------------------------------------------------------------分割线-------------------------------------------------------------------------*/

    /**
     * 查找最大子数组
     * @param original
     * @return
     */
    public static int[] findMaxSubArr(int[] original) {
        ArrRange range = findRange(original, 0, original.length - 1);
        return Arrays.copyOfRange(original, range.left, range.right + 1);
    }

    /**
     * 查找范围
     * @param original
     * @param low
     * @param high
     * @return
     */
    private static ArrRange findRange(int[] original, int low, int high) {
        if (low == high) {
            return new ArrRange(low,high,original[low]);
        }else {
            int mid = (low + high) >> 1;
            ArrRange leftRange = findRange(original, low, mid);
            ArrRange rightRange = findRange(original, mid + 1, high);
            ArrRange midRange = rangeSum(original, low, mid, high);
            if (leftRange.sum >= rightRange.sum && leftRange.sum >= midRange.sum) {
                return leftRange;
            } else if (rightRange.sum >= leftRange.sum && rightRange.sum >= midRange.sum) {
                return rightRange;
            }else {
                return midRange;
            }
        }
    }

    /**
     * 求中间数组和
     * @param original
     * @param low
     * @param mid
     * @param high
     * @return
     */
    private static ArrRange rangeSum(int[] original, int low, int mid, int high) {
        int leftSum = Integer.MIN_VALUE, rightSum = Integer.MIN_VALUE;
        int sum = 0, left = low, right = high;
        for (int i = mid; i >= low; i--) {
            sum += original[i];
            if (sum > leftSum) {
                leftSum = sum;
                left = i;
            }
        }
        sum = 0;
        for (int i = mid + 1; i <= high; i++) {
            sum += original[i];
            if (sum > rightSum) {
                rightSum = sum;
                right = i;
            }
        }
        return new ArrRange(left, right, leftSum + rightSum);
    }

    /**
     * 始末索引、数组和
     */
    private static class ArrRange{
        int left;
        int right;
        int sum;

        ArrRange(int left, int right, int sum) {
            this.left = left;
            this.right = right;
            this.sum = sum;
        }
    }
}
