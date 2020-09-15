package com.codeant.rpc.three.client;

import com.codeant.rpc.three.client.socket.Client;
import com.codeant.rpc.three.common.InterfaceA;

/**
 * 描述:
 *
 * @author lijinhong
 * @date 20.9.2
 */
public class RpcClient {
    //测试
    public static void main(String... args) {
        Client client = new Client();
        InterfaceA a = (InterfaceA) client.proxy(InterfaceA.class);
        a.doSomething();
    }
}
