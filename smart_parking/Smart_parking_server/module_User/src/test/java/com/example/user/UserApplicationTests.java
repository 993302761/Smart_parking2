package com.example.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.annotation.Resource;

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

        redisTemplate.opsForHash().put("test", "test1", 111);
        Object o = redisTemplate.opsForHash().get("test", "test2");
        if (o == null) {
            System.out.println("ok");
        } else {
            System.out.println("no");
        }
    }
}
