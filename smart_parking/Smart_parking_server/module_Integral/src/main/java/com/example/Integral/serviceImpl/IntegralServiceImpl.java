package com.example.Integral.serviceImpl;

import com.example.Integral.dao.IntegralDao;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service

public class IntegralServiceImpl {


    @Resource
    private IntegralDao integralDao;


    public void addIntegral(String user_name){
        int i = integralDao.addIntegral(user_name);
        if (i<=0){
            System.out.println(user_name+" 积分添加失败");
        }else {
            System.out.println(user_name+" 积分添加成功");
        }
    }

}
