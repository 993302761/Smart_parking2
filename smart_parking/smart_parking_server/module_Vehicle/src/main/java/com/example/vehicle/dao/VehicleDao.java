package com.example.vehicle.dao;

import com.example.vehicle.entity.Vehicle_Blob;
import com.example.vehicle.entity.Vehicle_Blob_information;
import org.apache.ibatis.annotations.*;

import java.sql.Blob;
import java.util.List;

@Mapper
public interface VehicleDao {



    /**
     * 查找用户绑定的车辆信息
     * */
    @Select("SELECT license_plate_number FROM Vehicle_information WHERE user_name =#{user_name}")
    List<String> find_Vehicle(@Param("user_name") String user_name);




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
            "#{vehicle_photos_suffix}, " +
            "#{registration}, " +
            "#{registration_suffix}, " +
            "#{driving_permit}, " +
            "#{driving_permit_suffix})")
    int add_Vehicle(@Param("user_name") String user_name,
                    @Param("user_id")  String user_id,
                    @Param("license_plate_number")  String license_plate_number,
                    @Param("vehicle_photos") Blob vehicle_photos,
                    @Param("vehicle_photos_suffix")  String vehicle_photos_suffix,
                    @Param("registration")  Blob registration,
                    @Param("registration_suffix")  String registration_suffix,
                    @Param("driving_permit")  Blob driving_permit,
                    @Param("driving_permit_suffix")  String driving_permit_suffix);




    /**
     * 删除所绑定的车辆信息
     *
     */
    @Delete("delete from Vehicle_information where user_name =#{user_name} and license_plate_number =#{license_plate_number} ")
    int deleteUserVehicle(@Param("user_name") String user_name,@Param("license_plate_number")  String license_plate_number);



}
