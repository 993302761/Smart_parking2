package com.example.Integral.controller;

import com.example.Integral.serviceImpl.IntegralServiceImpl;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.Resource;

@Component
@EnableBinding(Sink.class)
public class IntegralController {



    @StreamListener(Sink.INPUT)
    public void complete_Order(Message<String> message){
        System.out.println(123);
        System.out.println(message.getPayload());
    }

}
