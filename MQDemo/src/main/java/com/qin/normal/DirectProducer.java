package com.qin.normal;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by DELL on 2017/11/10.
 */
public class DirectProducer {

    private final static String EXCHANGE_NAME="direct_logs";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory connectionFactory=new ConnectionFactory();
        connectionFactory.setHost("192.168.1.106");
        connectionFactory.setUsername("qcb");
        connectionFactory.setPassword("123456");
        connectionFactory.setVirtualHost("/");
        Connection connection=connectionFactory.newConnection();//连接

        Channel channel=connection.createChannel();//信道
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);//交换器

        String[] serverities={"error","info","warning"};

        for(int i=0;i<serverities.length;i++){
            String server=serverities[i];
            String message="Hello world_"+(i+1);

            channel.basicPublish(EXCHANGE_NAME,server,null,message.getBytes());

            System.out.println("Sent "+server+":"+message);
        }
        channel.close();
        connection.close();
    }
}
