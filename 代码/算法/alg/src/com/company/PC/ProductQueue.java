package com.company.PC;

import java.util.LinkedList;

/**
 * 描述:
 *
 * @author codeant
 * @date 20.8.18
 */
public class ProductQueue<T> {

    private LinkedList<T> queue;
    //容量
    private int capacity;

    public ProductQueue(int capacity) {
        this.capacity = capacity;
        queue = new LinkedList<>();
    }
    //消费
    public T consumption() {
        synchronized (queue) {
            while (queue.isEmpty()) {
                System.out.println("队列没货了, wait");
                try {
                    queue.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            T t = queue.removeFirst();
            queue.notifyAll();
            return t;
        }
    }

    public void production(T t) {
        synchronized (queue) {
            while (queue.size() == capacity) {
                System.out.println("队列装满了, wait");
                try {
                    queue.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            queue.addLast(t);
            queue.notifyAll();
        }
    }
}