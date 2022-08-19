package com.example.user.serviceImpl;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserIntegralServiceImpl {

    @Resource
    private RabbitTemplate rabbitTemplate;


    public String complete_Order(String user_name, String order_number){
        System.out.println(user_name);
        rabbitTemplate.convertAndSend("IntegralChange",user_name);
        return null;
    }

}
