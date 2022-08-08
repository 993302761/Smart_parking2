package com.example.user.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient("ClientOrder")
public interface OrderFeignService {


    @GetMapping("/Order/getAllOrders")
    Object getAllOrders();


    @PutMapping(value = "/Order/parking_cancellation_Order/{parking_lot_number}/{order_number}")
    String parking_cancellation_Order (@PathVariable("parking_lot_number") String parking_lot_number,
                                       @PathVariable("order_number") String order_number);


    @PostMapping(value = "/Order/generate_order/{user_name}/{license_plate_number}/{parking_lot_number}")
    String  generate_order (@PathVariable("user_name") String user_name,
                            @PathVariable("license_plate_number") String license_plate_number,
                            @PathVariable("parking_lot_number") String parking_lot_number);


    @GetMapping(value = "/Order/userGetParkingOrder/{user_name}/{parking_lot_number}")
    Object userGetParkingOrder (@PathVariable("user_name") String user_name,@PathVariable("parking_lot_number") String parking_lot_number);


    @PutMapping(value = "/Order/complete_Order/{user_name}/{order_number}")
    String complete_Order (@PathVariable("user_name") String user_name,@PathVariable("order_number") String order_number);


    @PutMapping(value = "/Order/app_cancellation_Order/{user_name}/{order_number}")
    String app_cancellation_Order (@PathVariable("user_name") String user_name,@PathVariable("order_number") String order_number);

}
