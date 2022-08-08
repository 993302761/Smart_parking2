package com.example.order.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("ClientVehicle")
public interface VehicleFeignService {

    @GetMapping(value = "/check_license_plate_number/{user_name}/{license_plate_number}")
    int check_license_plate_number (@PathVariable String user_name, @PathVariable String license_plate_number);


}
