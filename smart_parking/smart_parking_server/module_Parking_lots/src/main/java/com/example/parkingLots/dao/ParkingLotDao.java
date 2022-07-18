package com.example.parkingLots.dao;

import com.example.parkingLots.entity.Parking;
import com.example.parkingLots.entity.Parking_for_user;
import com.example.parkingLots.entity.Parking_lot_information;

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
    Parking_lot_information getParkingByPid(@Param("pctr_id") String pctr_id);





    /**
     * 根据停车场编号查找停车场信息
     * */
    @Select("SELECT * FROM Parking_lot_information WHERE parking_lot_number =#{parking_lot_number}")
    Parking_lot_information getParkingByPNumber(@Param("parking_lot_number") String parking_lot_number);



    /**
     * 根据停车场编号查找停车场名
     * */
    @Select("SELECT parking_lot_name FROM Parking_lot_information WHERE parking_lot_number =#{parking_lot_number}")
    String getParkingName(@Param("parking_lot_number") String parking_lot_number);


    /**
     * 根据停车场编号查找停车场计费
     * */
    @Select("SELECT billing_rules FROM Parking_lot_information WHERE parking_lot_number =#{parking_lot_number}")
    float getParkingBilling_rules(@Param("parking_lot_number") String parking_lot_number);




    /**
     * 获取停车场管理员列表
     */
    @Select("select parking_lot_name,parking_in_the_city,parking_lot_number,parking_spaces_num,billing_rules,longitude,latitude from Parking_lot_information")
    List<Parking> getAllParking();



    /**
     * 获取某一城市的所有停车场
     */
    @Results({
            @Result(property = "parking_lot_name", column = "parking_lot_name"),
            @Result(property = "Parking_in_the_city", column = "Parking_in_the_city"),
            @Result(property = "parking_lot_number", column = "parking_lot_number"),
            @Result(property = "parking_spaces_num", column = "parking_spaces_num"),
            @Result(property = "billing_rules", column = "billing_rules"),
            @Result(property = "longitude", column = "longitude"),
            @Result(property = "latitude", column = "latitude")
    })
    @Select("select parking_lot_name," +
            "Parking_in_the_city," +
            "parking_lot_number," +
            "parking_spaces_num," +
            "billing_rules," +
            "longitude," +
            "latitude from Parking_lot_information WHERE Parking_in_the_city =#{Parking_in_the_city}")
    List<Parking_for_user> get_parking_lot(@Param("Parking_in_the_city") String Parking_in_the_city);


    /**
     * 删除所有已注册的停车场
     */
    @Delete("DELETE FROM Parking_lot_information")
    void delete_Parking();


}
