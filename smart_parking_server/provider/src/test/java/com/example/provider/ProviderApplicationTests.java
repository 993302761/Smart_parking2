package com.example.provider;

import com.example.provider.entiry.User;
import com.example.provider.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

//spring-test来初始化单元测试用的IOC容器
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ProviderApplication.class)
@Transactional

class ProviderApplicationTests {

    @Resource
    public UserService userService;



    @Test
    @Rollback
    void contextLoads() {
        User user=userService.findUser("123");
        System.out.println(user.toString());
    }

}
