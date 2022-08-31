package com.feign.api.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient("ClientVehicle")
public interface VehicleFeignService {

    @GetMapping(value = "/Vehicle/check_license_plate_number/{user_name}/{license_plate_number}")
    int check_license_plate_number (@PathVariable("user_name") String user_name, @PathVariable("license_plate_number") String license_plate_number);

    @PostMapping(value = "/Vehicle/vehicle_binding/{user_name}/{user_id}/{license_plate_number}/{vehicle_photos}/{registration}/{driving_permit}")
    String  vehicle_binding ( @PathVariable("user_name") String user_name,
                              @PathVariable("user_id") String user_id,
                              @PathVariable("license_plate_number") String license_plate_number,
                              @PathVariable("vehicle_photos") String vehicle_photos,
                              @PathVariable("registration")String registration,
                              @PathVariable("driving_permit")String driving_permit);


    @DeleteMapping(value = "/Vehicle/deleteVehicle/{user_name}/{license_plate_number}")
    String deleteVehicle (@PathVariable("user_name") String user_name, @PathVariable("license_plate_number") String license_plate_number);


    @DeleteMapping(value = "/Vehicle/deleteAllVehicle/{user_name}")
    String deleteAllVehicle (@PathVariable("user_name") String user_name);


    @GetMapping(value = "/Vehicle/getUserVehicle/{user_name}")
    List<String> getUserVehicle (@PathVariable("user_name") String user_name);

}
