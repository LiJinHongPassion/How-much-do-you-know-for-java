package com.company.client.io;

import com.company.client.util.ConfigUtil;
import com.company.common.entity.IORequest;
import com.company.common.entity.IOResponse;
import com.company.common.io.IO;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * 描述:
 *
 * @author lijinhong
 * @date 20.9.24
 */
public class ClientIO implements IO {

    private static String IP_ADDRESS = "127.0.0.1";
    private static int PORT = 8834;
    static {
        String port = ConfigUtil.getValue("port");
        String ip = ConfigUtil.getValue("ip");

        if (port != null
                && ip != null
                && !"".equals(port)
                && !"".equals(ip)){
            PORT = Integer.parseInt(port);
            IP_ADDRESS = ip;
        }
    }

    @Override
    public IOResponse send(IORequest request) throws IOException, ClassNotFoundException {
        //1.定义客户端 Socket
        Socket socket = new Socket(IP_ADDRESS, PORT);//ip ->服务器的ip   端口:服务端的端口号
        //2.bio
        //获取输出流      返回此套接字的输出流。
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        oos.writeObject(request);
        oos.flush();

        //接收服务器的响应
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        IOResponse response = (IOResponse) ois.readObject();

        //3.关闭
        oos.close();
        ois.close();
        socket.close();
        return response;
    }

    @Override
    public IORequest receive() throws IOException, ClassNotFoundException {
        return null;
    }
}
