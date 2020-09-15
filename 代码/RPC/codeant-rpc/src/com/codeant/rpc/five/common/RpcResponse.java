package com.codeant.rpc.five.common;

public class RpcResponse implements java.io.Serializable{
    private String id;
    private Throwable error;
    private Object result;

    public boolean isError() {
        return null != this.error;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Throwable getError() {
        return error;
    }

    public void setError(Throwable error) {
        this.error = error;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "RpcResponse{" +
                "id='" + id + '\'' +
                ", error=" + error +
                ", result=" + result +
                '}';
    }
}