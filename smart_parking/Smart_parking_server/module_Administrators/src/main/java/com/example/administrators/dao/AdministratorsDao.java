package com.example.administrators.dao;

import com.example.administrators.entity.Administrators;
import org.apache.ibatis.annotations.*;

@Mapper
public interface AdministratorsDao {


    /**
     * 查找超级管理员
     * */

    @Select("SELECT ctr_id,ctr_password FROM Administrators WHERE ctr_id =#{ctr_id}")
    Administrators find_Adm(@Param("ctr_id") String ctr_id);

}
