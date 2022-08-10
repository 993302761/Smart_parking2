package com.feign.api.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("ClientVehicle")
public interface VehicleFeignService {

    @GetMapping(value = "/Vehicle/check_license_plate_number/{user_name}/{license_plate_number}")
    int check_license_plate_number (@PathVariable("user_name") String user_name, @PathVariable("license_plate_number") String license_plate_number);


}
