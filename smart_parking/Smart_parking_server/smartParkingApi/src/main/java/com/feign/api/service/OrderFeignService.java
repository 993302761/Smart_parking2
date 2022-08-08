package com.feign.api.service;

import com.feign.api.entity.order.Order_information;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient("ClientOrder")
public interface OrderFeignService {

    @GetMapping("/Order/getAllOrders")
    List<Order_information> getAllOrders();
}
