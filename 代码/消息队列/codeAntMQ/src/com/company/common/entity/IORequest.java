package com.company.common.entity;

import java.io.Serializable;

/**
 * 描述: 请求体
 *
 * @author lijinhong
 * @date 20.9.24
 */
public class IORequest implements Serializable {
    private Message msg;
    private OperateCode code;// 200-push 300-pop

    public IORequest() {
    }

    public IORequest(Message msg, OperateCode code) {
        this.msg = msg;
        this.code = code;
    }

    public IORequest(OperateCode code) {
        this.msg = null;
        this.code = code;
    }

    public Message getMsg() {
        return msg;
    }

    public void setMsg(Message msg) {
        this.msg = msg;
    }

    public OperateCode getCode() {
        return code;
    }

    public void setCode(OperateCode code) {
        this.code = code;
    }
}
