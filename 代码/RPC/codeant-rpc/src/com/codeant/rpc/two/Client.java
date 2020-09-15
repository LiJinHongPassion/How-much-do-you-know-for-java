package com.codeant.rpc.two;

import java.io.*;
import java.net.Socket;

/**
 * 客户端
 */
public class Client {

    public static void main(String[] args) {

        OutputStream ots = null;
        PrintWriter pw = null;
        InputStream is = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        Socket socket = null;

        try {
            //1.创建一个客户端socket
            socket = new Socket("127.0.0.1", 8888);

            //2.向服务器端传递信息
            ots = socket.getOutputStream();
            pw = new PrintWriter(ots);
            pw.write("用户名：admin;密码：123");
            pw.flush();

            //3.关闭输出流
            socket.shutdownOutput();

            //4.获取服务器端传递的数据
            is = socket.getInputStream();
            isr = new InputStreamReader(is);
            br = new BufferedReader(isr);
            String info = null;
            while ((info = br.readLine()) != null) {
                System.out.println("我是客户端，服务器说：" + info);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //5.关闭资源
            try {
                if (br != null) {
                    br.close();
                }
                if (isr != null) {
                    isr.close();
                }
                if (is != null) {
                    is.close();
                }
                if (pw != null) {
                    pw.close();
                }
                if (ots != null) {
                    ots.close();
                }
                if (socket != null) {
                    socket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}