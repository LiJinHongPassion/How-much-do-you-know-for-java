package com.codeant.rpc.three.server;

import com.codeant.rpc.three.common.InterfaceA;

import java.io.Serializable;

//接口实现类
public class InterfaceAImpl implements InterfaceA, Serializable {

    @Override
    public String doSomething() {
        System.out.println("InterfaceImplA.doSomething()");
        return "InterfaceImplA.doSomething()";
    }
}