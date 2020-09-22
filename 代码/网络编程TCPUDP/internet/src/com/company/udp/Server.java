package com.company.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * 描述:
 *
 * @author lijinhong
 * @date 20.9.22
 */
public class Server {
    public static void main(String[] args) throws IOException {
        System.out.println("-----------接收端--------------");
        //1.定义接收端   DatagramSocket(端口)
        DatagramSocket receive=new DatagramSocket(9999);
        //2.准备字节数组进行打包
        byte[] arr=new byte[1024*60];
        //DatagramPacket(byte[] buf, int length)  构造 DatagramPacket，用来接收长度为 length 的数据包。
        DatagramPacket packet=new DatagramPacket(arr,arr.length);
        //3.接收
        receive.receive(packet);
		/*
		 * 4.处理数据
		 *  byte[] getData() 返回数据缓冲区。
			int getLength() 返回将要发送或接收到的数据的长度。
		 */
        byte[] datas=packet.getData();
        int len=packet.getLength();
        System.out.println(new String(datas,0,len).length());
        System.out.println(new String(arr).length());
        //5.关闭
        receive.close();
    }
}
