package com.example.provider;

import com.example.provider.entiry.User;
import com.example.provider.service.base.UserService;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

//spring-test来初始化单元测试用的IOC容器
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ProviderApplication.class)
class ProviderApplicationTests {

    @Autowired
    public UserService userService;

    @Before
    public void clearUser(){
        userService.deleteAllUsers();
    }

    @Test
    void contextLoads() {
        userService.add_User("123","456","435216578852316654");
        userService.add_User("456","789","1234564");
        userService.add_User("2222","3333","1234564");
        System.out.println(userService.getAllUsersNumber());
        List<User> s=userService.getAllUsers();
        for (int i = 0; i < s.size(); i++) {
            System.out.println(s.get(i).toString());
        }
    }

}
