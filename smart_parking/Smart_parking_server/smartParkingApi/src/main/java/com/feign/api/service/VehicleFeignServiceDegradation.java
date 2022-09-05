package com.feign.api.service;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class VehicleFeignServiceDegradation implements VehicleFeignService{
    @Override
    public int check_license_plate_number(String user_name, String license_plate_number) {
        return -1;
    }

    @Override
    public String vehicle_binding(String user_name, String user_id, String license_plate_number, String vehicle_photos, String registration, String driving_permit) {
        return "系统繁忙，绑定车辆失败，请稍后再试";
    }

    @Override
    public String deleteVehicle(String user_name, String license_plate_number) {
        return "系统繁忙，删除车辆信息失败，请稍后再试";
    }

    @Override
    public String deleteAllVehicle(String user_name) {
        return "系统繁忙，删除用户的所有车辆信息失败，请稍后再试";
    }

    @Override
    public List<String> getUserVehicle(String user_name) {
        List<String> s=new ArrayList<>();
        s.add("获取用户绑定的车辆列表");
        return s;
    }
}
