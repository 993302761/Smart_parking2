package com.example.Integral.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface IntegralDao {

    /**
     * 设置车辆进入时间
     * */
    @Update("UPDATE User SET integral=integral+10 WHERE user_name=#{user_name}")
    int  addIntegral (@Param("user_name") String user_name);



}
