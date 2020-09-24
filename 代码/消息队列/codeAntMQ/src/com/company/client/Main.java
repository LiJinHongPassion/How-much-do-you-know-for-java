package com.company.client;

import com.company.client.io.ClientIO;
import com.company.common.entity.IORequest;
import com.company.common.entity.IOResponse;
import com.company.common.entity.Message;
import com.company.common.entity.OperateCode;

import java.io.IOException;

/**
 * 描述:
 *
 * @author lijinhong
 * @date 20.9.24
 */
public class Main {
    public static void main(String[] args) {
        ClientIO clientIO = new ClientIO();
        //推送第一个消息push进入队列 -- start
        Message one = new Message("this is first message!", 1000L);
        IORequest request = new IORequest(one, OperateCode.PUSH);

        try {
            IOResponse response = clientIO.send(request);
            System.out.println(response.getRes());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("第一个消息发送时IO出错");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("第一个消息发送时文件找不到");
        }

        //推送第一个消息进入队列 -- end

        IORequest request2 = new IORequest(OperateCode.POP);

        try {
            IOResponse response = clientIO.send(request2);
            Message res = (Message) response.getRes();
            System.out.println(res.getObject());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("消息发送时IO出错");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("消息发送时文件找不到");
        }

        //推送第一个消息进入队列 -- end

    }
}
