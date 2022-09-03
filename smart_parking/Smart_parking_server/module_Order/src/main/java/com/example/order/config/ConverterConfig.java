package com.example.order.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.awt.image.DirectColorModel;

@Configuration
public class ConverterConfig {
    //  RabbitMQ会对发送对象做序列化，spring对消息对象的处理是由MessageConverter处理,
    //  默认实现的是SimpleMessageConverter，基于JDK的ObjectOutPutStream完成序列化
    //  若要修改，只需要定义一个MessageConverter类型的Bean就好
    @Bean
    public MessageConverter jsonMessageConverter(){
        return new Jackson2JsonMessageConverter();
    }


    //设置交换机
    @Bean
    public DirectExchange ttlExchange(){
        return new DirectExchange("OrderExchange");
    }

    //设置组
    @Bean
    public Queue ttlQueue(){
        return QueueBuilder.durable("GenerateOrder")
                .ttl(600000)                                 //设置十分钟的超时时间
                .deadLetterExchange("dlOrderExchange")      //设置死信交换机
                .deadLetterRoutingKey("AddOrder")           //设置死信交换机的RoutingKey
                .build();
    }


    @Bean
    public Binding binding(){
        return BindingBuilder.bind(ttlQueue()).to(ttlExchange()).with("addOrder");
    }
}
