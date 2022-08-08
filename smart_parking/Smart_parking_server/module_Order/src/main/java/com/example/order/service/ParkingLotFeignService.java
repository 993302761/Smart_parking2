package com.example.order.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("ClientParkingLots")
public interface ParkingLotFeignService {

    @GetMapping("/ParkingLots/getAllParking")
    Object getAllParking();

    @GetMapping(value = "/ParkingLots/getParkingName/{parking_lot_number}")
    String getParkingName (@PathVariable String parking_lot_number );

    @GetMapping(value = "/getParkingBilling_rules/{parking_lot_number}")
    String getParkingBilling_rules (@PathVariable String parking_lot_number );


}
