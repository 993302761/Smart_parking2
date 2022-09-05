package com.example.administrators.Degradation;

import com.feign.api.entity.user.User;
import com.feign.api.service.UserFeignService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserFeignServiceDegradation implements UserFeignService {
    @Override
    public List<User> getAllUsers() {
        return null;
    }
}
