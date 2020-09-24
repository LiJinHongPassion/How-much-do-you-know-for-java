package com.company.common.entity;


import java.io.Serializable;

/**
 * 描述: 响应体
 *
 * @author lijinhong
 * @date 20.9.24
 */
public class IOResponse implements Serializable {
    private Object res;
    private OperateCode code;

    public IOResponse() {
    }

    public Object getRes() {
        return res;
    }

    public void setRes(Object res) {
        this.res = res;
    }

    public OperateCode getCode() {
        return code;
    }

    public void setCode(OperateCode code) {
        this.code = code;
    }
}
