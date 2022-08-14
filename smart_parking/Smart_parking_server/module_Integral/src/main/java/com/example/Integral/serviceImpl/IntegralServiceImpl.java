package com.example.Integral.serviceImpl;

import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class IntegralServiceImpl {

    public void complete_Order(String user_name){
        System.out.println(user_name);
    }

}
