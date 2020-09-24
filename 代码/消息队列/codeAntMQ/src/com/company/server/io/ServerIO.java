package com.company.server.io;

import com.company.common.entity.IORequest;
import com.company.common.entity.IOResponse;
import com.company.common.entity.Message;
import com.company.common.entity.OperateCode;
import com.company.common.io.IO;
import com.company.server.queue.AbstractQueue;
import com.company.server.queue.FIFOQueue;
import com.company.server.util.ConfigUtil;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 描述:
 *
 * @author lijinhong
 * @date 20.9.24
 */
public class ServerIO implements IO {

    private static int PORT = 8834;
    //1.定义服务端
    private static ServerSocket server = null;
    private static AbstractQueue fifoQueue = null;

    static {
        //检查配置文件是否设置了PORT
        String port = ConfigUtil.getValue("port");
        if(!"".equals(port) && port != null){
            PORT = Integer.parseInt(port);
        }
        //启动服务器监听端口
        try {
            server = new ServerSocket(PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //初始化队列
        fifoQueue = new FIFOQueue();
    }

    @Override
    public IOResponse send(IORequest request) throws IOException, ClassNotFoundException {
        return null;
    }

    @Override
    public IORequest receive() throws IOException, ClassNotFoundException {
        //2.阻塞式监听   Socket accept()
        Socket client = server.accept();
        //3.bio操作
        //输入流
        ObjectInputStream ois = new ObjectInputStream(client.getInputStream());
        IORequest request = (IORequest) ois.readObject();

        //4.bio
        //获取输出流   OutputStream getOutputStream()   返回此套接字的输出流。
        ObjectOutputStream oos = new ObjectOutputStream(client.getOutputStream());

        IOResponse response = new IOResponse();
        response.setCode(OperateCode.PUSH);
        getResponse(request, response);

        oos.writeObject(response);
        oos.flush();
        //5.关闭
        oos.close();
        client.close();
        return request;
    }

    private void getResponse(IORequest request, IOResponse response) {
        switch (request.getCode()) {
            case PUSH:
                fifoQueue.push(request.getMsg());
                response.setRes("push success!");
                break;
            case POP:
                Message pop = fifoQueue.pop();
                response.setRes(pop);
                break;
        }
    }
}
