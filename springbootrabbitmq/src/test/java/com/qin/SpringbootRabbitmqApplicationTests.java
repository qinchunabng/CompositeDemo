package com.qin;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootRabbitmqApplicationTests {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private AmqpAdmin amqpAdmin;

    @Test
    public void contextLoads(){}

    @Test
    public void create(){
        //创建exchange
        amqpAdmin.declareExchange(new DirectExchange("exchange.direct"));
        amqpAdmin.declareExchange(new FanoutExchange("exchange.fanout"));
        amqpAdmin.declareExchange(new TopicExchange("exchange.topic"));
        //创建queue
        amqpAdmin.declareQueue(new Queue("direct.queue",true));
        amqpAdmin.declareQueue(new Queue("fanout.queue",true));

        //绑定queue
        amqpAdmin.declareBinding(new Binding("direct.queue", Binding.DestinationType.QUEUE, "exchange.direct", "direct.queue", null));
        amqpAdmin.declareBinding(new Binding("fanout.queue", Binding.DestinationType.QUEUE, "exchange.direct", "fanout.queue", null));
        amqpAdmin.declareBinding(new Binding("direct.queue", Binding.DestinationType.QUEUE, "exchange.fanout", "", null));
        amqpAdmin.declareBinding(new Binding("fanout.queue", Binding.DestinationType.QUEUE, "exchange.fanout", "", null));
        amqpAdmin.declareBinding(new Binding("direct.queue", Binding.DestinationType.QUEUE, "exchange.topic", "direct.#", null));
        amqpAdmin.declareBinding(new Binding("fanout.queue", Binding.DestinationType.QUEUE, "exchange.topic", "direct.*", null));
    }

    @Test
    public void send2Direct(){
        Map<String, Object> map = new HashMap<>();
        map.put("msg", "这是一条点对点的消息");
        map.put("data", Arrays.asList("hello world", 123, true));
        rabbitTemplate.convertAndSend("exchange.direct", "direct.queue", map);
    }

    @Test
    public void send2Topic(){
        Map<String , Object> map = new HashMap<>();
        map.put( "msg" , "这是一条广播消息" );
        map.put( "data" , Arrays.asList("topic消息" , 123 , true) );

        rabbitTemplate.convertAndSend( "exchange.fanout" , "", map );
    }

    @Test
    public void receive() {
        Object o = rabbitTemplate.receiveAndConvert( "direct.queue" );
        o.getClass();
        System.out.println(o.getClass());
        System.out.println(o);
    }
}
