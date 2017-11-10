package com.qin.confirm;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by DELL on 2017/11/10.
 */
public class ClientConfirmAck {

    private static final String EXCHANGE_NAME="direct_cc_confirm";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.1.106");
        connectionFactory.setUsername("qcb");
        connectionFactory.setPassword("123456");
        connectionFactory.setVirtualHost("/");
        Connection connection = connectionFactory.newConnection();//连接

        Channel channel = connection.createChannel();//信道
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);//交换器
        //声明队列
        String queue = "confirm_queue";
        channel.queueDeclare(queue,false,false,false,null);
        String server = "error";

        //队列和交换器的绑定
        channel.queueBind(queue,EXCHANGE_NAME,server);
        System.out.println("Waiting message....");

        Consumer consumerB=new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                super.handleDelivery(consumerTag, envelope, properties, body);

                String message=new String(body,"utf-8");
                System.out.println("Accept:"+envelope.getRoutingKey()+":"+message);
                getChannel().basicAck(envelope.getDeliveryTag(),false);
            }
        };

        channel.basicConsume(queue,false,consumerB);
    }
}
