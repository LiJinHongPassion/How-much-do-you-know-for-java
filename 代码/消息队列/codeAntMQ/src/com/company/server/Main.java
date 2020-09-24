package com.company.server;

import com.company.server.io.ServerIO;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        start();
    }

    private static void start(){
        ServerIO serverIO = new ServerIO();

        try {
            while (true)
                serverIO.receive();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("消息接收时IO出错");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("消息接收时文件找不到");
        }
    }
}
