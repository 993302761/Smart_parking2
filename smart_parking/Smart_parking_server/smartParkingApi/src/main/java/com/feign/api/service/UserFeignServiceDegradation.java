package com.feign.api.service;

import com.feign.api.entity.user.User;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class UserFeignServiceDegradation implements  FallbackFactory<UserFeignService> {


    @Override
    public UserFeignService create(Throwable cause) {
        return new UserFeignService() {
            @Override
            public List<User> getAllUsers() {
                List<User> user=new ArrayList<>();
                User i=new User();
                i.setUser_name("系统繁忙，获取用户列表失败，请稍后再试");
                user.add(i);
                log.error("getAllUsers 服务出现异常，异常信息：" + cause);
                return user;
            }
        };
    }
}
