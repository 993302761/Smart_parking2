package com.feign.api.service;

import com.feign.api.entity.parkingLots.Parking;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient("ClientParkingLots")
public interface ParkingLotFeignService {

    @GetMapping("/ParkingLots/getAllParking")
    List<Parking> getAllParking();
}
