package com.feign.api.service;

import com.feign.api.entity.parkingLots.Parking;
import com.feign.api.entity.parkingLots.Parking_for_user;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class ParkingLotFeignServiceDegradation implements FallbackFactory<ParkingLotFeignService> {


    @Override
    public ParkingLotFeignService create(Throwable cause) {
        return new ParkingLotFeignService() {
            @Override
            public List<Parking> getAllParking() {
                List<Parking> parking =new ArrayList<>();
                Parking p=new Parking();
                p.setParking_lot_name("系统繁忙，获取所有停车场失败，请稍后再试");
                parking.add(p);
                log.error("getAllParking 服务出现异常，异常信息：" + cause);
                return parking;
            }

            @Override
            public String getParkingName(String parking_lot_number) {
                log.error("getParkingName 服务出现异常，异常信息：" + cause);
                return "系统繁忙，查找停车场名失败，请稍后再试";
            }

            @Override
            public String getParkingBilling_rules(String parking_lot_number) {
                log.error("getParkingBilling_rules 服务出现异常，异常信息：" + cause);
                return "系统繁忙，获取停车场收费标准失败，请稍后再试";
            }

            @Override
            public List<Parking_for_user> get_parking_lot(String city) {
                List<Parking_for_user> parking =new ArrayList<>();
                Parking_for_user p=new Parking_for_user();
                p.setParking_lot_name("系统繁忙，获取停车场情况失败，请稍后再试");
                parking.add(p);
                log.error("get_parking_lot 服务出现异常，异常信息：" + cause);
                return parking;
            }

            @Override
            public List<Parking_for_user> getParkingLot(String parking_lot_name, String city) {
                List<Parking_for_user> parking =new ArrayList<>();
                Parking_for_user p=new Parking_for_user();
                p.setParking_lot_name("系统繁忙，查找停车场失败，请稍后再试");
                parking.add(p);
                log.error("getParkingLot 服务出现异常，异常信息：" + cause);
                return parking;
            }

            @Override
            public String updateParking(String pctr_id, String parking_lot_number, String parking_in_the_city, Integer parking_spaces_num, float billing_rules) {
                log.error("updateParking 服务出现异常，异常信息：" + cause);
                return "系统繁忙，停车场信息更新失败，请稍后再试";
            }

            @Override
            public int findParkingLot(String parking_lot_number) {
                log.error("findParkingLot 服务出现异常，异常信息：" + cause);
                return -1;
            }
        };
    }
}
