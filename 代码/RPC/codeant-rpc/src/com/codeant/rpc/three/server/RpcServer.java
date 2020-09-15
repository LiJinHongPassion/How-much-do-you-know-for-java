package com.codeant.rpc.three.server;

import com.codeant.rpc.five.HelloService;
import com.codeant.rpc.five.HelloServiceImpl;
import com.codeant.rpc.five.server.NioServer;
import com.codeant.rpc.three.common.InterfaceA;
import com.codeant.rpc.three.server.socket.Server;

import java.io.IOException;

/**
 * 描述:
 *
 * @author lijinhong
 * @date 20.9.2
 */
public class RpcServer {
    public static void main(String[] args) throws IOException {
        InterfaceA interfaceA = new InterfaceAImpl();
        Server server = new Server(interfaceA);
        server.main(args);
    }
}
