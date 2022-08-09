package com.feign.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient("ClientUser")
public interface UserFeignService {

    @GetMapping("/User/getAllUsers")
    Object getAllUsers();
}
