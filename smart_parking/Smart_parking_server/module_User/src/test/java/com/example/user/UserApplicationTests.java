package com.example.user;

import com.example.user.dao.test;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class UserApplicationTests {

    @Resource
    test test;

    @Test
    void contextLoads() {
        String s = test.uploadFile("/home/lyq/图片/123.png");
        System.out.println(s);
    }

}
