package com.company.sort.heap_sort;

import java.util.Arrays;

/**
 * 描述: 堆排序
 *
 * @author lijinhong
 * @date 20.8.21
 */
public class HeapSort {
    public static void main(String[] args) {
        int[] testArr = new int[]{50, 12, 45, 10, 47, 1, 4, 5, 41, 8};
        sort(testArr);
        //最终结果
        System.out.println(Arrays.toString(testArr));
    }

    public static void sort(int[] tree) {

        int len = tree.length;

        buildMaxHeap(tree, len);
        //每次把根节点取出 和 当前堆的最后一个叶子节点交换, 然后堆的长度-1, 再重新构建堆, 一直循环
        for (int i = len - 1; i > 0; i--) {
            swap(tree, 0, i);
            len--;
            heapify(tree, len,0);
        }
    }

    /**
     * 构建大顶堆
     * @param tree
     * @param len
     */
    private static void buildMaxHeap(int[] tree, int len) {
        // 从最后一个 *父节点* 开始做heapify           最后一个父节点位置 = (最后一个叶子节点位置 - 1 ) / 2
        for (int i = (len - 1) / 2; i >= 0; i--) {
            heapify(tree, len, i);
        }
    }

    /**
     * 当前节点一直往下做一个heapify
     * @param tree
     * @param i
     * @param len
     */
    private static void heapify(int[] tree, int len, int i) {
        if( i >= len){
            return;
        }
        int left = 2 * i + 1; // i这个节点的左孩子
        int right = 2 * i + 2;// i这个节点的右孩子
        int largest = i;
        // int parent = (i - 1) / 2 //i的父节点

        if (left < len && tree[left] > tree[largest]) {
            largest = left;
        }

        if (right < len && tree[right] > tree[largest]) {
            largest = right;
        }

        if (largest != i) {
            swap(tree, i, largest);
            heapify(tree, len, largest);
        }
    }

    /**
     * 交换
     * @param tree
     */
    private static void swap(int[] tree, int i, int j) {
        int temp = tree[i];
        tree[i] = tree[j];
        tree[j] = temp;
    }

}
