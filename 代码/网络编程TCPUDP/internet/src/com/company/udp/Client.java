package com.company.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * 描述:
 *
 * @author lijinhong
 * @date 20.9.22
 */
public class Client {
    public static void main(String[] args) throws IOException {
        System.out.println("-----------发送端--------------");
        //1.定义发送端  DatagramSocket(端口)
        DatagramSocket send=new DatagramSocket(8888);
        //2.准备数据+打包DatagramPacket(byte[] buf, int length, InetAddress address, int port)  构造数据报包，用来将长度为 length 的包发送到指定主机上的指定端口号。
        String str="UDP - 网络编程";
        byte[] arr=str.getBytes();
        DatagramPacket packet=new DatagramPacket(arr,arr.length, InetAddress.getLocalHost(),9999);
        //3.发送
        send.send(packet);
        //4.关闭
        send.close();
    }
}
