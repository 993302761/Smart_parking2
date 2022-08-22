package com.example.Integral.controller;

import com.example.Integral.serviceImpl.IntegralServiceImpl;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.Resource;

@Component
public class IntegralController {
//Fanout  广播， Direct  路由， Topic   话题


    //Direct  路由
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "Integral"),
            exchange = @Exchange(name = "IntegralExchange",type = ExchangeTypes.DIRECT),
            key = {"addIntegral"}
    ))
    public void complete_Order(String message){
        System.out.println(message);
    }

}
