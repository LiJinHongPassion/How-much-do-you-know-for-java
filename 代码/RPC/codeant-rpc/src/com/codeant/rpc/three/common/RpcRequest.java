package com.codeant.rpc.three.common;

import java.io.Serializable;

/**
 * 描述: 接收client端的请求参数
 *
 * @author lijinhong
 * @date 20.9.1
 */
public class RpcRequest implements Serializable {
	private String className;//接口类名称
    private String methodName;//接口类中方法名称
    private Class[] types;//接口类中参数类型
    private Object[] params;//接口类中参数

    public RpcRequest(String className, String methodName, Class[] types, Object[] params) {
        this.className = className;
        this.methodName = methodName;
        this.types = types;
        this.params = params;
    }

    public RpcRequest() {
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Class[] getTypes() {
        return types;
    }

    public void setTypes(Class[] types) {
        this.types = types;
    }

    public Object[] getParams() {
        return params;
    }

    public void setParams(Object[] params) {
        this.params = params;
    }
}