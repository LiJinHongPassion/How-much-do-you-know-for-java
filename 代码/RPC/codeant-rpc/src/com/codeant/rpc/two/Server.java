package com.codeant.rpc.two;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 服务端
 */
public class Server {


    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;
        Socket socket = null;
        InputStream inp = null;
        InputStreamReader isr = null;
        BufferedReader bfr = null;
        OutputStream ots = null;
        PrintWriter pw = null;

        try {
            //创建一个服务器端socket，指定绑定的端口号，并监听此端口
            serverSocket = new ServerSocket(8888);

            //调用accept()方法开始监听，等待客户端的连接
            System.out.println("**********服务器即将启动，等待客户端的连接*************");
            socket = serverSocket.accept();

            //获取输入流，并读取客户端信息
            inp = socket.getInputStream();

            //把字节流转换成字符流
            isr = new InputStreamReader(inp);

            //为字符流增加缓冲区
            bfr = new BufferedReader(isr);
            String info = null;
            while ((info = bfr.readLine()) != null) {//循环读取数据
                System.out.println("我是服务器，客户端说：" + info);
            }

            //关闭输入流
            socket.shutdownInput();

            //向客户端传递的信息
            ots = socket.getOutputStream();
            pw = new PrintWriter(ots);
            pw.write("欢迎登陆");
            pw.flush();
        } finally {
            //关闭资源
            if (pw != null) {
                pw.close();
            }
            if (ots != null) {
                ots.close();
            }
            if (bfr != null) {
                bfr.close();
            }
            if (isr != null) {
                isr.close();
            }
            if (inp != null) {
                inp.close();
            }
            if (socket != null) {
                socket.close();
            }
            if (serverSocket != null) {
                serverSocket.close();
            }
        }
    }
}