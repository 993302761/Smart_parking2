package com.feign.api.service;

import com.feign.api.entity.parkingLots.Parking;
import com.feign.api.entity.parkingLots.Parking_for_user;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;

@Component
@FeignClient(value = "ClientParkingLots" ,fallbackFactory = ParkingLotFeignServiceDegradation.class)

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


    @PutMapping(value = "/ParkingLots/updateParking/{pctr_id}/{parking_lot_name}/{parking_in_the_city}/{parking_spaces_num}/{billing_rules}")
    String updateParking(@PathVariable("pctr_id") String pctr_id,
                         @PathVariable("parking_lot_name") String parking_lot_name,
                         @PathVariable("parking_in_the_city") String parking_in_the_city,
                         @PathVariable("parking_spaces_num") Integer parking_spaces_num,
                         @PathVariable("billing_rules") float billing_rules);


    @GetMapping(value = "/change_parking_space")
    int  findParkingLot  (String parking_lot_number );
}
