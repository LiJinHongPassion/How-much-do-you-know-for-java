package com.company.sort.shell_sort;

import java.util.Arrays;

/**
 * 描述: 希尔排序
 *
 * @author lijinhong
 * @date 20.8.13
 */
public class ShellSort {
    public static void main(String[] args) {
        int[] testArr = new int[]{9,6,11,3,5,12,8,7,10,15,14,4,1,13,2};
        sort(testArr);
        //最终结果
        System.out.println(Arrays.toString(testArr));
    }

    public static void sort(int[] arr){
        if (arr == null) return;

        //间隔
        int inc = arr.length;
        
        do{
            inc = inc / 3 + 1;
            System.out.println(inc);

            //start ---- 这里可以与直接插入排序对比
            int temp = 0, j = 0;
            for (int i = 1 + inc; i < arr.length; i++) {
                temp = arr[i];
                j = i;
                while (j > 0 && arr[j - 1] >= temp) {
                    arr[j] = arr[j - 1];
                    j--;
                }
                arr[j] = temp;
                //每一次交换结果
                System.out.println(Arrays.toString(arr));
            }
            //end   ---- 这里可以与直接插入排序对比

        }while (inc > 1);
    }

}
