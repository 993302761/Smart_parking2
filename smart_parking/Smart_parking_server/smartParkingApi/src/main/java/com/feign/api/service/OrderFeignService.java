package com.feign.api.service;

import com.feign.api.entity.order.Order;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;

@FeignClient(value = "ClientOrder" ,fallbackFactory = OrderFeignServiceDegradation.class)
public interface OrderFeignService {


    @GetMapping("/Order/getAllOrders")
    List<Order> getAllOrders();


    @PutMapping(value = "/Order/parking_cancellation_Order/{parking_lot_number}/{order_number}")
    String parking_cancellation_Order (@PathVariable("parking_lot_number") String parking_lot_number,
                                       @PathVariable("order_number") String order_number);


    @PostMapping(value = "/Order/generate_order/{user_name}/{license_plate_number}/{parking_lot_number}/{generation_time}")
    String  generate_order (@PathVariable("user_name") String user_name,@PathVariable("license_plate_number") String license_plate_number,@PathVariable("parking_lot_number") String parking_lot_number,@PathVariable("generation_time") String generation_time);



    @GetMapping(value = "/Order/userGetParkingOrder/{user_name}/{parking_lot_number}")
    Order userGetParkingOrder (@PathVariable("user_name") String user_name, @PathVariable("parking_lot_number") String parking_lot_number);


    @PutMapping(value = "/Order/complete_Order/{user_name}/{order_number}")
    String complete_Order (@PathVariable("user_name") String user_name,@PathVariable("order_number") String order_number);


    @PutMapping(value = "/Order/app_cancellation_Order/{user_name}/{order_number}")
    String app_cancellation_Order (@PathVariable("user_name") String user_name,@PathVariable("order_number") String order_number);


    @GetMapping(value = "/Order/getOrderByUsername/{user_name}")
    List<Order> getOrderByUsername (@PathVariable("user_name") String user_name);


    @PutMapping(value = "/Order/cancelOrder/{order_number}")
    String cancelOrder (@PathVariable("order_number") String order_number);
}
