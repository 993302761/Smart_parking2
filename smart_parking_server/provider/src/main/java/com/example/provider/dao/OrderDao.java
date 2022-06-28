package com.example.provider.dao;

import com.example.provider.entiry.Parking_lot_information;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface OrderDao {

    /**
     * 查找订单
     * */
    @Select("SELECT * FROM Order_information WHERE pctr_id =#{pctr_id}")
    Parking_lot_information find_Order(@Param("pctr_id") String pctr_id);


}
