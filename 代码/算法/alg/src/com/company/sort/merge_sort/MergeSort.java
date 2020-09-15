package com.company.sort.merge_sort;

import java.util.Arrays;

/**
 * 描述: 归并排序
 *
 * @author lijinhong
 * @date 20.8.21
 */
public class MergeSort {

    public static void main(String[] args) {
        int[] elem = {80,30,60,40,20,10,50,70};

        mergeSort(elem, 0, elem.length - 1); //从上到下

        System.out.println(Arrays.toString(elem));
    }

    public static void mergeSort(int[] elem, int start, int end) {
        if(elem == null || start >= end) { return; }

        int mid = (start + end) / 2;

        mergeSort(elem, start, mid);
        mergeSort(elem, mid + 1, end);

        merge(elem, start, mid, end);
    }

    public static void merge(int[] elem, int start, int mid, int end) {
        int[] temp = new int[end - start + 1];

        int i = start;
        int j = mid + 1;
        int k = 0;
        while(i <= mid && j <= end) {
            if(elem[i] < elem[j]) {
                temp[k++] = elem[i++];
            }
            else {
                temp[k++] = elem[j++];
            }
        }

        while(i <= mid) {
            temp[k++] = elem[i++];
        }

        while(j <= end) {
            temp[k++] = elem[j++];
        }

        for (i = 0; i < k; i++) {
            elem[start + i] = temp[i];
        }
        temp = null;
    }
}
