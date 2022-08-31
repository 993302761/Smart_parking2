package com.example.vehicle.dao;

import com.example.vehicle.entity.Vehicle;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface VehicleDao {



    /**
     * 查找用户绑定的车牌号
     * */
    @Select("SELECT license_plate_number FROM Vehicle_information WHERE user_name =#{user_name}")
    List<String> find_Vehicle(@Param("user_name") String user_name);



    /**
     * 查找用户绑定的所有车辆信息
     * */
    @Select("SELECT license_plate_number,vehicle_photos,registration,driving_permit FROM Vehicle_information WHERE user_name =#{user_name}")
    List<Vehicle> find_Vehicle_Message(@Param("user_name") String user_name);



    /**
     * 查找用户绑定的车辆信息
     * */
    @Select("SELECT license_plate_number,vehicle_photos,registration,driving_permit FROM Vehicle_information WHERE user_name =#{user_name} and license_plate_number =#{license_plate_number}")
    Vehicle find_User_Vehicle(@Param("user_name") String user_name,@Param("license_plate_number") String license_plate_number);


    /**
     * 检测车牌号与用户名是否匹配
     * */
    @Select("SELECT count(1) FROM Vehicle_information WHERE user_name =#{user_name} and license_plate_number =#{license_plate_number} ")
    int check_license_plate_number(@Param("user_name") String user_name,@Param("license_plate_number") String license_plate_number);




    /**
     * 增加一条车辆信息
     *
     * */
    @Insert("INSERT INTO Vehicle_information VALUES(" +
            "#{user_name}," +
            " #{user_id}, " +
            "#{license_plate_number}, " +
            "#{vehicle_photos}, " +
            "#{registration}, " +
            "#{driving_permit})")
    int add_Vehicle(@Param("user_name") String user_name,
                    @Param("user_id")  String user_id,
                    @Param("license_plate_number")  String license_plate_number,
                    @Param("vehicle_photos") String vehicle_photos,
                    @Param("registration")  String registration,
                    @Param("driving_permit")  String driving_permit);




    /**
     * 删除所绑定的车辆信息
     *
     */
    @Delete("delete from Vehicle_information where user_name =#{user_name} and license_plate_number =#{license_plate_number} ")
    int deleteUserVehicle(@Param("user_name") String user_name,@Param("license_plate_number")  String license_plate_number);



    /**
     * 删除某用户的所有车辆信息
     *
     */
    @Delete("delete from Vehicle_information where user_name =#{user_name} ")
    int deleteAllVehicle(@Param("user_name") String user_name);

}
