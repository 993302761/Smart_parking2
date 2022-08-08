package com.example.administrators.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;

@FeignClient("ClientOrder")
public interface OrderFeignService {

    @GetMapping("/Order/getAllOrders")
    Object getAllOrders();


    @PutMapping(value = "/parking_cancellation_Order/{parking_lot_number}/{order_number}")
    String parking_cancellation_Order (@PathVariable String parking_lot_number, @PathVariable String order_number);


    @PostMapping(value = "/generate_order")
    String  generate_order (String user_name,String license_plate_number,String parking_lot_number);


    @GetMapping(value = "/userGetParkingOrder/{user_name}/{parking_lot_number}")
    Object userGetParkingOrder (@PathVariable String user_name,@PathVariable String parking_lot_number);


    @PutMapping(value = "/complete_Order/{user_name}/{order_number}")
    String complete_Order (@PathVariable String user_name,@PathVariable String order_number);


    @PutMapping(value = "/app_cancellation_Order/{user_name}/{order_number}")
    String app_cancellation_Order (@PathVariable String user_name,@PathVariable String order_number);

}
