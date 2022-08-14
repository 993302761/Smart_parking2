package com.example.user.serviceImpl;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;

import javax.annotation.Resource;

@EnableBinding(Source.class)        //定义消息的推送管道
public class UserIntegralServiceImpl {

    @Resource
    private MessageChannel output;      //消息发送管道

    public String complete_Order(String user_name, String order_number){
        output.send(MessageBuilder.withPayload(user_name).build());
        return null;
    }
}
