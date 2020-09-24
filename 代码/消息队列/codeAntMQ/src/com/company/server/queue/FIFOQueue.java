package com.company.server.queue;

import com.company.common.entity.Message;

import java.util.Calendar;
import java.util.Date;
import java.util.Deque;
import java.util.LinkedList;

/**
 * 描述:消息存储队列 - FIFO
 *
 * @author lijinhong
 * @date 20.9.24
 */
public class FIFOQueue extends AbstractQueue {

    Deque<Message> queue = new LinkedList<>();

    @Override
    public Message pop() {
        Message msg = queue.getLast();
        if (isExp(msg)) {
            queue.removeLast();
            return msg;
        }
        return null;
    }

    @Override
    public synchronized boolean push(Message msg) {
        queue.addFirst(msg);
        return true;
    }

    @Override
    public synchronized boolean clean() {
        queue.forEach(m -> {
            if (!isExp(m)) {
                //这里重写了Hashcode和equal方法，可以直接move
                queue.remove(m);
            }
        });
        return false;
    }

    @Override
    public synchronized boolean remove(Message msg) {
        return false;
    }

    /**
     * 判断消息是否过期
     * true : 有效
     * false ： 无效
     *
     * @param msg
     * @return
     */
    private boolean isExp(Message msg) {
        if (msg.getExpirationTime() == -1) {
            return false;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(msg.getCreateTime());
        calendar.add(Calendar.SECOND, (int) msg.getExpirationTime());

        Calendar nowCalendar = Calendar.getInstance();
        nowCalendar.setTime(new Date());
        int res = calendar.compareTo(nowCalendar);
        return res > 0;
    }

}
