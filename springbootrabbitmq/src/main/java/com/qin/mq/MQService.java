package com.qin.mq;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class MQService {

    @RabbitListener(queues = "fanout.queue")
    public void receive(Message message){
        System.out.println("收到消息：" + new String(message.getBody()));
    }
}
