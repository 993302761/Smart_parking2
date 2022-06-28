package com.example.provider.dao;

import com.example.provider.entiry.Parking_lot_information;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface VehicleDao {

    /**
     * 查找车辆信息
     * */
    @Select("SELECT * FROM Vehicle_information WHERE pctr_id =#{pctr_id}")
    Parking_lot_information find_Vehicle(@Param("pctr_id") String pctr_id);

}
