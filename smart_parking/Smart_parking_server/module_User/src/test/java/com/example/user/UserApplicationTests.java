package com.example.user;

import com.example.user.entity.User_information;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.annotation.Resource;
import java.util.Map;

@SpringBootTest
class UserApplicationTests {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    //json序列化工具
    private final static ObjectMapper mapper=new ObjectMapper();

    @Test
    void contextLoads() throws JsonProcessingException {
        User_information user_information = new User_information();
        user_information.setUser_id("111");
        user_information.setUser_name("222");
        user_information.setPassword("333");
        //手动序列化
        String json=mapper.writeValueAsString(user_information);
//        stringRedisTemplate.opsForValue().set("test",json);
//        String value=stringRedisTemplate.opsForValue().get("test");
        stringRedisTemplate.opsForHash().put("test","test1",json);
        user_information.setUser_id("4444");
        user_information.setUser_name("5555");
        user_information.setPassword("6666");
        json=mapper.writeValueAsString(user_information);

        stringRedisTemplate.opsForHash().put("test","test2",json);
        Map<Object, Object> test = stringRedisTemplate.opsForHash().entries("test");
        //反序列化
//        User_information userInformation=mapper.readValue(value,User_information.class);
        System.out.println(test);

//        redisTemplate.opsForValue().set("test",user_information);
//        User_information t = (User_information) redisTemplate.opsForValue().get("test");
//        System.out.println(t);


    }

}
