package com.feign.api.service;

import com.feign.api.entity.user.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserFeignServiceDegradation implements UserFeignService{
    @Override
    public List<User> getAllUsers() {
        List<User> user=new ArrayList<>();
        User i=new User();
        i.setUser_name("系统繁忙，获取用户列表失败，请稍后再试");
        user.add(i);
        return user;
    }
}
