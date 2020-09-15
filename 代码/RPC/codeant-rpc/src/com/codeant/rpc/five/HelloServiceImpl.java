package com.codeant.rpc.five;

public class HelloServiceImpl implements HelloService {
    @Override
    public String hello(String msg) {
        return "Hello "+ msg;
    }
}