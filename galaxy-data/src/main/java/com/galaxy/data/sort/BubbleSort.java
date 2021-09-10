package com.galaxy.data.sort;

import java.util.Arrays;

/**
 * 冒泡排序
 * @author galaxy
 * @since 2021/9/10 8:08
 */
public class BubbleSort {
    public static void main(String[] args) {
        int[] nums = {10, 3, 9, 19};
        int[] result = bubbleSort(nums);
        System.out.println(Arrays.toString(result));
    }
    public static int[] bubbleSort(int[] arr){
        if (arr == null) return null;
        int length = arr.length;

        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length - i - 1; j++) {
                if (arr[i] > arr[i + 1]){
                    int tmp = arr[i];
                    arr[i] = arr[i + 1];
                    arr[i + 1] = tmp;
                }
            }
        }
        return arr;
    }
}
