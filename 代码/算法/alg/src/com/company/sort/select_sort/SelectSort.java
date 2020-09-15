package com.company.sort.select_sort;

import java.util.Arrays;

/**
 * 描述:
 *
 * @author lijinhong
 * @date 20.8.13
 */
public class SelectSort {

    public static void main(String[] args) {
        int[] testArr = new int[]{50,12,45,10,47,1,4,5,41,8};
        sort(testArr);
        //最终结果
        System.out.println(Arrays.toString(testArr));
    }

    //插入排序
    public static void sort(int[] arr){
        if (arr == null) return;
        for (int i = 0; i < arr.length; i++) {
            for (int j = i; j < arr.length; j++) {
                if ( arr[i] > arr[j] ){
                    int temp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = temp;
                    //每一次交换结果
                    System.out.println(Arrays.toString(arr));
                }
            }
        }
    }
}
