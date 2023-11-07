//package com.bs;
//
//import com.rabbitmq.client.Channel;
//import com.rabbitmq.client.Connection;
//import com.rabbitmq.client.ConnectionFactory;
//
//import java.io.IOException;
//import java.util.concurrent.TimeoutException;
//
//public class MQProducer {
//    private static final String QUEUE_NAME = "helloWorld11";
//    private static final String EXCHANGE_NAME = "helloExchange1";
//    private static final String ROUTING_KEY = "router";
//
//    public static void main(String[] args) throws IOException, TimeoutException {
//        ConnectionFactory connectionFactory = new ConnectionFactory();
//        connectionFactory.setHost("10.25.102.63");
//        connectionFactory.setPort(5672);
//        connectionFactory.setUsername("xinsight");
//        connectionFactory.setPassword("test");
//        connectionFactory.setVirtualHost("bdn01");
//        Connection connection = connectionFactory.newConnection();
//        Channel channel = connection.createChannel();
//        channel.queueDeclare(QUEUE_NAME, true, false, false, null);
//        channel.exchangeDeclare(EXCHANGE_NAME, "direct", true, false, null);
//        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, ROUTING_KEY);
//        for (int i = 0; i < 1000; i++) {
//            String msg = "helloWord" + i;
//            channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY, null, msg.getBytes());
//            System.out.println("发送消息：msg -->" + msg);
//        }
//        channel.close();
//        connection.close();
//    }
//}
