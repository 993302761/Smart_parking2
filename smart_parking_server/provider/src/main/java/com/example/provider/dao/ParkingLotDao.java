package com.example.provider.dao;

import com.example.provider.entiry.Parking_lot_information;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ParkingLotDao {

    /**
     * 增加一个停车场管理员
     *
     * @return*/
    @Insert("INSERT INTO Parking_lot_information VALUES(#{pctr_id}, #{pctr_password}, #{parking_lot_name}, #{Parking_in_the_city}, #{parking_spaces_num}, #{billing_rules}, #{longitude}, #{latitude})")
    int add_Parking(@Param("pctr_id") String pctr_id,@Param("pctr_password")  String pctr_password,@Param("parking_lot_name")  String parking_lot_name,@Param("Parking_in_the_city") String Parking_in_the_city,@Param("parking_spaces_num") Integer parking_spaces_num,@Param("billing_rules") float billing_rules,@Param("longitude") String longitude,@Param("latitude") String latitude);


    /**
     * 查找停车场管理员
     * */
    @Select("SELECT * FROM Parking_lot_information WHERE pctr_id =#{pctr_id}")
    Parking_lot_information find_Parking(@Param("pctr_id") String pctr_id);


    /**
     * 获取停车场管理员列表
     */
    @Select("select * from Parking_lot_information")
    List<Parking_lot_information> getAllParking();

}
