package com.example.provider.dao;

import com.example.provider.entiry.Parking_lot_information;
import com.example.provider.entiry.User;
import com.example.provider.entiry.Vehicle_information;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface VehicleDao {

    /**
     * 查找用户绑定的车辆信息
     * */
    @Select("SELECT * FROM Vehicle_information WHERE user_name =#{user_name}")
    List<Vehicle_information> find_Vehicle(@Param("user_name") String user_name);


    /**
     * 查找车牌号
     * */
    @Select("SELECT * FROM Vehicle_information WHERE license_plate_number =#{license_plate_number}")
    Vehicle_information find_license_plate_number(@Param("license_plate_number") String license_plate_number);


    /**
     * 检测车牌号与用户名是否匹配
     * */
    @Select("SELECT count(1) FROM Vehicle_information WHERE user_name =#{user_name} and license_plate_number =#{license_plate_number} ")
    int check_license_plate_number(@Param("user_name") String user_name,@Param("license_plate_number") String license_plate_number);

    /**
     * 增加一条车辆信息
     *
     * */
    @Insert("INSERT INTO Vehicle_information VALUES(#{user_name}, #{user_id}, #{license_plate_number}, #{picture_index}, #{registration}, #{vehicle_license})")
    int add_Vehicle(@Param("user_name") String user_name,@Param("user_id")  String user_id,@Param("license_plate_number")  String license_plate_number,@Param("picture_index")  String picture_index,@Param("registration")  String registration,@Param("vehicle_license")  String vehicle_license);


    /**
     * 获取所有的车辆信息
     *
     */
    @Select("select * from Vehicle_information")
    List<Vehicle_information> getAllVehicle();


    /**
     * 删除所绑定的车辆信息
     *
     */
    @Delete("delete from Vehicle_information where user_name =#{user_name} and license_plate_number =#{license_plate_number} ")
    int deleteUserVehicle(@Param("user_name") String user_name,@Param("license_plate_number")  String license_plate_number);

}
