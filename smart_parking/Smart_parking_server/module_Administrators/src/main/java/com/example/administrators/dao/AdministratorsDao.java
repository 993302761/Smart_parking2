package com.example.administrators.dao;

import com.example.administrators.entity.Administrators;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface AdministratorsDao {


    /**
     * 查找超级管理员
     * */
    @Select("SELECT * FROM Administrators WHERE ctr_id =#{ctr_id}")
    Administrators find_Adm(@Param("ctr_id") String ctr_id);

}
