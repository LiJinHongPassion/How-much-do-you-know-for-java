package com.company.search;
/**
 * 描述:二分查找
 *
 * 条件: 数组是有序的
 *
 * @author lijinhong
 * @date 20.6.17
 */
public class BinaryFind {


    public static void main(String[] args) {
        int[] a = {1, 12, 13, 27, 34, 38, 49, 49, 64, 65, 76, 78, 97 };
        long start = System.currentTimeMillis();
        System.out.println("位置: " + find(a,27));
        long end = System.currentTimeMillis();
        System.out.println("耗时(ms):" + (end-start));
    }


    public static int find(int[] arr, int value){
        int mid = 0;
        int left = 0;
        int right = arr.length - 1;

        while (left <= right){
            mid = (left + right)/2;
            if(value < arr[mid])
                right = mid - 1;
            else if (value > arr[mid])
                left = mid + 1;
            else
                return mid;
        }
        return -1;
    }
}
