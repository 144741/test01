package com.bs;

import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.rabbitmq.RMQSource;
import org.apache.flink.streaming.connectors.rabbitmq.common.RMQConnectionConfig;

public class RabbitMQParser {
    private static final String QUEUE_NAME = "helloWorld11";

    public static void main(String[] args) throws Exception {
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        final RMQConnectionConfig connectionConfig = new RMQConnectionConfig.Builder()
                .setHost("10.25.102.63")
                .setPort(5672)
                .setUserName("xinsight")
                .setPassword("test")
                .setVirtualHost("bdn01")
                .build();

        DataStream<String> stream = env
                .addSource(new RMQSource<>
                        (connectionConfig, QUEUE_NAME, false, new SimpleStringSchema()))
                .setParallelism(1);
        stream.print();
        env.execute();
    }
}
