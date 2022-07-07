package com.example.provider.service;

import com.example.provider.dao.UserDao;
import com.example.provider.dao.VehicleDao;
import com.example.provider.entiry.User;
import com.example.provider.entiry.Vehicle_information;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class VehicleServiceImpl {

    @Resource
    private VehicleDao vehicleDao;

    /**
     * 添加一条车辆信息
     * */
    public String add_Vehicle(String user_name,String user_id,String license_plate_number, String picture_index,String registration,String vehicle_license) {
        if (user_name==null||user_id==null||license_plate_number==null||picture_index==null||registration==null||vehicle_license==null){
            return "所填信息不完整";
        }
        int i=vehicleDao.add_Vehicle(user_name,user_id,license_plate_number,picture_index,registration,vehicle_license);
        if (i<=0){
            return "添加车辆信息失败";
        }
        else {
            return "添加车辆信息成功";
        }
    }



    /**
     * 获取所有用户列表
     * */
    public List<Vehicle_information> getAllVehicle() {
        return vehicleDao.getAllVehicle();
    }


}
