package com.example.user;

import com.example.user.entity.User_information;
import com.example.user.serviceImpl.UserServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.feign.api.entity.user.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.geo.Metrics;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

@SpringBootTest
class UserApplicationTests {

    @Resource
    private StringRedisTemplate stringRedisTemplate;


    @Resource
    private RedisTemplate redisTemplate;
    //json序列化工具
    private final static ObjectMapper mapper = new ObjectMapper();

    @Test
    void contextLoads() throws JsonProcessingException {

        long l = System.currentTimeMillis();
        System.out.println(l);
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long i = System.currentTimeMillis();
        System.out.println(i);
        System.out.println(i-l);
    }
}
