package com.feign.api.service;

import com.feign.api.entity.parkingLots.Parking;
import com.feign.api.entity.parkingLots.Parking_for_user;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ParkingLotFeignServiceDegradation implements ParkingLotFeignService{
    @Override
    public List<Parking> getAllParking() {
        List<Parking> parking =new ArrayList<>();
        Parking p=new Parking();
        p.setParking_lot_name("系统繁忙，获取所有停车场失败，请稍后再试");
        parking.add(p);
        return parking;
    }

    @Override
    public String getParkingName(String parking_lot_number) {
        return "系统繁忙，查找停车场名失败，请稍后再试";
    }

    @Override
    public String getParkingBilling_rules(String parking_lot_number) {
        return "系统繁忙，获取停车场收费标准失败，请稍后再试";
    }

    @Override
    public List<Parking_for_user> get_parking_lot(String city) {
        List<Parking_for_user> parking =new ArrayList<>();
        Parking_for_user p=new Parking_for_user();
        p.setParking_lot_name("系统繁忙，获取停车场情况失败，请稍后再试");
        parking.add(p);
        return parking;
    }

    @Override
    public List<Parking_for_user> getParkingLot(String parking_lot_name, String city) {
        List<Parking_for_user> parking =new ArrayList<>();
        Parking_for_user p=new Parking_for_user();
        p.setParking_lot_name("系统繁忙，查找停车场失败，请稍后再试");
        parking.add(p);
        return parking;
    }
}
