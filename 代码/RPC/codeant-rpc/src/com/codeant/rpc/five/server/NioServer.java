package com.codeant.rpc.five.server;

import com.codeant.rpc.five.IOUtil;
import com.codeant.rpc.five.common.RpcRequest;
import com.codeant.rpc.five.common.RpcResponse;

import java.io.*;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class NioServer implements Runnable{

    private Selector selector;
    private ServerSocketChannel server;

    private Object service;

    public NioServer(Object service) throws IOException {
        this(service, 5891);
        this.service = service;
    }

    public NioServer(final Object service, int port) throws IOException {
        this.service = service;
        server = ServerSocketChannel.open();
        server.bind(new InetSocketAddress(port));
        server.configureBlocking(false);//非阻塞

        selector = Selector.open();
        //注册到selector 监听OP_ACCEPT事件
        SelectionKey key = server.register(selector, SelectionKey.OP_ACCEPT);
        //将新连接处理器作为附件，绑定到sk选择器
        key.attach(new Acceptor());

    }

    public void start(){
        new Thread(this).start();
    }

    @Override
    public void run() {
        try {
            System.out.println("rpc 服务启动成功..");
            // class Reactor continued
            //无限循环等待网络请求的到来
            //其中selector.select();会阻塞直到有绑定到selector的请求类型对应的请求到来，一旦收到事件，处理分发到对应的handler，并将这个事件移除
            while(!Thread.interrupted()){
                selector.select();
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()){
                    //反应器负责dispatch收到的事件
                    dispatch(iterator.next());
                    iterator.remove();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 反应器的分发方法
     * @param key
     */
    void dispatch(SelectionKey key){
        Runnable runnable = (Runnable) key.attachment();
        if(runnable != null)
            runnable.run();
    }

    /**
     * 新连接处理器
     */
    class Acceptor implements Runnable{

        @Override
        public void run() {
            try {
                //接收新连接
                //需要为新连接，创建一个输入输出的handler处理器
                SocketChannel c = server.accept();
                System.out.println("新的连接："+ c.getRemoteAddress());
                if(c != null){
                    new Handler(selector, c);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Handler就是负责socket的数据输入、业务处理，结果输出。
     */
    final class Handler implements Runnable {
        final SelectionKey key;
        final SocketChannel socketChannel;

        Handler(Selector selector, SocketChannel socketChannel) throws IOException {
            this.socketChannel = socketChannel;
            socketChannel.configureBlocking(false);
            // Optionally try first read now
            key = socketChannel.register(selector, 0);
            //设置附件
            key.attach(this);
            //注册读写就绪事件
            key.interestOps(SelectionKey.OP_READ);
            selector.wakeup();
        }


        @Override
        public void run() {
            // class Handler continued
            //具体的请求处理，可能是读事件、写事件等
            ObjectInputStream inputStream = null;
            ObjectOutputStream outputStream = null;
            SocketChannel socketChannel = null;
            try {
                if(key.isReadable()){
                    socketChannel = (SocketChannel) key.channel();
                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                    buffer.clear();
                    socketChannel.read(buffer);

                    ByteArrayInputStream bin = new ByteArrayInputStream(buffer.array());

                    inputStream = new ObjectInputStream(bin);
                    RpcRequest request = (RpcRequest) inputStream.readObject();

                    RpcResponse response = new RpcResponse();
                    response.setId(request.getId());
                    try {
                        // 处理并设置返回结果
                        Object result = invoke(request);
                        response.setResult(result);
                    } catch (Throwable t) {
                        response.setError(t);
                    }
                    write(socketChannel, outputStream, response);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                IOUtil.closeQuietly(inputStream);
                IOUtil.closeQuietly(outputStream);
                IOUtil.closeQuietly(socketChannel);
            }
        }
    }

    /**
     * 发送
     * @param socketChannel
     * @param outputStream
     * @param response
     * @throws IOException
     */
    private void write(SocketChannel socketChannel, ObjectOutputStream outputStream, RpcResponse response) throws IOException {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        outputStream = new ObjectOutputStream(bout);
        outputStream.writeObject(response);
        outputStream.flush();
        byte[] arr = bout.toByteArray();
        ByteBuffer wrap = ByteBuffer.wrap(arr);
        socketChannel.write(wrap);
    }

    /**
     * 反射调用
     * @param request
     * @return
     * @throws Exception
     */
    private Object invoke(RpcRequest request) throws Exception {
        if (service == null){
            throw new IllegalArgumentException("service instance == null");
        }

        String className = request.getClassName();
        String methodName = request.getMethodName();
        Object[] parameters = request.getParameters();

        Class[] parameterTypes = new Class[parameters.length];
        for (int i = 0, length = parameters.length; i < length; i++) {
            parameterTypes[i] = parameters[i].getClass();
        }

        System.out.println("服务端开始调用--"+ request + ", "+ className + " ," + service.getClass().getName());

        Method method = service.getClass().getMethod(methodName, parameterTypes);

        Object result = method.invoke(service, parameters);
        return result;
    }

}