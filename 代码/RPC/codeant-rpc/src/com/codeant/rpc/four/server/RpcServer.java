package com.codeant.rpc.four.server;

import com.codeant.rpc.four.common.InterfaceA;
import com.codeant.rpc.four.server.socket.Server;

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
