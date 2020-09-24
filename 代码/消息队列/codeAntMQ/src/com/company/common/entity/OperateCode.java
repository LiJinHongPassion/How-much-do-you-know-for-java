package com.company.common.entity;

/**
 * 描述: 操作码
 *
 * @author lijinhong
 * @date 20.9.24
 */
public enum OperateCode {
    POP(300), PUSH(200);

    private int code;

    OperateCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
