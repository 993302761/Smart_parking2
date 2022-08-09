package com.example.administrators.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("ClientParkingLots")
public interface ParkingLotFeignService {

    @GetMapping("/ParkingLots/getAllParking")
    Object getAllParking();

    @GetMapping(value = "/ParkingLots/getParkingName/{parking_lot_number}")
    String getParkingName (@PathVariable("parking_lot_number") String parking_lot_number );

    @GetMapping(value = "/ParkingLots/getParkingBilling_rules/{parking_lot_number}")
    String getParkingBilling_rules (@PathVariable("parking_lot_number") String parking_lot_number );

    @GetMapping(value = "/ParkingLots/get_parking_lot/{city}")
    Object get_parking_lot (@PathVariable("city") String city);

    @GetMapping(value = "/ParkingLots/getParkingLot/{parking_lot_name}/{city}")
    Object getParkingLot (@PathVariable("parking_lot_name") String parking_lot_name, @PathVariable("city") String city);



}
