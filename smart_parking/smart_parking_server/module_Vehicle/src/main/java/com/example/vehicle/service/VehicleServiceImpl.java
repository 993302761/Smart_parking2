package com.example.vehicle.service;


import com.example.vehicle.dao.VehicleDao;
import com.example.vehicle.entity.Vehicle;
import com.example.vehicle.entity.Vehicle_information;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class VehicleServiceImpl {

    @Resource
    private VehicleDao vehicleDao;




    /**
     * TODO：添加一条车辆信息
     * @param user_name 用户名
     * @param license_plate_number 车牌号
     * @param picture_index 车辆照片
     * @param registration 机动车登记证照片
     * @param vehicle_license 车辆行驶证照片
     * @return 是否成功
     */
    public String add_Vehicle(String user_name,String license_plate_number, String picture_index,String registration,String vehicle_license) {
//        if (user_name==null||license_plate_number==null||picture_index==null||registration==null||vehicle_license==null){
//            return "所填信息不完整";
//        }
//        User user = userDao.find_User(user_name);
//        if (user==null){
//            return "错误：600";
//        }
//        Vehicle_information vehicleNumber = getVehicleNumber(user_name, license_plate_number);
//        if (vehicleNumber!=null){
//            return "该车辆已注册，请勿重复注册";
//        }
//        int i=vehicleDao.add_Vehicle(user_name,user.getUser_id(),license_plate_number,picture_index,registration,vehicle_license);
//        if (i<=0){
//            return "添加车辆信息失败";
//        }
//        else {
//            return "添加车辆信息成功";
//        }
        return null;
    }



    /**
     * TODO：获取所有的车辆信息
     * @return 获取所有的车辆信息
     */
    public List<Vehicle_information> getAllVehicle() {
        return vehicleDao.getAllVehicle();
    }





    /**
     * TODO：获取用户绑定的车辆信息
     * @param user_name 用户名
     * @return 用户绑定的车辆信息
     */
    public List<Vehicle> getUserVehicle(String user_name) {
        return vehicleDao.find_Vehicle(user_name);
    }




    /**
     * TODO：删除车辆信息
     * @param user_name 用户名
     * @param license_plate_number 车牌号
     * @return 是否成功
     */
    public String delete_User_Vehicle(String user_name,String license_plate_number) {
        int i = vehicleDao.deleteUserVehicle(user_name, license_plate_number);
        if (i<=0){
            return "删除车辆信息失败";
        }else {
            return "删除成功";
        }
    }



    /**
     * TODO：查找车牌号
     * @param user_name 用户名
     * @param license_plate_number 车牌号
     * @return 车辆信息
     */
    public Vehicle_information getVehicleNumber(String user_name,String license_plate_number) {
        return vehicleDao.find_license_plate_number(user_name,license_plate_number);
    }




    /**
     * TODO：绑定的车辆信息
     */
    public void delete_Vehicle (){
        vehicleDao.delete_Vehicle();
    }
}
