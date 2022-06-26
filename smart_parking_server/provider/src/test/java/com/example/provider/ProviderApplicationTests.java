package com.example.provider;

import com.example.provider.entiry.User;
import com.example.provider.dao.UserDao;
import org.junit.Test;
import org.junit.runner.RunWith;
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
//声明式事务管理建立在AOP之上的。其本质是对方法前后进行拦截，
// 然后在目标方法开始之前创建或者加入一个事务，
// 在执行完目标方法之后根据执行情况提交或者回滚事务。
public class ProviderApplicationTests {


    @Resource
    private UserDao userMapper;

    @Test
    @Rollback
    //使用回滚注解@Rollback，配合事务注解@Transactional，来实现回滚事务。
    public void contextLoads() {
        List user=userMapper.getAllUsers();
        System.out.printf(user.toString());
    }

}
