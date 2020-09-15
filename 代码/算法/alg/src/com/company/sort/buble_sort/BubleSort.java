package com.company.sort.buble_sort;

import java.util.Arrays;

/**
 * 描述: 冒泡排序
 *
 * @author lijinhong
 * @date 20.8.13
 */
public class BubleSort {

    public static void main(String[] args) {
        int[] testArr = new int[]{50,12,45,10,47,1,4,5,41,8};
        sort(testArr);
        //最终结果
        System.out.println(Arrays.toString(testArr));
    }

    public static void sort(int[] arr){
        if ( arr == null ) return;
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length - 1 - i; j++) {
                if ( arr[j] > arr[j + 1] ){
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    //每一次交换结果
                    System.out.println(Arrays.toString(arr));
                }
            }
        }
    }
}
