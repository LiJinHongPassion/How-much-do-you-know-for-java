package com.codeant.rpc.three.server.socket;

import com.codeant.rpc.three.common.RpcRequest;
import com.codeant.rpc.three.common.RpcResponse;

import java.io.*;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 描述:
 *
 * @author lijinhong
 * @date 20.9.2
 */
public class Server {


    private Object service;

    public Server(Object service) {
        this.service = service;
    }

    public void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;
        Socket socket = null;
        ObjectInputStream ois = null;
        ObjectOutputStream oos = null;

        try {
            //创建一个服务器端socket，指定绑定的端口号，并监听此端口
            serverSocket = new ServerSocket(8888);

            //调用accept()方法开始监听，等待客户端的连接
            System.out.println("**********服务器即将启动，等待客户端的连接*************");
            socket = serverSocket.accept();

            //把二进制流流转换成对象流
            ois = new ObjectInputStream(socket.getInputStream());
            RpcRequest request = (RpcRequest) ois.readObject();

            //关闭输入流
            socket.shutdownInput();

            //---------------动态代理-------------
            RpcResponse response = invoke(request);

            //向客户端传递的信息
            oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(response);
            oos.flush();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关闭资源
            if (ois != null) {
                ois.close();
            }
            if (oos != null) {
                oos.close();
            }
            if (socket != null) {
                socket.close();
            }
            if (serverSocket != null) {
                serverSocket.close();
            }
        }
    }

    /**
     * 反射调用
     * @param request
     * @return
     * @throws Exception
     */
    private RpcResponse invoke(RpcRequest request) throws Exception {

        String className = request.getClassName();
        String methodName = request.getMethodName();
        Object[] parameters = request.getParams();

        //通过接口名称, 获取其实现类;  在spring容器中的话, 可以从容器中获取实例对象; 这里直接获取的实例对象
        //参考 : https://www.cnblogs.com/heaveneleven/p/9125228.html
        if (service == null){
            throw new IllegalArgumentException("service == null");
        }
        Class[] parameterTypes = null;

        if(parameters != null){
        parameterTypes = new Class[parameters.length];
            for (int i = 0, length = parameters.length; i < length; i++) {
                parameterTypes[i] = parameters[i].getClass();
            }
        }

        System.out.println("服务端开始调用--"+ request + ", "+ className + " ," + service.getClass().getName());

        Method method = service.getClass().getMethod(methodName, parameterTypes);

        Object result = method.invoke(service, parameters);

        RpcResponse rpcResponse = new RpcResponse();
        rpcResponse.setCode(200);
        rpcResponse.setData(result);
        return rpcResponse;
    }

}
