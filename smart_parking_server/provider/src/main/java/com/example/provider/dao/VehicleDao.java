package com.example.provider.dao;

import com.example.provider.entiry.Parking_lot_information;
import com.example.provider.entiry.Vehicle_information;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface VehicleDao {

    /**
     * 查找用户绑定的车辆信息
     * */
    @Select("SELECT * FROM Vehicle_information WHERE user_id =#{user_id}")
    Vehicle_information find_Vehicle(@Param("user_id") String user_id);


    /**
     * 查找车牌号
     * */
    @Select("SELECT * FROM Vehicle_information WHERE license_plate_number =#{license_plate_number}")
    Vehicle_information find_license_plate_number(@Param("license_plate_number") String license_plate_number);


    /**
     * 增加一条车辆信息
     *
     * @return*/
    @Insert("INSERT INTO Vehicle_information VALUES(#{user_name}, #{user_id}, #{license_plate_number}, #{picture_index}, #{registration}, #{vehicle_license})")
    int add_Vehicle(@Param("user_name") String user_name,@Param("user_id")  String user_id,@Param("license_plate_number")  String license_plate_number,@Param("picture_index")  String picture_index,@Param("registration")  String registration,@Param("vehicle_license")  String vehicle_license);


}
