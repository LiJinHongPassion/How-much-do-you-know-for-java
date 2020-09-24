package com.company.common.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * 描述: 消息体
 *
 * @author lijinhong
 * @date 20.9.24
 */
public class Message implements Serializable {

    private Object object;
    private Date createTime;
    private long expirationTime;

    public Message() {
    }

    public Message(Object object, Long expirationTime) {
        this.object = object;
        this.createTime = new Date();
        if (expirationTime == null) {
            this.expirationTime = -1;
        } else
            this.expirationTime = expirationTime;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public long getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(long expirationTime) {
        this.expirationTime = expirationTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Message)) return false;
        Message message = (Message) o;
        return Objects.equals(getObject(), message.getObject()) &&
                Objects.equals(getCreateTime(), message.getCreateTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getObject(), getCreateTime());
    }
}
