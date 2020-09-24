package com.company.server.queue;

import com.company.common.entity.Message;

/**
 * 描述:抽象队列
 *
 * @author lijinhong
 * @date 20.9.24
 */
public abstract class AbstractQueue {
    /**
     * 出队列
     *
     * @return
     */
    public abstract Message pop();

    /**
     * 入队列
     *
     * @param msg
     * @return
     */
    public abstract boolean push(Message msg);

    /**
     * 清除过期消息
     *
     * @return
     */
    public abstract boolean clean();

    /**
     * 移除指定消息
     *
     * @param msg
     * @return
     */
    public abstract boolean remove(Message msg);
}
