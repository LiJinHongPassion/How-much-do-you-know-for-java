package com.codeant.rpc.three.common;

import java.io.Serializable;

/**
 * 描述: 返回给client端的参数结果
 *
 * @author lijinhong
 * @date 20.9.1
 */
public class RpcResponse implements Serializable {
	private int code;//200成功，500失败
	private String msg;
	private Object data;

	public RpcResponse() {
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "RpcResponse [code=" + code + ", msg=" + msg + ", data=" + data + "]";
	}
}