package com.feign.api.service;

import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class VehicleFeignServiceDegradation implements FallbackFactory<VehicleFeignService> {


    @Override
    public VehicleFeignService create(Throwable cause) {
        return new VehicleFeignService() {
            @Override
            public int check_license_plate_number(String user_name, String license_plate_number) {
                log.error("check_license_plate_number 服务出现异常，异常信息：" + cause);
                return -1;
            }

            @Override
            public String vehicle_binding(String user_name, String user_id, String license_plate_number, String vehicle_photos, String registration, String driving_permit) {
                log.error("vehicle_binding 服务出现异常，异常信息：" + cause);
                return "系统繁忙，绑定车辆失败，请稍后再试";
            }

            @Override
            public String deleteVehicle(String user_name, String license_plate_number) {
                log.error("deleteVehicle 服务出现异常，异常信息：" + cause);

                return "系统繁忙，删除车辆信息失败，请稍后再试";
            }

            @Override
            public String deleteAllVehicle(String user_name) {
                log.error("deleteAllVehicle 服务出现异常，异常信息：" + cause);
                return "系统繁忙，删除用户的所有车辆信息失败，请稍后再试";
            }

            @Override
            public List<String> getUserVehicle(String user_name) {
                List<String> s=new ArrayList<>();
                s.add("系统繁忙，获取用户绑定的车辆列表失败，请稍后再试");
                log.error("getUserVehicle 服务出现异常，异常信息：" + cause);
                return s;
            }
        };
    }
}
