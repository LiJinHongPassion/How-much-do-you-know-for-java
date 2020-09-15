package com.codeant.rpc.five.server;

import com.codeant.rpc.five.HelloService;
import com.codeant.rpc.five.HelloServiceImpl;

import java.io.IOException;

/**
 * @desc 暴露服务
 */
public class RpcServer {

    public static void main(String[] args) throws IOException {
        HelloService helloService = new HelloServiceImpl();
        NioServer server = new NioServer(helloService);
        server.start();
    }
}