package com.codeant.rpc.five.client;


import com.codeant.rpc.five.HelloService;

/**
 * @desc 引用服务
 */
public class RpcClient {
    public static void main(String[] args) throws InterruptedException {
        NioClient client = new NioClient();
        HelloService helloService = client.proxy(HelloService.class);

        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            String hello = helloService.hello("World " + i);
            System.out.println(hello);
            Thread.sleep(500);
        }
    }
}