package com.feign.api.service;

import com.feign.api.entity.parkingLots.Parking;
import com.feign.api.entity.parkingLots.Parking_for_user;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient("ClientParkingLots")
public interface ParkingLotFeignService {

    @GetMapping("/ParkingLots/getAllParking")
    List<Parking> getAllParking();


    @GetMapping(value = "/ParkingLots/getParkingName/{parking_lot_number}")
    String getParkingName (@PathVariable("parking_lot_number") String parking_lot_number );

    @GetMapping(value = "/ParkingLots/getParkingBilling_rules/{parking_lot_number}")
    String getParkingBilling_rules (@PathVariable("parking_lot_number") String parking_lot_number );

    @GetMapping(value = "/ParkingLots/get_parking_lot/{city}")
    List<Parking_for_user>  get_parking_lot (@PathVariable("city") String city);

    @GetMapping(value = "/ParkingLots/getParkingLot/{parking_lot_name}/{city}")
    List<Parking_for_user> getParkingLot (@PathVariable("parking_lot_name") String parking_lot_name, @PathVariable("city") String city);



}
