package com.company.tcp;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * 描述:
 *
 * @author lijinhong
 * @date 20.9.22
 */
public class Client {
    static final String ip = "127.0.0.1";
    static final int port = 8888;

    public static void main(String[] args) throws IOException {
        //1.定义客户端 Socket
        Socket socket = new Socket(ip,port);//ip ->服务器的ip   端口:服务端的端口号
        //2.io
        //获取输出流   OutputStream getOutputStream()   返回此套接字的输出流。
        DataOutputStream os=new DataOutputStream(socket.getOutputStream());
        os.writeUTF("TCP - 哈哈哈");
        os.flush();
        //3.关闭
        os.close();
        socket.close();
    }
}
