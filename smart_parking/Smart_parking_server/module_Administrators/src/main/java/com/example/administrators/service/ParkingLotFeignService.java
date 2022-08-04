package com.example.administrators.service;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("ClientParkingLots")
public interface ParkingLotFeignService {
}
