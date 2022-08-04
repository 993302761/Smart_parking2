package com.example.administrators.service;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("ClientOrder")
public interface OrderFeignService {
}
