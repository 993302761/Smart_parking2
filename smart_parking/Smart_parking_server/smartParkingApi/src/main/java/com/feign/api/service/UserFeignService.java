package com.feign.api.service;

import com.feign.api.entity.user.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(value = "ClientUser",fallbackFactory = UserFeignServiceDegradation.class)
public interface UserFeignService {

    @GetMapping("/User/getAllUsers")
    List<User> getAllUsers();
}
