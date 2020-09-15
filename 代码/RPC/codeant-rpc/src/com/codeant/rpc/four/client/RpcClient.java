package com.codeant.rpc.four.client;

import com.codeant.rpc.four.client.socket.Client;
import com.codeant.rpc.four.common.InterfaceA;

import java.util.Date;

/**
 * 描述:
 *
 * @author lijinhong
 * @date 20.9.2
 */
public class RpcClient {
    //测试
    public static void main(String... args) {

        int i = 0;
        Client client = new Client();
        InterfaceA a = client.proxy(InterfaceA.class);

        while (i++ < 10) {

            new Thread(
                    () -> { a.doSomething();  System.out.println(Thread.currentThread().getName() + " --- " + new Date());  },
                    String.valueOf(i)
            ).start();
        }
    }
}
