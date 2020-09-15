package com.codeant.rpc.four.server;

import com.codeant.rpc.four.common.InterfaceA;

import java.io.Serializable;

//接口实现类
public class InterfaceAImpl implements InterfaceA, Serializable {

    @Override
    public String doSomething() {
        System.out.println("InterfaceImplA.doSomething()");
        return "InterfaceImplA.doSomething()";
    }
}