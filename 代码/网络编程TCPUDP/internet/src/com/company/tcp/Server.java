package com.company.tcp;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 描述:
 *
 * @author lijinhong
 * @date 20.9.22
 */
public class Server {
    static final int port = 8888;

    public static void main(String[] args) throws IOException {
        //1.定义服务端
        ServerSocket server=new ServerSocket(port);
        //2.阻塞式监听   Socket accept()
        Socket client=server.accept();
        //3.io操作
        //输入流
        DataInputStream is=new DataInputStream(client.getInputStream());
        System.out.println(is.readUTF());
        //4.关闭
        is.close();
    }
}
