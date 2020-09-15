package com.codeant.rpc.three.client.socket;

import com.codeant.rpc.three.common.RpcRequest;
import com.codeant.rpc.three.common.RpcResponse;

import java.io.*;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.Socket;
import java.util.UUID;

/**
 * 描述:
 *
 * @author lijinhong
 * @date 20.9.2
 */
public class Client {

    /**
     * 远程调用
     * @param request RpcRequest
     * @return RpcResponse
     */
    private RpcResponse send(RpcRequest request){

        OutputStream ots = null;
        ObjectOutputStream oos = null;
        InputStream is = null;
        ObjectInputStream ois = null;
        Socket socket = null;

        try {
            //1.创建一个客户端socket
            socket = new Socket("127.0.0.1", 8888);

            //2.向服务器端传递信息
            ots = socket.getOutputStream();
            oos = new ObjectOutputStream(ots);
            oos.writeObject(request);
            oos.flush();

            //3.关闭输出流
            socket.shutdownOutput();

            //4.获取服务器端传递的数据
            is = socket.getInputStream();
            ois = new ObjectInputStream(is);

            return (RpcResponse) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            //5.关闭资源
            try {
                if (oos != null) {
                    oos.close();
                }
                if (ots != null) {
                    ots.close();
                }
                if (is != null) {
                    is.close();
                }
                if (ois != null) {
                    ois.close();
                }
                if (socket != null) {
                    socket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    /**
     * 动态代理
     * @param interfaceClass
     * @param <T>
     * @return
     * @throws Exception
     */
    public <T> T proxy(Class<T> interfaceClass){
        return (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class[]{interfaceClass}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                RpcRequest request = new RpcRequest();
                request.setClassName(interfaceClass.getName());
                request.setMethodName(method.getName());
                request.setParams(args);
                RpcResponse response = send(request);
                System.out.println("调用完成--" + response);
                // 检测是否有异常
                if (response.getCode() == 500) {
                    System.out.println(response.getMsg());
                    throw new Exception();
                } else {
                    return response.getData();
                }
            }
        });
    }
}
