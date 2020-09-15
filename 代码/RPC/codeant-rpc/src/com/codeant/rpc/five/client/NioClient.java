package com.codeant.rpc.five.client;

import com.codeant.rpc.five.IOUtil;
import com.codeant.rpc.five.common.RpcRequest;
import com.codeant.rpc.five.common.RpcResponse;

import java.io.*;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.UUID;


public class NioClient {
    private int port = 5891;
    private String host = "127.0.0.1";

    /**
     * 远程调用
     *
     * @param request
     * @return
     */
    public RpcResponse send(RpcRequest request) {
        ObjectInputStream inputStream = null;
        ObjectOutputStream outputStream = null;
        SocketChannel client = null;
        try {
            client = SocketChannel.open(new InetSocketAddress(host, port));
            write(client, outputStream, request);

            //接收
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            buffer.clear();
            client.read(buffer);
            ByteArrayInputStream bin = new ByteArrayInputStream(buffer.array());

            inputStream = new ObjectInputStream(bin);
            RpcResponse response = (RpcResponse) inputStream.readObject();
            return response;
        } catch (Exception e) {
            throw new RuntimeException("发起远程调用异常！", e);
        } finally {
            IOUtil.closeQuietly(inputStream);
            IOUtil.closeQuietly(outputStream);
            IOUtil.closeQuietly(client);
        }
    }

    /**
     * 发送
     *
     * @param socketChannel
     * @param outputStream
     * @param request
     * @throws IOException
     */
    private void write(SocketChannel socketChannel, ObjectOutputStream outputStream, RpcRequest request) throws IOException {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        outputStream = new ObjectOutputStream(bout);
        outputStream.writeObject(request);
        outputStream.flush();
        byte[] arr = bout.toByteArray();
        ByteBuffer wrap = ByteBuffer.wrap(arr);
        socketChannel.write(wrap);
    }

    /**
     * 动态代理
     *
     * @param interfaceClass
     * @param <T>
     * @return
     * @throws Exception
     */
    public <T> T proxy(Class<T> interfaceClass) {
        return (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class[]{interfaceClass}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                RpcRequest request = new RpcRequest();
                request.setId(UUID.randomUUID().toString());
                request.setClassName(interfaceClass.getName());
                request.setMethodName(method.getName());
                request.setParameters(args);
                RpcResponse response = send(request);
                System.out.println("调用完成--" + response);
                // 检测是否有异常
                if (response.isError()) {
                    throw response.getError();
                } else {
                    return response.getResult();
                }
            }
        });
    }

}