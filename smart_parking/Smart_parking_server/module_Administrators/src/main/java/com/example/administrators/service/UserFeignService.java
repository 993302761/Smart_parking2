package com.example.administrators.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient("ClientUser")
public interface UserFeignService {

    @GetMapping("/User/getAllUsers")
    Object getAllUsers();
}
