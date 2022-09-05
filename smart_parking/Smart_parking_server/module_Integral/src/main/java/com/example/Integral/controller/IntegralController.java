package com.example.Integral.controller;

import com.example.Integral.serviceImpl.IntegralServiceImpl;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.Resource;

@Component
@DefaultProperties(defaultFallback ="err")

public class IntegralController {

    @Resource
    private IntegralServiceImpl integralService;

    //Fanout  广播， Direct  路由， Topic   话题
    //Direct  路由
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "Integral", autoDelete = "false"),
            exchange = @Exchange(name = "IntegralExchange",type = ExchangeTypes.DIRECT, autoDelete = "false"),  //交换机
            key = {"addIntegral"}
    ))
    @HystrixCommand
    public void complete_Order(String user_name){
        integralService.addIntegral(user_name);
    }

    private String err(){
        return "积分系统繁忙，请稍后再试";
    }
}
