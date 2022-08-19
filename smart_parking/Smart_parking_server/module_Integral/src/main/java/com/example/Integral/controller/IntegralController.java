package com.example.Integral.controller;

import com.example.Integral.serviceImpl.IntegralServiceImpl;
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



    @RabbitListener(queues = "IntegralChange")      //监听队列的名称
    public void complete_Order(String message){
        System.out.println(9999);
        System.out.println(message);
    }

}
