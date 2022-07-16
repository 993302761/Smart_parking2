package com.example.provider.dao;

import com.example.provider.entity.Parking;
import com.example.provider.entity.Parking_lot_information;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ParkingLotDao {

    /**
     * 增加一个停车场管理员
     *
     * */
    @Insert("INSERT INTO Parking_lot_information VALUES(#{pctr_id}, #{pctr_password}, #{parking_lot_name}, #{Parking_in_the_city},#{parking_lot_number}, #{parking_spaces_num}, #{billing_rules}, #{longitude}, #{latitude})")
    int add_Parking(@Param("pctr_id") String pctr_id,@Param("pctr_password")  String pctr_password,@Param("parking_lot_name")  String parking_lot_name,@Param("Parking_in_the_city") String Parking_in_the_city,@Param("parking_lot_number") String parking_lot_number,@Param("parking_spaces_num") Integer parking_spaces_num,@Param("billing_rules") float billing_rules,@Param("longitude") String longitude,@Param("latitude") String latitude);


    /**
     * 查找停车场管理员
     * */
    @Select("SELECT * FROM Parking_lot_information WHERE pctr_id =#{pctr_id}")
    Parking_lot_information find_Parking(@Param("pctr_id") String pctr_id);


    /**
     * 根据停车场编号查找停车场信息
     * */
    @Select("SELECT * FROM Parking_lot_information WHERE parking_lot_number =#{parking_lot_number}")
    Parking_lot_information find_Parking_num(@Param("parking_lot_number") String parking_lot_number);


    /**
     * 获取停车场管理员列表
     */
    @Select("select * from Parking_lot_information")
    List<Parking_lot_information> getAllParking();

    /**
     * 获取某一城市的所有停车场
     */
    @Select("select parking_lot_name,Parking_in_the_city,parking_lot_number,parking_spaces_num,billing_rules,longitude,latitude from Parking_lot_information WHERE Parking_in_the_city =#{Parking_in_the_city}")
    List<Parking> get_parking_lot(@Param("Parking_in_the_city") String Parking_in_the_city);


    /**
     * 删除所有已注册的停车场
     */
    @Delete("DELETE FROM Parking_lot_information")
    void delete_Parking();


}
