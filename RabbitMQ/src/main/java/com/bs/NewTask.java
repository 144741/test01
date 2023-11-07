package com.bs;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class NewTask
{
    //队列名称
    private final static String QUEUE_NAME = "workqueue";

    public static void main(String[] args) throws IOException, TimeoutException {

        //创建连工厂
        ConnectionFactory factory = new ConnectionFactory();
        //设置ip
        factory.setHost("localhost");
        //创建连接
        Connection connection = factory.newConnection();
        //创建队列
        Channel channel = connection.createChannel();
        //声明队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        //发送10条消息，依次在消息后面附加1-10个点
        for (int i = 0; i < 10; i++)
        {
            String dots = "";
            for (int j = 0; j <= i; j++)
            {
                dots += ".";
            }
            //拼数据
            String message = "helloworld" + dots+dots.length();
            //推送到rabbitmq中
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            //推送完成,打印结束语句
            System.out.println(" [x] Sent '" + message + "'");
        }
        //关闭队列
        channel.close();
        //关闭消息
        connection.close();

    }
}


